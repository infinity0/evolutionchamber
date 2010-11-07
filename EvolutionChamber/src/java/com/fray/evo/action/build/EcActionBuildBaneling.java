package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildBaneling extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildBaneling()
	{
		super(25,25,0,20,"Baneling",false);
	}

	public void postExecute(final EcBuildOrder s, EcEvolver e)
	{
		s.banelings += 1;
	}
	
	public void preExecute(final EcBuildOrder s)
	{
		s.zerglings -= 1;
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
		if (s.zerglings < 1)
			return false;
		return isPossibleResources(s);
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
