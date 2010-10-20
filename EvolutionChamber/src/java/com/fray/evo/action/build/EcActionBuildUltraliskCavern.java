package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildUltraliskCavern extends EcAction implements Serializable
{
	private static final int	time		= 65;
	private static final int	minerals	= 150;
	private static final int	gas			= 200;

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
					e.obtained(s, " Ultralisk Cavern+1");
				s.ultraliskCavern += 1;
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
		if (s.hives == 0 && s.evolvingHives == 0)
			return true;
		if (s.ultraliskCavern == 1)
			return true;
		return super.isInvalid(s);
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildHive());
		return l;
	}
}
