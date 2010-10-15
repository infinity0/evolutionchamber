package com.fray.evo;

import java.util.HashMap;
import java.util.Map;

import com.fray.evo.action.EcAction;
import com.fray.evo.action.EcActionExtractorTrick;
import com.fray.evo.action.EcActionWait;
import com.fray.evo.action.build.EcActionBuildBaneling;
import com.fray.evo.action.build.EcActionBuildBanelingNest;
import com.fray.evo.action.build.EcActionBuildBroodLord;
import com.fray.evo.action.build.EcActionBuildCorruptor;
import com.fray.evo.action.build.EcActionBuildDrone;
import com.fray.evo.action.build.EcActionBuildExtractor;
import com.fray.evo.action.build.EcActionBuildGreaterSpire;
import com.fray.evo.action.build.EcActionBuildHatchery;
import com.fray.evo.action.build.EcActionBuildHive;
import com.fray.evo.action.build.EcActionBuildHydralisk;
import com.fray.evo.action.build.EcActionBuildHydraliskDen;
import com.fray.evo.action.build.EcActionBuildInfestationPit;
import com.fray.evo.action.build.EcActionBuildInfestor;
import com.fray.evo.action.build.EcActionBuildLair;
import com.fray.evo.action.build.EcActionBuildMutalisk;
import com.fray.evo.action.build.EcActionBuildOverlord;
import com.fray.evo.action.build.EcActionBuildQueen;
import com.fray.evo.action.build.EcActionBuildRoach;
import com.fray.evo.action.build.EcActionBuildRoachWarren;
import com.fray.evo.action.build.EcActionBuildSpawningPool;
import com.fray.evo.action.build.EcActionBuildSpineCrawler;
import com.fray.evo.action.build.EcActionBuildSpire;
import com.fray.evo.action.build.EcActionBuildUltralisk;
import com.fray.evo.action.build.EcActionBuildUltraliskCavern;
import com.fray.evo.action.build.EcActionBuildZergling;
import com.fray.evo.action.upgrade.EcActionUpgradeAdrenalGlands;
import com.fray.evo.action.upgrade.EcActionUpgradeBurrow;
import com.fray.evo.action.upgrade.EcActionUpgradeCarapace1;
import com.fray.evo.action.upgrade.EcActionUpgradeCarapace2;
import com.fray.evo.action.upgrade.EcActionUpgradeCarapace3;
import com.fray.evo.action.upgrade.EcActionUpgradeCentrifugalHooks;
import com.fray.evo.action.upgrade.EcActionUpgradeChitinousPlating;
import com.fray.evo.action.upgrade.EcActionUpgradeFlyerArmor1;
import com.fray.evo.action.upgrade.EcActionUpgradeFlyerArmor2;
import com.fray.evo.action.upgrade.EcActionUpgradeFlyerArmor3;
import com.fray.evo.action.upgrade.EcActionUpgradeFlyerAttacks1;
import com.fray.evo.action.upgrade.EcActionUpgradeFlyerAttacks2;
import com.fray.evo.action.upgrade.EcActionUpgradeFlyerAttacks3;
import com.fray.evo.action.upgrade.EcActionUpgradeGlialReconstitution;
import com.fray.evo.action.upgrade.EcActionUpgradeGroovedSpines;
import com.fray.evo.action.upgrade.EcActionUpgradeMelee1;
import com.fray.evo.action.upgrade.EcActionUpgradeMelee2;
import com.fray.evo.action.upgrade.EcActionUpgradeMelee3;
import com.fray.evo.action.upgrade.EcActionUpgradeMetabolicBoost;
import com.fray.evo.action.upgrade.EcActionUpgradeMissile1;
import com.fray.evo.action.upgrade.EcActionUpgradeMissile2;
import com.fray.evo.action.upgrade.EcActionUpgradeMissile3;
import com.fray.evo.action.upgrade.EcActionUpgradeNeuralParasite;
import com.fray.evo.action.upgrade.EcActionUpgradePathogenGlands;
import com.fray.evo.action.upgrade.EcActionUpgradePneumatizedCarapace;
import com.fray.evo.action.upgrade.EcActionUpgradeTunnelingClaws;
import com.fray.evo.action.upgrade.EcActionUpgradeVentralSacs;

public class EcRequirementTree
{
	static int max;
	public static void execute(EcState target)
	{
		max = 0;
		Map<Integer, Class> actions = new HashMap<Integer,Class>();

		add(actions,new EcActionBuildQueen());
		add(actions,new EcActionBuildDrone());
		add(actions,new EcActionBuildExtractor());
		add(actions,new EcActionExtractorTrick());
		add(actions,new EcActionBuildHatchery());
		add(actions,new EcActionBuildOverlord());
		add(actions,new EcActionBuildSpawningPool());
		add(actions,new EcActionWait());

		if (target.adrenalGlands)
			add(actions,new EcActionUpgradeAdrenalGlands());
		if (target.armor1)
			add(actions,new EcActionUpgradeCarapace1());
		if (target.armor2)
			add(actions,new EcActionUpgradeCarapace2());
		if (target.armor3)
			add(actions,new EcActionUpgradeCarapace3());
		if (target.burrow)
			add(actions,new EcActionUpgradeBurrow());
		if (target.centrifugalHooks)
			add(actions,new EcActionUpgradeCentrifugalHooks());
		if (target.chitinousPlating)
			add(actions,new EcActionUpgradeChitinousPlating());
		if (target.flyerArmor1)
			add(actions,new EcActionUpgradeFlyerArmor1());
		if (target.flyerArmor2)
			add(actions,new EcActionUpgradeFlyerArmor2());
		if (target.flyerArmor3)
			add(actions,new EcActionUpgradeFlyerArmor3());
		if (target.flyerAttack1)
			add(actions,new EcActionUpgradeFlyerAttacks1());
		if (target.flyerAttack2)
			add(actions,new EcActionUpgradeFlyerAttacks2());
		if (target.flyerAttack3)
			add(actions,new EcActionUpgradeFlyerAttacks3());
		if (target.glialReconstitution)
			add(actions,new EcActionUpgradeGlialReconstitution());
		if (target.groovedSpines)
			add(actions,new EcActionUpgradeGroovedSpines());
		if (target.melee1)
			add(actions,new EcActionUpgradeMelee1());
		if (target.melee2)
			add(actions,new EcActionUpgradeMelee2());
		if (target.melee3)
			add(actions,new EcActionUpgradeMelee3());
		if (target.metabolicBoost)
			add(actions,new EcActionUpgradeMetabolicBoost());
		if (target.missile1)
			add(actions,new EcActionUpgradeMissile1());
		if (target.missile2)
			add(actions,new EcActionUpgradeMissile2());
		if (target.missile3)
			add(actions,new EcActionUpgradeMissile3());
		if (target.neuralParasite)
			add(actions,new EcActionUpgradeNeuralParasite());
		if (target.pathogenGlands)
			add(actions,new EcActionUpgradePathogenGlands());
		if (target.pneumatizedCarapace)
			add(actions,new EcActionUpgradePneumatizedCarapace());
		if (target.tunnelingClaws)
			add(actions,new EcActionUpgradeTunnelingClaws());
		if (target.ventralSacs)
			add(actions,new EcActionUpgradeVentralSacs());
		if (target.banelingNest > 0)
			add(actions,new EcActionBuildBanelingNest());
		if (target.banelings > 0)
			add(actions,new EcActionBuildBaneling());
		if (target.broodlords> 0)
			add(actions,new EcActionBuildBroodLord());
		if (target.corruptors > 0)
			add(actions,new EcActionBuildCorruptor());
		if (target.greaterSpire> 0)
			add(actions,new EcActionBuildGreaterSpire());
		if (target.hives> 0)
			add(actions,new EcActionBuildHive());
		if (target.hydraliskDen> 0)
			add(actions,new EcActionBuildHydraliskDen());
		if (target.hydralisks> 0)
			add(actions,new EcActionBuildHydralisk());
		if (target.infestationPit> 0)
			add(actions,new EcActionBuildInfestationPit());
		if (target.infestors> 0)
			add(actions,new EcActionBuildInfestor());
		if (target.lairs > 0)
			add(actions,new EcActionBuildLair());
		if (target.mutalisks> 0)
			add(actions,new EcActionBuildMutalisk());
		if (target.roaches> 0)
			add(actions,new EcActionBuildRoach());
		if (target.roachWarrens > 0)
			add(actions,new EcActionBuildRoachWarren());
		if (target.spire > 0)
			add(actions,new EcActionBuildSpire());
		if (target.ultraliskCavern > 0)
			add(actions,new EcActionBuildUltraliskCavern());
		if (target.ultralisks > 0)
			add(actions,new EcActionBuildUltralisk());
		if (target.zerglings > 0)
			add(actions,new EcActionBuildZergling());
		if (target.spineCrawlers > 0)
			add(actions,new EcActionBuildSpineCrawler());

		for (Class a : actions.values())
			System.out.println(a.getSimpleName());

		EcAction.actions = actions;
	}

	private static void add(Map<Integer, Class> actions, EcAction action)
	{
		if (!actions.containsValue(action.getClass()))
		{
			actions.put(max++, action.getClass());
			for (EcAction a : action.requirements())
			{
				add(actions, a);
			}
		}
	}
}
