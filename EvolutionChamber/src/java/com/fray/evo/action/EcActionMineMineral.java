package com.fray.evo.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;

public class EcActionMineMineral extends EcAction implements Serializable
{
	@Override
	public void execute(final EcBuildOrder s, final EcEvolver e)
	{
		s.dronesGoingOnMinerals += 1;
		s.dronesOnGas -= 1;
		s.addFutureAction(2, new Runnable()
		{
			@Override
			public void run()
			{
				if (e.debug)
					e.mining(s," +1 on mineral");
				s.dronesGoingOnMinerals--;
				s.dronesOnMinerals++;
			}
		});
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s) {
		if (s.dronesOnGas != 0)
			return false;
		return true;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.dronesOnGas != 0)
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