package com.fray.evo.action.upgrade;

import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.build.EcActionBuildLair;
import com.fray.evo.action.build.EcActionBuildRoachWarren;

public class EcActionUpgradeGlialReconstitution extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(100, 100, 110, "Glial Reconstitution");
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.roachWarrens == 0)
			return true;
		if (s.lairs == 0)// Need to account for evolving lairs->hives
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.glialReconstitution = true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildRoachWarren());
		l.add(new EcActionBuildLair());
		return l;
	}
}