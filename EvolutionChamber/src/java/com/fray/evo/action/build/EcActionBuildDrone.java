package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildDrone extends EcAction implements Serializable
{
	@Override
	public void execute(final EcBuildOrder s, final EcEvolver e)
	{
		s.minerals -= 50;
		s.consumeLarva(e);
		s.supplyUsed += 1;
		s.addFutureAction(17, new Runnable()
		{
			@Override
			public void run()
			{
				if (e.debug)
					e.obtained(s," Drone+1");
				s.drones += 1;
				s.dronesOnMinerals += 1;
			}
		});
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.minerals >= 50 && !s.hasSupply(1))
			return true;
		return super.isInvalid(s);
	}
	
	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 50)
			return false;
		if (s.larva < 1)
			return false;
		if (!s.hasSupply(1))
			return false;
		return true;
	}
	
	@Override
	public List<EcAction> requirements(EcState destination)
	{
		return new ArrayList<EcAction>();
	}

}
