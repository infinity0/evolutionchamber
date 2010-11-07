package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildBroodLord extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildBroodLord()
	{
		super(150, 150, 2, 34, "Brood Lord", false);
	}

	protected void preExecute(final EcBuildOrder s)
	{
		s.corruptors -= 1;
	}

	protected void postExecute(final EcBuildOrder s, final EcEvolver e)
	{
		s.broodlords += 1;
	}
	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.corruptors < 1)
			return false;
		return isPossibleResources(s);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.hives == 0 && s.evolvingHives == 0)
			return true;
		if (s.greaterSpire == 0)
			return true;
		if (!s.hasSupply(2))
			return true;
		return super.isInvalid(s);
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildGreaterSpire());
		l.add(new EcActionBuildCorruptor());
		destination.hives = Math.max(destination.hives,1);
		destination.greaterSpire = Math.max(destination.greaterSpire,1);
		return l;
	}
}
