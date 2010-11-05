package com.fray.evo.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcSettings;
import com.fray.evo.EcState;

public class EcActionMineGas extends EcAction implements Serializable
{
	@Override
	public void execute(final EcBuildOrder s, final EcEvolver e)
	{
		if (s.settings.pullThreeWorkersOnly) 
		{
			s.dronesGoingOnGas += 3;
			s.dronesOnMinerals -= 3;
		}
		else
		{
			s.dronesGoingOnGas += 1;
			s.dronesOnMinerals -= 1;
		}
		s.addFutureAction(2, new Runnable()
		{
			@Override
			public void run()
			{
				if (s.settings.pullThreeWorkersOnly) 
				{
					if (e.debug)
						e.mining(s," +3 on gas");
					s.dronesGoingOnGas -= 3;
					s.dronesOnGas += 3;
				}
				else
				{
					if (e.debug)
						e.mining(s," +1 on gas");
					s.dronesGoingOnGas--;
					s.dronesOnGas++;
				}

			}
		});
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s) {
		return !isPossible(s);
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