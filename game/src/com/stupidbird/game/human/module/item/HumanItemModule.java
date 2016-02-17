package com.stupidbird.game.human.module.item;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.stupidbird.core.actor.event.ActorEvent;
import com.stupidbird.game.entity.HumanEntity;
import com.stupidbird.game.entity.HumanItemEntity;
import com.stupidbird.game.event.KickAss;
import com.stupidbird.game.human.Human;
import com.stupidbird.game.human.module.BaseHumanModule;
import com.stupidbird.game.human.module.front.EquipFronter;
import com.stupidbird.game.human.module.front.ItemFronter;

/**
 * 玩家物品模块;
 * 
 * @author crazyjohn
 *
 */
public class HumanItemModule extends BaseHumanModule implements ItemFronter {
	protected List<HumanItemEntity> items = new ArrayList<HumanItemEntity>();
	@Inject
	protected EquipFronter equipModule;
	private Logger logger = LoggerFactory.getLogger("Action");

	@Inject
	public HumanItemModule(Human human) {
		super(human);
	}

	@Override
	public void onLogin(HumanEntity entity) {
		items = entity.getItems();
	}

	@Override
	public void onLogout() {
		// TODO Auto-generated method stub
		// PlayerActor other =
		// GameWorld.getInstance().queryPlayerActor(288511851128422402l);
		// if (other != null) {
		// other.tell(new KickAss(this.human.getPlayer().getId()));
		// }

	}

	@Override
	public void onEvent(ActorEvent event) {
		if (event instanceof KickAss) {
			KickAss kick = (KickAss) event;
			logger.info(String.format("This biatch [%d] want to kick my ass!", kick.from()));
		}
	}

	@Override
	public void addItem(HumanItemEntity itemEntity) {
		items.add(itemEntity);
	}

	@Override
	public void assemble(HumanEntity entity) {
		// TODO Auto-generated method stub
		
	}

}
