package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildHatchery extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildHatchery()
	{
		super(300, 0, 100, "Hatchery");
	}

	@Override
	protected void preExecute(EcBuildOrder s)
	{
		s.hatcheriesBuilding += 1;
		s.addFutureAction(time - 30, new Runnable()
		{
			@Override
			public void run()
			{
			}
		});
		s.addFutureAction(time - 50, new Runnable()
		{
			@Override
			public void run()
			{
			}
		});
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
		s.hatcheries += 1;
		s.hatcheriesBuilding -= 1;
		s.hatcheryTimes.add(new Integer(s.seconds));
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.supplyUsed < s.settings.minimumHatcherySupply)
			return true;
		return false;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		return l;
	}

}