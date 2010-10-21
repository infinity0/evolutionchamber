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
import com.fray.evo.action.build.EcActionBuildNydusCanal;
import com.fray.evo.action.build.EcActionBuildNydusWorm;
import com.fray.evo.action.build.EcActionBuildOverlord;
import com.fray.evo.action.build.EcActionBuildOverseer;
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
	public static void execute(EcState destination)
	{
		max = 0;
		Map<Integer, Class> actions = EcAction.actions;
		actions.clear();

		add(actions,new EcActionBuildQueen(), destination);
		add(actions,new EcActionBuildDrone(), destination);
		add(actions,new EcActionExtractorTrick(), destination);
		add(actions,new EcActionBuildHatchery(), destination);
		add(actions,new EcActionBuildOverlord(), destination);
		add(actions,new EcActionBuildSpawningPool(), destination);
		add(actions,new EcActionWait(), destination);

		actions(destination, actions);

		for (Class a : actions.values())
			System.out.println(a.getSimpleName());

	}

	private static void actions(EcState destination, Map<Integer, Class> actions)
	{
		if (destination.adrenalGlands)
			add(actions,new EcActionUpgradeAdrenalGlands(), destination);
		if (destination.armor1)
			add(actions,new EcActionUpgradeCarapace1(), destination);
		if (destination.armor2)
			add(actions,new EcActionUpgradeCarapace2(), destination);
		if (destination.armor3)
			add(actions,new EcActionUpgradeCarapace3(), destination);
		if (destination.burrow)
			add(actions,new EcActionUpgradeBurrow(), destination);
		if (destination.centrifugalHooks)
			add(actions,new EcActionUpgradeCentrifugalHooks(), destination);
		if (destination.chitinousPlating)
			add(actions,new EcActionUpgradeChitinousPlating(), destination);
		if (destination.flyerArmor1)
			add(actions,new EcActionUpgradeFlyerArmor1(), destination);
		if (destination.flyerArmor2)
			add(actions,new EcActionUpgradeFlyerArmor2(), destination);
		if (destination.flyerArmor3)
			add(actions,new EcActionUpgradeFlyerArmor3(), destination);
		if (destination.flyerAttack1)
			add(actions,new EcActionUpgradeFlyerAttacks1(), destination);
		if (destination.flyerAttack2)
			add(actions,new EcActionUpgradeFlyerAttacks2(), destination);
		if (destination.flyerAttack3)
			add(actions,new EcActionUpgradeFlyerAttacks3(), destination);
		if (destination.glialReconstitution)
			add(actions,new EcActionUpgradeGlialReconstitution(), destination);
		if (destination.groovedSpines)
			add(actions,new EcActionUpgradeGroovedSpines(), destination);
		if (destination.melee1)
			add(actions,new EcActionUpgradeMelee1(), destination);
		if (destination.melee2)
			add(actions,new EcActionUpgradeMelee2(), destination);
		if (destination.melee3)
			add(actions,new EcActionUpgradeMelee3(), destination);
		if (destination.metabolicBoost)
			add(actions,new EcActionUpgradeMetabolicBoost(), destination);
		if (destination.missile1)
			add(actions,new EcActionUpgradeMissile1(), destination);
		if (destination.missile2)
			add(actions,new EcActionUpgradeMissile2(), destination);
		if (destination.missile3)
			add(actions,new EcActionUpgradeMissile3(), destination);
		if (destination.neuralParasite)
			add(actions,new EcActionUpgradeNeuralParasite(), destination);
		if (destination.pathogenGlands)
			add(actions,new EcActionUpgradePathogenGlands(), destination);
		if (destination.pneumatizedCarapace)
			add(actions,new EcActionUpgradePneumatizedCarapace(), destination);
		if (destination.tunnelingClaws)
			add(actions,new EcActionUpgradeTunnelingClaws(), destination);
		if (destination.ventralSacs)
			add(actions,new EcActionUpgradeVentralSacs(), destination);
		if (destination.gasExtractors>0)
			add(actions,new EcActionBuildExtractor(), destination);
		if (destination.banelingNest > 0)
			add(actions,new EcActionBuildBanelingNest(), destination);
		if (destination.banelings > 0)
			add(actions,new EcActionBuildBaneling(), destination);
		if (destination.broodlords> 0)
			add(actions,new EcActionBuildBroodLord(), destination);
		if (destination.corruptors > 0)
			add(actions,new EcActionBuildCorruptor(), destination);
		if (destination.greaterSpire> 0)
			add(actions,new EcActionBuildGreaterSpire(), destination);
		if (destination.hives> 0)
			add(actions,new EcActionBuildHive(), destination);
		if (destination.hydraliskDen> 0)
			add(actions,new EcActionBuildHydraliskDen(), destination);
		if (destination.hydralisks> 0)
			add(actions,new EcActionBuildHydralisk(), destination);
		if (destination.infestationPit> 0)
			add(actions,new EcActionBuildInfestationPit(), destination);
		if (destination.infestors> 0)
			add(actions,new EcActionBuildInfestor(), destination);
		if (destination.lairs > 0)
			add(actions,new EcActionBuildLair(), destination);
		if (destination.mutalisks> 0)
			add(actions,new EcActionBuildMutalisk(), destination);
		if (destination.roaches> 0)
			add(actions,new EcActionBuildRoach(), destination);
		if (destination.roachWarrens > 0)
			add(actions,new EcActionBuildRoachWarren(), destination);
		if (destination.spire > 0)
			add(actions,new EcActionBuildSpire(), destination);
		if (destination.ultraliskCavern > 0)
			add(actions,new EcActionBuildUltraliskCavern(), destination);
		if (destination.ultralisks > 0)
			add(actions,new EcActionBuildUltralisk(), destination);
		if (destination.zerglings > 0)
			add(actions,new EcActionBuildZergling(), destination);
		if (destination.spineCrawlers > 0)
			add(actions,new EcActionBuildSpineCrawler(), destination);
		if (destination.overseers > 0)
			add(actions,new EcActionBuildOverseer(), destination);
		if (destination.nydusCanal > 0)
			add(actions,new EcActionBuildNydusCanal(), destination);
		if (destination.nydusWorm > 0)
			add(actions,new EcActionBuildNydusWorm(), destination);
		for (EcState s : destination.waypoints)
			actions(s,actions);
	}

	private static void add(Map<Integer, Class> actions, EcAction action, EcState destination)
	{
		if (!actions.containsValue(action.getClass()))
		{
			actions.put(max++, action.getClass());
			for (EcAction a : action.requirements(destination))
			{
				add(actions, a, destination);
			}
		}
	}
}
