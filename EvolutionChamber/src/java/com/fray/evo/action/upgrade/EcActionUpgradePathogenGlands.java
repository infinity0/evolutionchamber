package com.fray.evo.action.upgrade;

import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.build.EcActionBuildInfestationPit;

public class EcActionUpgradePathogenGlands extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(150, 150, 110, "Pathogen Glands");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.infestationPit == 0)
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.pathogenGlands = true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildInfestationPit());
		return l;
	}
}