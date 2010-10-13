package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.action.EcAction;

public class EcActionBuildUltralisk extends EcAction implements Serializable
{
	private static final int	time		= 70;
	private static final int	supply		= 6;
	private static final int	gas			= 200;
	private static final int	minerals	= 300;

	public void execute(final EcBuildOrder s, final EcEvolver e)
	{
		s.minerals -= minerals;
		s.gas -= gas;
		s.consumeLarva(e);
		s.supplyUsed += supply;
		s.addFutureAction(time, new Runnable()
		{
			@Override
			public void run()
			{
				if (e.debug)
					System.out.println("@" + s.timestamp() + " Ultralisk+1");
				s.ultralisks += 1;
			}
		});
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.spire == 0 && s.evolvingSpires == 0 && s.greaterSpire == 0)
			return true;
		return false;
	}

	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < minerals)
			return false;
		if (s.gas < gas)
			return false;
		if (s.larva < 1)
			return false;
		if (!s.hasSupply(supply))
			return false;
		return true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildUltraliskCavern());
		return l;
	}
}
