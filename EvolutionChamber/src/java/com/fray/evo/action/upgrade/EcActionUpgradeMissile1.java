package com.fray.evo.action.upgrade;

import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.build.EcActionBuildEvolutionChamber;
import com.fray.evo.action.build.EcActionBuildExtractor;

public class EcActionUpgradeMissile1 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(100, 100, 160, "Missile +1");
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.evolutionChambers == 0)
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
		s.missile1 = true;
		s.evolutionChambersInUse--;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildEvolutionChamber());
		l.add(new EcActionBuildExtractor());
		return l;
	}
}