package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildSpineCrawler extends EcActionBuildBuilding implements Serializable
{

	public EcActionBuildSpineCrawler()
	{
		super(100,0,50,"Spine Crawler");
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
		s.spineCrawlers += 1;
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.spawningPools == 0)
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
