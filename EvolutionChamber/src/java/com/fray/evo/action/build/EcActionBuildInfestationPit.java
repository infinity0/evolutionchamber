package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.action.EcAction;

public class EcActionBuildInfestationPit extends EcAction implements Serializable
{
	private static final int	time	= 50;
	private static final int	minerals	= 100;
	private static final int	gas	= 100;

	@Override
	public void execute(final EcBuildOrder s, final EcEvolver e)
	{
		s.minerals -= minerals;
		s.gas -= gas;
		s.drones -= 1;
		s.dronesOnMinerals -= 1;
		s.supplyUsed -= 1;
		s.addFutureAction(time, new Runnable()
		{
			@Override
			public void run()
			{
				if (e.debug)
					System.out.println("@" + s.timestamp() + " Infestation Pit+1");
				s.infestationPit += 1;
			}
		});
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < minerals)
			return false;
		if (s.gas < gas)
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
