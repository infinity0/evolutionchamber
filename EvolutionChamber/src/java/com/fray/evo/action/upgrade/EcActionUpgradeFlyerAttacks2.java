package com.fray.evo.action.upgrade;

import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.build.EcActionBuildSpire;

public class EcActionUpgradeFlyerAttacks2 extends EcActionUpgrade
{
	@Override
	public void init()
	{
		init(175, 175, 190, "Flyer Attacks +2");
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.spire == 0)
			return true;
		if (s.lairs == 0 && s.hives == 0 && s.evolvingLairs == 0 && s.evolvingHives == 0)
			return true;
		if (s.flyerAttack1 == false)
			return true;
		return false;
	}

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		super.execute(s, e);
		s.spiresInUse++;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.spiresInUse == s.spire)
			return false;
		return super.isPossible(s);
	}

	@Override
	public void afterTime(EcBuildOrder s, EcEvolver e)
	{
		s.flyerAttack2 = true;
		s.spiresInUse--;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildSpire());
		l.add(new EcActionUpgradeFlyerAttacks1());
		return l;
	}
}