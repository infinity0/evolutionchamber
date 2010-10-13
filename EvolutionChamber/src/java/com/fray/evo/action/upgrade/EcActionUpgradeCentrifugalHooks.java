package com.fray.evo.action.upgrade;

import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.build.EcActionBuildBanelingNest;

public class EcActionUpgradeCentrifugalHooks extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(150, 150, 110, "Centrifugal Hooks");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.banelingNest == 0)
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.centrifugalHooks = true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildBanelingNest());
		return l;
	}
}