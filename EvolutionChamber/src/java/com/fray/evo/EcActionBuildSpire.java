package com.fray.evo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EcActionBuildSpire extends EcAction implements Serializable
{

	@Override
	public void execute(final EcBuildOrder s, final EcEvolver e)
	{
		s.minerals -= 200;
		s.gas -= 200;
		s.drones -= 1;
		s.dronesOnMinerals -= 1;
		s.supplyUsed -= 1;
		s.addFutureAction(100, new Runnable()
		{
			@Override
			public void run()
			{
				if (e.debug)
					System.out.println("@" + s.seconds() + " Spire+1");
				s.spire += 1;
			}
		});
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 200)
			return false;
		if (s.gas < 200)
			return false;
		if (s.drones < 1)
			return false;
		return true;
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.lairs == 0)
			return true;
		return super.isInvalid(s);
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildLair());
		return l;
	}
}
