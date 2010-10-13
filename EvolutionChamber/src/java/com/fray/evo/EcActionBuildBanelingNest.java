package com.fray.evo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EcActionBuildBanelingNest extends EcAction implements Serializable
{
	@Override
	public void execute(final EcBuildOrder s, final EcEvolver e)
	{
		s.minerals -= 100;
		s.gas -= 50;
		s.drones -= 1;
		s.dronesOnMinerals -= 1;
		s.supplyUsed -= 1;
		s.addFutureAction(60, new Runnable()
		{
			@Override
			public void run()
			{
				if (e.debug)
					System.out.println("@" + s.seconds() + " Baneling Nest+1");
				s.banelingNest += 1;
			}
		});
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.spawningPools == 0)
			return true;
		return false;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 100)
			return false;
		if (s.gas < 50)
			return false;
		if (s.drones < 1)
			return false;
		return true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildSpawningPool());
		return l;
	}
}
