package com.fray.evo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EcActionBuildRoach extends EcAction implements Serializable
{
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
				if (e.debug) System.out.println("@"+s.seconds()+" Roach+1");
				s.roaches +=1;
			}});
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.roachWarrens == 0)
			return true;
		return false;
	}
	
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
