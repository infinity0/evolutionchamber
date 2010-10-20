package com.fray.evo.action.upgrade;

import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.build.EcActionBuildHydraliskDen;

public class EcActionUpgradeGroovedSpines extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(150, 150, 80, "Grooved Spines");
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.hydraliskDen == 0)
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.groovedSpines = true;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildHydraliskDen());
		return l;
	}
}