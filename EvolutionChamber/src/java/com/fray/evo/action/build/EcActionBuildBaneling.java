package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildBaneling extends EcAction implements Serializable
{
	@Override
	public void execute(final EcBuildOrder s, final EcEvolver e)
	{
		s.minerals -= 25;
		s.gas -= 25;
		s.zerglings -= 1;
		s.addFutureAction(20, new Runnable()
		{
			@Override
			public void run()
			{
				if (e.debug)
					e.obtained(s, " Baneling+1");
				s.banelings += 1;
			}
		});
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.banelingNest == 0)
			return true;
		return false;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 25)
			return false;
		if (s.gas < 25)
			return false;
		if (s.zerglings < 1)
			return false;
		return true;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildBanelingNest());
		l.add(new EcActionBuildZergling());
		return l;
	}

}
