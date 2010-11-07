package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildOverseer extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildOverseer()
	{
		super(50, 100, 0, 17, "Overseer", false);
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
		s.overlords -= 1;
		s.overseers += 1;
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.lairs == 0 && s.evolvingLairs == 0 && s.hives == 0 && s.evolvingHives == 0)
			return true;
		return false;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.overlords < 1)
			return false;
		return super.isPossible(s);
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildLair());
		return l;
	}

}
