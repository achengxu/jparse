package com.achengxu.parse.base;

import game.data.card.CardIdData;
import game.data.card.CardSkillData;
import game.data.card.CardTakeData;
import game.data.card.CardUpExpData;
import game.data.card.group.GroupAttack1;
import game.data.card.group.GroupAttack2;
import game.data.card.group.GroupDefense1;
import game.data.card.group.GroupDefense2;
import game.data.drop.DropPackData;
import game.data.effect.EffectCardData;
import game.data.effect.EffectDJData;
import game.data.effect.EffectItemData;
import game.data.effect.EffectPowerData;
import game.data.effect.EffectRoleData;
import game.data.effect.EffectStartData;
import game.data.effect.EffectTaskData;
import game.data.login.GoLoginData;
import game.data.mall.GiftBagData;
import game.data.mall.ItemData;
import game.data.mall.MallData;
import game.data.mall.UsedData;
import game.data.name.NameManData;
import game.data.name.NameTagData;
import game.data.name.NameWomenData;
import game.data.role.RoleBaseData;
import game.data.shut.SoulData;
import game.data.shut.SoulGetData;
import game.data.task.TaskBossData;
import game.data.task.TaskIdData;
import game.data.task.TaskInitData;
import game.data.task.TaskListData;
import game.data.task.TaskWorldData;
import game.data.trial.TrialBossData;
import game.data.trial.TrialIdData;
import game.data.trial.TrialLimitData;
import game.data.trial.TrialListData;
import game.data.trial.TrialReLiveData;
import game.data.trial.TrialWorldData;
import game.data.words.WordsData;

import java.util.HashMap;

import com.achengxu.parse.conf.Config;
import com.achengxu.parse.conf.Constant;

public final class ReadUtil {

	static {
		Config.parseProperties("conf.properties");
	}

	public final static void init() {
		final HashMap<String, Class<?>> map = new HashMap<String, Class<?>>();
		// 角色
		map.put("value.role.base.xls", RoleBaseData.class);
		// 任务
		map.put("value.task.world.xls", TaskWorldData.class);
		map.put("value.task.list.xls", TaskListData.class);
		map.put("value.task.id.xls", TaskIdData.class);
		map.put("value.task.boss.xls", TaskBossData.class);
		map.put("value.task.init.xls", TaskInitData.class);
		// 试炼
		map.put("value.trial.id.xls", TrialIdData.class);
		map.put("value.trial.list.xls", TrialListData.class);
		map.put("value.trial.world.xls", TrialWorldData.class);
		map.put("value.trial.boss.xls", TrialBossData.class);
		map.put("value.trial.limit.xls", TrialLimitData.class);
		map.put("value.trial.relive.xls", TrialReLiveData.class);
		// 斗法
		map.put("value.shut.soul.xls", SoulData.class);
		map.put("value.shut.get.xls", SoulGetData.class);
		// 卡牌
		map.put("value.card.data.xls", CardIdData.class);
		map.put("value.card.take.xls", CardTakeData.class);
		map.put("value.card.upexp.xls", CardUpExpData.class);
		map.put("value.card.skill.xls", CardSkillData.class);
		// 名字

		map.put("value.name.tag.xls", NameTagData.class);
		map.put("value.name.man.xls", NameManData.class);
		map.put("value.name.women.xls", NameWomenData.class);
		map.put("value.words.xls", WordsData.class);

		// 商城
		map.put("value.mall.data.xls", MallData.class);
		map.put("value.mall.item.xls", ItemData.class);
		map.put("value.mall.consu.xls", UsedData.class);
		map.put("value.mall.gift.xls", GiftBagData.class);
		map.put("value.get.gologin.xls", GoLoginData.class);
		map.put("value.drop.data.xls", DropPackData.class);
		map.put("value.group.attack1.xls", GroupAttack1.class);
		map.put("value.group.attack2.xls", GroupAttack2.class);
		map.put("value.group.defense1.xls", GroupDefense1.class);
		map.put("value.group.defense2.xls", GroupDefense2.class);
		
		map.put("value.effect.dj.xls", EffectDJData.class);
		map.put("value.effect.role.xls", EffectRoleData.class);
		map.put("value.effect.task.xls", EffectTaskData.class);
		map.put("value.effect.item.xls", EffectItemData.class);
		map.put("value.effect.card.xls", EffectCardData.class);
		map.put("value.effect.start.xls", EffectStartData.class);
		map.put("value.effect.power.xls", EffectPowerData.class);
		
		long start = System.currentTimeMillis();
		for (final String excleName : map.keySet()) {
			parse(excleName, map.get(excleName));
		}
		long end = System.currentTimeMillis();
		System.out.println("parse ok used:" + (end - start) / 1000 + "s");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		init();
	}

	/**
	 * excle=>java数据解析
	 * 
	 * @param input
	 *            导入excle源文件
	 * @param output
	 *            导出解析好的二进制文件
	 * @param className
	 * 
	 */
	public final static void parse(String input, String output, String className) {
		new ReadExcleJava(input, output, className);
	}

	public final static void parse(String input, String output, Class<?> c) {
		parse(input, output, c.getName());
	}

	public final static void parse(String excleName, String className) {
		String input = Config.map.get(Constant.IN_XLS_PATH) + "/" + excleName;
		String output = excleName.substring(0, excleName.lastIndexOf('.')) + Config.map.get(Constant.OUT_BEAN_SUFFIX);
		new ReadExcleJava(input, output, className);
	}

	public final static void parse(String excleName, Class<?> c) {
		parse(excleName, c.getName());
	}

}
