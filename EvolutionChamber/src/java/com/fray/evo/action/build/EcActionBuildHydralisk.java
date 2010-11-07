package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildHydralisk extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildHydralisk()
	{
		super(100, 50, 2, 33, "Hydralisk", true);
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
		s.hydralisks += 1;
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.hydraliskDen == 0)
			return true;
		return false;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildHydraliskDen());
		destination.hydraliskDen = Math.max(destination.hydraliskDen, 1);
		return l;
	}
}
