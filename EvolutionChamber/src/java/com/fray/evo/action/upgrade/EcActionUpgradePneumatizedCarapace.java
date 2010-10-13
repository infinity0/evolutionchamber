package com.fray.evo.action.upgrade;

import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.build.EcActionBuildLair;

public class EcActionUpgradePneumatizedCarapace extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(100, 100, 60, "Pneumatized Carapace");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.lairs == 0 && s.evolvingLairs == 0 && s.hives == 0)
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.pneumatizedCarapace = true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildLair());
		return l;
	}
}