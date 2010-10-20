package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildQueen extends EcAction implements Serializable
{
	@Override
	public void execute(final EcBuildOrder s, final EcEvolver e)
	{
		s.supplyUsed += 2;
		s.minerals -= 150;
		s.consumeHatch(50);
		s.addFutureAction(50, new Runnable()
		{

			@Override
			public void run()
			{
				if (e.debug)
					e.obtained(s," Queen+1");
				s.queens += 1;
				s.addFutureAction(45, new Runnable()
				{
					@Override
					public void run()
					{
						if (e.debug)
							e.obtained(s," Larva+4");
						s.larva += 4;
						s.addFutureAction(45, this);
					}
				});
			}
		});
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.spawningPools == 0)
			return true;
		if (s.queensBuilding + s.queens >= s.hatcheriesBuilding + s.bases())
			return true;
		if (s.hatcheries + s.lairs + s.hives == 0)
			return true;
		return false;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 150)
			return false;
		if (s.hasSupply(2) == false)
			return false;
		return true;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildSpawningPool());
		return l;
	}

}
