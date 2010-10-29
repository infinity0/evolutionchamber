package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcSettings;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildHatchery extends EcAction implements Serializable
{
	@Override
	public void execute(final EcBuildOrder s, final EcEvolver e)
	{
		s.minerals -= 300;
		s.drones -= 1;
		s.dronesOnMinerals -= 1;
		s.supplyUsed -= 1;
		s.hatcheriesBuilding += 1;
		s.addFutureAction(70, new Runnable()
		{
			@Override
			public void run()
			{
			}
		});
		s.addFutureAction(100, new Runnable()
		{
			@Override
			public void run()
			{
				if (e.debug)
					e.obtained(s," Hatchery+1");
				s.hatcheries += 1;
				s.hatcheriesBuilding -= 1;
				s.hatcheryTimes.add(new Integer(s.seconds));
			}
		});
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s) {
		if(s.supplyUsed < EcSettings.minimumHatcherySupply)
			return true;
		return false;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 300)
			return false;
		if (s.drones < 1)
			return false;
		return true;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		return l;
	}

}