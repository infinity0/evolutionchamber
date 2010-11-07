package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildMutalisk extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildMutalisk()
	{
		super(100, 100, 2, 33, "Mutalisk", true);
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
		s.mutalisks += 1;
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.spire == 0 && s.evolvingSpires == 0 && s.greaterSpire == 0)
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
