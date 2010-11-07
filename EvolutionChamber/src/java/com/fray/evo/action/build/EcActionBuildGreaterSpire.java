package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildGreaterSpire extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildGreaterSpire()
	{
		super(100, 150, 100, "Greater Spire");
		takesDrone = false;
	}

	@Override
	protected void preExecute(EcBuildOrder s)
	{
		s.evolvingSpires += 1;
		s.spire -= 1;
	}
	
	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
		s.greaterSpire += 1;
		s.evolvingSpires -= 1;
	}
	
	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.spire < 1)
			return false;
		return super.isPossible(s);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.hives == 0 && s.evolvingHives == 0)
			return true;
		if (s.spire == 0)
			return true;
		if (s.greaterSpire == 1)
			return true;
		return super.isInvalid(s);
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildSpire());
		l.add(new EcActionBuildHive());
		return l;
	}

}
