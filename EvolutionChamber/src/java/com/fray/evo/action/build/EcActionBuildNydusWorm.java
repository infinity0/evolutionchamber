package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildNydusWorm extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildNydusWorm()
	{
		super(100, 100, 20, "Nydus Worm");
		takesDrone = false;
	}

	@Override
	protected void preExecute(EcBuildOrder s)
	{
		s.nydusNetworkInUse += 1;
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
		s.nydusWorm += 1;
		s.nydusNetworkInUse -= 1;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.nydusNetworkInUse == s.nydusNetwork)
			return false;
		return super.isPossible(s);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.lairs == 0 && s.evolvingLairs == 0 && s.hives == 0 && s.evolvingHives == 0)
			return true;
		if (s.nydusNetwork == 0)
			return true;
		return super.isInvalid(s);
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildNydusNetwork());
		return l;
	}
}
