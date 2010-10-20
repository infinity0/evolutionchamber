package com.fray.evo.action.upgrade;

import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.build.EcActionBuildInfestationPit;

public class EcActionUpgradePathogenGlands extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(150, 150, 110, "Pathogen Glands");
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		// TODO Auto-generated method stub
		super.execute(s, e);
		s.infestationPitInUse++;
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.infestationPit-s.infestationPitInUse == 0)
			return true;
		return false;
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.pathogenGlands = true;
		s.infestationPitInUse--;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildInfestationPit());
		return l;
	}
}