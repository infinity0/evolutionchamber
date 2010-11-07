package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildCorruptor extends EcActionBuildUnit implements Serializable
{	
	public EcActionBuildCorruptor()
	{
		super(150, 100, 2, 40, "Corruptor", true);
	}

	protected void postExecute(final EcBuildOrder s, final EcEvolver e)
	{
		s.corruptors += 1;
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.spire == 0 && s.greaterSpire == 0 && s.evolvingSpires == 0)
			return true;
		return false;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildSpire());
		return l;
	}
}
