package com.fray.evo.action.upgrade;

import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.build.EcActionBuildEvolutionChamber;
import com.fray.evo.action.build.EcActionBuildHive;

public class EcActionUpgradeMissile3 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(200, 200, 220, "Missile +3");
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.evolutionChambers == 0)
			return true;
		if (s.hives == 0 && s.evolvingHives == 0)
			return true;
		if (s.missile2 == false)
			return true;
		return false;
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.evolutionChambersInUse++;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.evolutionChambersInUse == s.evolutionChambers)
			return false;
		return super.isPossible(s);
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.missile3 = true;
		s.evolutionChambersInUse--;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildEvolutionChamber());
		l.add(new EcActionBuildHive());
		l.add(new EcActionUpgradeMissile2());
		return l;
	}
}