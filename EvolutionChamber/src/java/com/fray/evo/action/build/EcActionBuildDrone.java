package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildDrone extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildDrone()
	{
		super(50, 0, 1, 17, "Drone", true);
	}

	@Override
	protected void postExecute(final EcBuildOrder s, final EcEvolver e)
	{
		s.drones += 1;
		s.dronesGoingOnMinerals += 1;
		s.addFutureAction(2, new Runnable()
		{
			@Override
			public void run()
			{
				if (s.droneIsScouting == false && s.drones >= e.getDestination().scoutDrone
						&& e.getDestination().scoutDrone != 0)
				{
					s.droneIsScouting = true;
					if (e.debug)
						e.scout(s, " +1 Scouting Drone");
				}
				else
				{
					s.dronesGoingOnMinerals--;
					s.dronesOnMinerals++;
				}
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
	public List<EcAction> requirements(EcState destination)
	{
		return new ArrayList<EcAction>();
	}

}
