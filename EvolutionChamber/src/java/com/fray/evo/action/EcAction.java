package com.fray.evo.action;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcRequirementTree;
import com.fray.evo.EcSettings;
import com.fray.evo.EcState;

public abstract class EcAction implements Serializable
{
	public static Map<Integer, Class>	actions = Collections.synchronizedMap(new HashMap<Integer, Class>());

	public abstract void execute(EcBuildOrder s, EcEvolver e);

	@Override
	public String toString()
	{
		return getClass().getSimpleName().replace("EcAction", "");
	}
	
	public String toBuildOrderString(EcState state)
	{
		//remove all the prefixes
		String result = 
		getClass().getSimpleName()
		.replace("EcAction", "")
		.replace("Build", "")
		.replace("Upgrade", "");
		if (state.settings.pullThreeWorkersOnly)
		{
			result = result.replace("MineGas", "+3 Drones on gas").replace("MineMineral", "+3 Drones on minerals");
		}
		else
		{
			result = result.replace("MineGas", "+1 Drone on gas").replace("MineMineral", "+1 Drone on minerals");
		}
		return result;
	}
	
	public String yabotGetTag(EcState s)
	{
		//Get the item type for Yabot Output
		String result = 
		getClass().getSimpleName()
		.replace("EcAction", "")
		.replace("Build", "")
		.replace("Upgrade", "")		
		.replace("BanelingNest", " ")
		.replace("EvolutionChamber", " ")
		.replace("Extractor", " ")
		.replace("Hatchery", " ")
		.replace("HydralistDen", " ")
		.replace("InfestationPit", " ")
		.replace("NydusNetwork", " ")
		.replace("RoachWarren", " ")
		.replace("SpawningPool", " ")
		.replace("SpineCrawler", " ")
		.replace("Spire", " ")
		.replace("SporeCrawler", " ")
		.replace("UltraliskCavern", " ")
		.replace("Corruptor", " ")
		.replace("Drone", " ")
		.replace("Hydralisk", " ")
		.replace("Infestor", " ")
		.replace("Mutalisk", " ")
		.replace("Overlord", " ")
		.replace("Queen", " ")
		.replace("Roach", " ")
		.replace("Ultralisk", " ")
		.replace("Zergling", " ")
		.replace("Lair", " ")
		.replace("Hive", " ")
		.replace("GreaterSpire", " ")
		.replace("BroodLord", " ")
		.replace("Baneling", " ")
		.replace("Overseer", " ")
		.replace("Carapace1", " ")
		.replace("Carapace2", " ")
		.replace("Carapace3", " ")
		.replace("Melee1", " ")
		.replace("Melee2", " ")
		.replace("Melee3", " ")
		.replace("FlyerAttacks1", " ")
		.replace("FlyerAttacks2", " ")
		.replace("FlyerAttacks3", " ")
		.replace("FlyerArmor1", " ")
		.replace("FlyerArmor2", " ")
		.replace("FlyerArmor3", " ")
		.replace("Missile1", " ")
		.replace("Missile2", " ")
		.replace("Missile3", " ")
		.replace("GoovedSpines", " ")
		.replace("PneumatizedCarapace", " ")
		.replace("GlialReconstitution", " ")
		.replace("TunnelingClaws", " ")
		.replace("ChitinousPlating", " ")
		.replace("AdrenalGlands", " ")
		.replace("MetabolicBoost", " ")
		.replace("Burrow", " ")
		.replace("CentrifugalHooks", " ")
		.replace("NeuralParasite", " ")
		.replace("PathogenGlands", " ");
		if (s.settings.pullThreeWorkersOnly)
		{
			result = result.replace("MineGas", " Add_3_drones_to_gas ").replace("MineMineral ", " Add_3_drones_to_minerals ");
		}
		else
		{
			result = result.replace("MineGas", " Add_1_drone_to_gas ").replace("MineMineral", " Add_1_drones_to_minerals ");
		}
		return result;
	}
	
	public String yabotGetType(EcState s)
	{
		//Get the item type for Yabot Output
		String result = 
		getClass().getSimpleName()
		.replace("EcAction", "")
		.replace("Build", "")
		.replace("Upgrade", "")		
		.replace("BanelingNest", "0")
		.replace("EvolutionChamber", "0")
		.replace("Extractor", "0")
		.replace("Hatchery", "0")
		.replace("HydralistDen", "0")
		.replace("InfestationPit", "0")
		.replace("NydusNetwork", "0")
		.replace("RoachWarren", "0")
		.replace("SpawningPool", "0")
		.replace("SpineCrawler", "0")
		.replace("Spire", "0")
		.replace("SporeCrawler", "0")
		.replace("UltraliskCavern", "0")
		.replace("Corruptor", "1")
		.replace("Drone", "1")
		.replace("Hydralisk", "1")
		.replace("Infestor", "1")
		.replace("Mutalisk", "1")
		.replace("Overlord", "1")
		.replace("Queen", "1")
		.replace("Roach", "1")
		.replace("Ultralisk", "1")
		.replace("Zergling", "1")
		.replace("Lair", "2")
		.replace("Hive", "2")
		.replace("GreaterSpire", "2")
		.replace("BroodLord", "2")
		.replace("Baneling", "2")
		.replace("Overseer", "2")
		.replace("Carapace1", "3")
		.replace("Carapace2", "3")
		.replace("Carapace3", "3")
		.replace("Melee1", "3")
		.replace("Melee2", "3")
		.replace("Melee3", "3")
		.replace("FlyerAttacks1", "3")
		.replace("FlyerAttacks2", "3")
		.replace("FlyerAttacks3", "3")
		.replace("FlyerArmor1", "3")
		.replace("FlyerArmor2", "3")
		.replace("FlyerArmor3", "3")
		.replace("Missile1", "3")
		.replace("Missile2", "3")
		.replace("Missile3", "3")
		.replace("GoovedSpines", "3")
		.replace("PneumatizedCarapace", "3")
		.replace("GlialReconstitution", "3")
		.replace("TunnelingClaws", "3")
		.replace("ChitinousPlating", "3")
		.replace("AdrenalGlands", "3")
		.replace("MetabolicBoost", "3")
		.replace("Burrow", "3")
		.replace("CentrifugalHooks", "3")
		.replace("NeuralParasite", "3")
		.replace("PathogenGlands", "3");
		if (s.settings.pullThreeWorkersOnly)
		{
			result = result.replace("MineGas", "0").replace("MineMineral", "0");
		}
		else
		{
			result = result.replace("MineGas", "0").replace("MineMineral", "0");
		}
		return result;
	}
	
	public String yabotGetItem(EcState s)
	{
		//Get the Item number for Yabot Output
		String result = 
		getClass().getSimpleName()
		.replace("EcAction", "")
		.replace("Build", "")
		.replace("Upgrade", "")		
		.replace("BanelingNest", "33")
		.replace("EvolutionChamber", "34")
		.replace("Extractor", "35")
		.replace("Hatchery", "36")
		.replace("HydraliskDen", "37")
		.replace("InfestationPit", "38")
		.replace("NydusNetwork", "39")
		.replace("RoachWarren", "40")
		.replace("SpawningPool", "41")
		.replace("SpineCrawler", "42")
		.replace("Spire", "43")
		.replace("SporeCrawler", "44")
		.replace("UltraliskCavern", "45")
		.replace("Corruptor", "27")
		.replace("Drone", "28")
		.replace("Hydralisk", "29")
		.replace("Infestor", "38")
		.replace("Mutalisk", "30")
		.replace("Overlord", "31")
		.replace("Queen", "32")
		.replace("Roach", "33")
		.replace("Ultralisk", "34")
		.replace("Zergling", "35")
		.replace("Lair", "3")
		.replace("Hive", "4")
		.replace("GreaterSpire", "5")
		.replace("BroodLord", "6")
		.replace("Baneling", "7")
		.replace("Overseer", "8")
		.replace("Carapace1", "28")
		.replace("Carapace2", "28")
		.replace("Carapace3", "28")
		.replace("Melee1", "29")
		.replace("Melee2", "29")
		.replace("Melee3", "29")
		.replace("FlyerAttacks1", "31")
		.replace("FlyerAttacks2", "31")
		.replace("FlyerAttacks3", "31")
		.replace("FlyerArmor1", "30")
		.replace("FlyerArmor2", "30")
		.replace("FlyerArmor3", "30")
		.replace("Missile1", "32")
		.replace("Missile2", "32")
		.replace("Missile3", "32")
		.replace("GoovedSpines", "33")
		.replace("PneumatizedCarapace", "34")
		.replace("GlialReconstitution", "36")
		.replace("TunnelingClaws", "38")
		.replace("ChitinousPlating", "40")
		.replace("AdrenalGlands", "41")
		.replace("MetabolicBoost", "42")
		.replace("Burrow", "44")
		.replace("CentrifugalHooks", "45")
		.replace("NeuralParasite", "49")
		.replace("PathogenGlands", "50");
		if (s.settings.pullThreeWorkersOnly)
		{
			result = result.replace("MineGas", "0").replace("MineMineral", "0");
		}
		else
		{
			result = result.replace("MineGas", "0").replace("MineMineral", "0");
		}
		return result;
	}

	public boolean canExecute(EcBuildOrder s)
	{
		if (isPossible(s))
			return true;
		s.seconds += 1;
		Collection<Runnable> futureActions = s.getFutureActions(s.seconds);
		if (futureActions != null)
			for (Runnable r : futureActions)
				r.run();
		s.accumulateMaterials();
		return false;
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		return false;
	}

	public abstract boolean isPossible(EcBuildOrder s);

	public abstract List<EcAction> requirements(EcState destination);

	public static Integer findAllele(EcAction a)
	{
		Integer allele = null;
		for (Integer i : actions.keySet())
		{
			Class a2 = actions.get(i);
			if (!actions.containsValue(a.getClass()))
				break;
			if (a2.getName().equals(a.getClass().getName()))
			{
				allele = i;
				break;
			}
		}
		return allele;
	}

	public static void setup(EcState target)
	{
//		actions = new HashMap<Integer, Class>();
		EcRequirementTree.execute(target);
	}

}
