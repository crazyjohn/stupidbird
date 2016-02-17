package com.stupidbird.game.human.module.equip;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.stupidbird.game.entity.HumanEntity;
import com.stupidbird.game.entity.HumanEquipEntity;
import com.stupidbird.game.human.Human;
import com.stupidbird.game.human.module.BaseHumanModule;
import com.stupidbird.game.human.module.front.EquipFronter;
import com.stupidbird.game.human.module.front.ItemFronter;

public class HumanEquipModule extends BaseHumanModule implements EquipFronter {
	protected List<HumanEquipEntity> equips = new ArrayList<HumanEquipEntity>();
	@Inject
	protected ItemFronter itemModule;

	@Inject
	public HumanEquipModule(Human human) {
		super(human);
	}

	@Override
	public void onLogin(HumanEntity entity) {
		this.equips = entity.getEquips();
	}

	@Override
	public void onLogout() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addEquip(HumanEquipEntity equipEntity) {
		equips.add(equipEntity);
	}

	@Override
	public void assemble(HumanEntity entity) {
		// TODO Auto-generated method stub
		
	}
}
