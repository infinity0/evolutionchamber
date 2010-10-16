package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.action.EcAction;

public class EcActionBuildRoach extends EcAction implements Serializable
{
	@Override
	public void execute(final EcBuildOrder s,final EcEvolver e)
	{
		s.minerals -=75;
		s.gas -=25;
		s.consumeLarva(e);
		s.supplyUsed += 2;
		s.addFutureAction(27,new Runnable(){
			@Override
			public void run()
			{
				if (e.debug) e.log.println("@"+s.timestamp()+" Roach+1");
				s.roaches +=1;
			}});
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.roachWarrens == 0)
			return true;
		return false;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 75)
			return false;
		if (s.gas < 25)
			return false;
		if (s.larva < 1)
			return false;
		if (!s.hasSupply(2))
			return false;
		return true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildRoachWarren());
		return l;
	}
}
