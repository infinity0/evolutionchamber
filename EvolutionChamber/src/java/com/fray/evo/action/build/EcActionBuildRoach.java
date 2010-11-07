package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildRoach extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildRoach()
	{
		super(75, 25, 2, 27, "Roach", true);
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
		s.roaches += 1;
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.roachWarrens == 0)
			return true;
		return false;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildRoachWarren());
		l.add(new EcActionBuildExtractor());
		destination.roachWarrens = Math.max(destination.roachWarrens, 1);
		return l;
	}
}
