package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildRoachWarren extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildRoachWarren()
	{
		super(150, 0, 55, "Roach Warren");
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
		s.roachWarrens += 1;
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.spawningPools == 0)
			return true;
		if (s.roachWarrens >= 1)
			return true;
		return false;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildSpawningPool());
		destination.spawningPools = Math.min(destination.spawningPools, 1);
		return l;
	}

}
