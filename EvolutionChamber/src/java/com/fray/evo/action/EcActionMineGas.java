package com.fray.evo.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;

public class EcActionMineGas extends EcAction implements Serializable
{
	@Override
	public void execute(final EcBuildOrder s, final EcEvolver e)
	{
		s.dronesGoingOnGas += 1;
		s.dronesOnMinerals -= 1;
		s.addFutureAction(2, new Runnable()
		{
			@Override
			public void run()
			{
				if (e.debug)
					e.mining(s," +1 on gas");
				s.dronesGoingOnGas--;
				s.dronesOnGas++;
			}
		});
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if ((s.dronesOnGas+s.dronesGoingOnGas) >= 3*s.gasExtractors)
			return false;
		if (s.dronesOnMinerals == 0)
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