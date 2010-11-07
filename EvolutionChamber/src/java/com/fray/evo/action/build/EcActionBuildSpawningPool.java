package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildSpawningPool extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildSpawningPool()
	{
		super(200, 0, 65, "Spawning Pool");
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
		s.spawningPools +=1;
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.spawningPools >= 1)
			return true;
		if(s.supplyUsed < s.settings.minimumPoolSupply)
			return true;
		return super.isInvalid(s);
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		return l;
	}
}
