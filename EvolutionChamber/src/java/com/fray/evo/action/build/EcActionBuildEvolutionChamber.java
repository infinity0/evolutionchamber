package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildEvolutionChamber extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildEvolutionChamber()
	{
		super(75, 0, 35, "Evolution Chamber");
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
		s.evolutionChambers += 1;
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.evolutionChambers == 3)
			return true;
		return super.isInvalid(s);
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		return l;
	}
}
