package com.stupidbird.game.player.module;

import java.util.List;

import com.google.inject.Injector;
import com.google.protobuf.InvalidProtocolBufferException;
import com.stone.proto.Auths.CreateRole;
import com.stone.proto.Auths.EnterScene;
import com.stone.proto.Auths.Login;
import com.stone.proto.Auths.LoginResult;
import com.stone.proto.Auths.Role;
import com.stone.proto.Auths.RoleList;
import com.stone.proto.Auths.SelectRole;
import com.stone.proto.Humans.Human;
import com.stone.proto.MessageTypes.MessageType;
import com.stupidbird.core.msg.Message;
import com.stupidbird.game.entity.HumanEntity;
import com.stupidbird.game.entity.PlayerEntity;
import com.stupidbird.game.human.HumanModules;
import com.stupidbird.game.human.module.front.EquipFronter;
import com.stupidbird.game.human.module.front.ItemFronter;
import com.stupidbird.game.player.Player;
import com.stupidbird.game.world.GameWorld;

/**
 * 玩家登陆模块;
 * 
 * @author crazyjohn
 *
 */
public class PlayerLoginModule extends BasePlayerModule {
	private GameWorld world;

	public PlayerLoginModule(Player player) {
		super(player);
		world = player.globalInjector(GameWorld.class);
	}

	@Override
	public void onMessage(Message msg) throws Exception {
		if (msg.getType() == MessageType.CG_PLAYER_LOGIN_VALUE) {
			handleLogin(msg);
		} else if (msg.getType() == MessageType.CG_GET_ROLE_LIST_VALUE) {
			handleRoleList(msg);
		} else if (msg.getType() == MessageType.CG_CREATE_ROLE_VALUE) {
			handleCreateRle(msg);
		} else if (msg.getType() == MessageType.CG_SELECT_ROLE_VALUE) {
			handleSelectRole(msg);
		}
	}

	private void handleSelectRole(Message msg) throws InvalidProtocolBufferException {
		// 选角色
		SelectRole.Builder selectRole = msg.getBuilder(SelectRole.newBuilder());

		// mongo way
		List<HumanEntity> entities = dbService().query(HumanEntity.class, new String[] { "id" },
				new Object[] { selectRole.getRoleId() });

		// 游戏角色
		HumanEntity humanEntity = null;
		if (entities.size() > 0) {
			humanEntity = entities.get(0);
			Human.Builder humanBuilder = Human.newBuilder();
			// bind player and human for each other
			com.stupidbird.game.human.Human human = new com.stupidbird.game.human.Human(player);
			player.setHuman(human);
			// 创建模块
			Injector humanInjector = player.createChildInjector(new HumanModules(human));
			humanInjector.getInstance(ItemFronter.class);
			humanInjector.getInstance(EquipFronter.class);
			// 加载角色数据
			human.onLogin(humanEntity);
			// 进入游戏世界
			world.enter(player);
			humanBuilder.setGuid(humanEntity.getId()).setLevel(humanEntity.getLevel()).setName(humanEntity.getName())
					.setPlayerId(humanEntity.getPlayerId());
			EnterScene.Builder enterScene = EnterScene.newBuilder();
			enterScene.setHuman(humanBuilder);
			player.sendMessage(MessageType.GC_ENTER_SCENE_VALUE, enterScene);
		}
	}

	private void handleCreateRle(Message msg) throws InvalidProtocolBufferException {
		// 创建角色
		CreateRole.Builder createRole = msg.getBuilder(CreateRole.newBuilder());
		HumanEntity newHuman = new HumanEntity();
		newHuman.motherGive();
		newHuman.setId(this.player.getUUIDService().getNextId(HumanEntity.class));
		newHuman.setLevel(1);
		newHuman.setName(createRole.getName());
		newHuman.setPlayerId(player.getId());
		dbService().insert(newHuman);
		// 下发角色列表
		RoleList.Builder roleList = RoleList.newBuilder();
		Role.Builder role = Role.newBuilder();
		role.setName(newHuman.getName());
		role.setRoleId(newHuman.getId());
		roleList.addRoleList(role);
		player.sendMessage(MessageType.GC_GET_ROLE_LIST_VALUE, roleList);
	}

	private void handleRoleList(Message msg) throws InvalidProtocolBufferException {
		// 角色列表

		// mongo way
		List<HumanEntity> entities = dbService().query(HumanEntity.class, new String[] { "playerId" },
				new Object[] { this.player.getId() });
		if (entities.size() <= 0) {
			player.sendMessage(MessageType.GC_GET_ROLE_LIST_VALUE, RoleList.newBuilder());
		} else {
			RoleList.Builder roleList = msg.getBuilder(RoleList.newBuilder());
			for (HumanEntity eachEntity : entities) {
				Role.Builder role = Role.newBuilder();
				role.setName(eachEntity.getName());
				role.setRoleId(eachEntity.getId());
				roleList.addRoleList(role);
			}
			player.sendMessage(MessageType.GC_GET_ROLE_LIST_VALUE, roleList);
		}

	}

	private void handleLogin(Message msg) throws InvalidProtocolBufferException {
		Login.Builder login = msg.getBuilder(Login.newBuilder());

		// mongo way
		List<PlayerEntity> entities = player.getDBService().query(PlayerEntity.class, new String[] { "puid" },
				new Object[] { login.getPuid() });

		PlayerEntity myPlayerEntity = null;
		if (entities.size() == 0) {
			// 创建玩家实体
			PlayerEntity newPlayer = new PlayerEntity();
			newPlayer.setId(player.getUUIDService().getNextId(PlayerEntity.class));
			newPlayer.setPuid(login.getPuid());
			player.getDBService().insert(newPlayer);
			myPlayerEntity = newPlayer;
		} else {
			myPlayerEntity = entities.get(0);
		}
		// 设置实体
		this.player.setPlayerEntity(myPlayerEntity);
		// 发送登陆成功
		LoginResult.Builder loginResult = LoginResult.newBuilder();
		loginResult.setSucceed(true);
		player.sendMessage(MessageType.GC_PLAYER_LOGIN_RESULT_VALUE, loginResult);
	}

}
