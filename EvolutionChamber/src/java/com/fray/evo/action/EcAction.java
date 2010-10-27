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
	
	public String toBuildOrderString()
	{
		//remove all the prefixes
		String result = 
		getClass().getSimpleName()
		.replace("EcAction", "")
		.replace("Build", "")
		.replace("Upgrade", "");
		if (EcSettings.pullThreeWorkersOnly)
		{
			result = result.replace("MineGas", "+3 Drones on gas").replace("MineMineral", "+3 Drones on minerals");
		}
		else
		{
			result = result.replace("MineGas", "+1 Drone on gas").replace("MineMineral", "+1 Drone on minerals");
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
