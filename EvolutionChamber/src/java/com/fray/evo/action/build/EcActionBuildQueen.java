package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildQueen extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildQueen()
	{
		super(150, 0, 2, 50, "Queen", false);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void preExecute(EcBuildOrder s)
	{
		s.consumeHatch(50);
	}
	
	@Override
	protected void postExecute(final EcBuildOrder s, final EcEvolver e)
	{
		s.queens += 1;
		if (s.hatcheriesSpawningLarva < s.bases())
		{
			s.hatcheriesSpawningLarva++;
			s.addFutureAction(45, new Runnable()
			{
				@Override
				public void run()
				{
					if (e.debug && s.larva < s.bases() * 19)
						e.obtained(s, " Larva+" + (Math.min(s.bases()*19,s.larva+4) - s.larva));
					s.larva = Math.min(s.bases()*19,s.larva+4);
					s.addFutureAction(45, this);
				}
			});
		}
		else
			s.addFutureAction(5, new Runnable()
			{
				@Override
				public void run()
				{
					if (s.hatcheriesSpawningLarva < s.bases())
					{
						s.hatcheriesSpawningLarva++;
						s.addFutureAction(45, new Runnable()
						{
							@Override
							public void run()
							{
								if (e.debug && s.larva < s.bases() * 19)
									e.obtained(s, " Larva+" + (Math.min(s.bases()*19,s.larva+4) - s.larva));
								s.larva = Math.min(s.bases()*19,s.larva+4);
								s.addFutureAction(45, this);
							}
						});
					}
					else
						s.addFutureAction(5, this);
				}
			});
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.spawningPools == 0)
			return true;
		if (s.hatcheries + s.lairs + s.hives == 0)
			return true;
		return false;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildSpawningPool());
		return l;
	}

}
