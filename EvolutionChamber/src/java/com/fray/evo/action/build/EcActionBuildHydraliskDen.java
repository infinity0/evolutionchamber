package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildHydraliskDen extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildHydraliskDen()
	{
		super(100, 100, 40, "Hydralisk Den");
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{	
		s.hydraliskDen += 1;
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.lairs == 0 && s.evolvingLairs == 0 && s.hives == 0 && s.evolvingHives == 0)
			return true;
		if (s.hydraliskDen == 1)
			return true;
		return super.isInvalid(s);
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildLair());
		return l;
	}
}
