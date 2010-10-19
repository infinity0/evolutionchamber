package com.fray.evo.action.upgrade;

import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.build.EcActionBuildLair;

public class EcActionUpgradeVentralSacs extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(200, 200, 130, "Ventral Sacs");
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.consumeHatch(time);
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.lairs == 0 && s.evolvingLairs == 0 && s.hives == 0 && s.evolvingHives == 0)
			return true;
		return false;
	}

	@Override
	public boolean isPossible(EcBuildOrder s) {
		if (s.hatcheries == 0 && s.lairs == 0 && s.hives == 0)
			return false;
		return super.isPossible(s);
	};

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.ventralSacs = true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildLair());
		return l;
	}
}