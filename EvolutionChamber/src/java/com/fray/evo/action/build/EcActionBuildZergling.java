package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildZergling extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildZergling()
	{
		super(50, 0, 1, 24, "Zergling", true);
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
		s.zerglings += 2;

	}

	@Override
	protected void obtainOne(EcBuildOrder s, EcEvolver e)
	{
		if (e.debug)
			e.obtained(s, " " + name + "+2");
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.spawningPools == 0)
			return true;
		if (s.minerals >= 50 && !s.hasSupply(1))
			return true;
		return false;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildSpawningPool());
		return l;
	}
}
