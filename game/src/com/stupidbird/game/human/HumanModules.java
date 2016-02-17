package com.stupidbird.game.human;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.stupidbird.game.human.module.equip.HumanEquipModule;
import com.stupidbird.game.human.module.front.EquipFronter;
import com.stupidbird.game.human.module.front.ItemFronter;
import com.stupidbird.game.human.module.item.HumanItemModule;

/**
 * 角色级别的模块配置;
 * 
 * @author crazyjohn
 *
 */
public class HumanModules extends AbstractModule {
	private Human human;

	public HumanModules(Human human) {
		this.human = human;
	}

	@Override
	protected void configure() {
		// 物品
		bind(ItemFronter.class).to(HumanItemModule.class).in(Scopes.SINGLETON);
		// 装备
		bind(EquipFronter.class).to(HumanEquipModule.class).in(Scopes.SINGLETON);
	}

	@Provides
	private Human human() {
		return human;
	}

}
