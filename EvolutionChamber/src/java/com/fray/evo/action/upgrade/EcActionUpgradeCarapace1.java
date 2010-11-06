package com.fray.evo.action.upgrade;

import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.build.EcActionBuildEvolutionChamber;
import com.fray.evo.action.build.EcActionBuildExtractor;

public class EcActionUpgradeCarapace1 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(150, 150, 160, "Carapace +1");
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.evolutionChambers == 0)
			return true;
		if (s.armor1 == true)
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
		s.armor1 = true;
		s.evolutionChambersInUse--;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildEvolutionChamber());
		l.add(new EcActionBuildExtractor());
		return l;
	}
}