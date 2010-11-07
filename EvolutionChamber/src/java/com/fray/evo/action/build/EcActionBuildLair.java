package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildLair extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildLair()
	{
		super(150, 100, 80, "Lair");
		takesDrone = false;
	}

	@Override
	protected void preExecute(EcBuildOrder s)
	{
		s.hatcheries -= 1;
		s.evolvingHatcheries += 1;
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
		s.lairs += 1;
		s.evolvingHatcheries -= 1;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.hatcheries <= s.queensBuilding)
			return false;
		return super.isPossible(s);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.hatcheries == 0)
			return true;
		if (s.spawningPools == 0)
			return true;
		return super.isInvalid(s);
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildSpawningPool());
		l.add(new EcActionBuildExtractor());
		destination.gasExtractors = Math.max(destination.gasExtractors, 1);
		return l;
	}

}
