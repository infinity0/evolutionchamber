package com.fray.evo.action.upgrade;

import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.build.EcActionBuildUltraliskCavern;

public class EcActionUpgradeChitinousPlating extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(150, 150, 110, "Chitinous Plating");
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.ultraliskCavern == 0)
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.chitinousPlating = true;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildUltraliskCavern());
		return l;
	}
}