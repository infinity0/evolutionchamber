package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildHive extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildHive()
	{
		super(200, 150, 100, "Hive");
		takesDrone = false;
	}

	@Override
	protected void preExecute(EcBuildOrder s)
	{
		s.lairs -= 1;
		s.evolvingLairs += 1;
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
		s.hives += 1;
		s.evolvingLairs -= 1;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.lairs < 1)
			return false;
		return super.isPossible(s);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.lairs == 0)
			return true;
		if (s.infestationPit == 0)
			return true;
		return super.isInvalid(s);
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildInfestationPit());
		return l;
	}
}
