package com.fray.evo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EcActionBuildHydralisk extends EcAction implements Serializable
{

	public void execute(final EcBuildOrder s,final EcEvolver e)
	{
		s.minerals -=100;
		s.gas -=50;
		s.consumeLarva(e);
		s.supplyUsed += 2;
		s.addFutureAction(33,new Runnable(){
			@Override
			public void run()
			{
				if (e.debug) System.out.println("@"+s.seconds()+" Hydralisk+1");
				s.hydralisks +=1;
			}});
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.hydraliskDen == 0)
			return true;
		return false;
	}
	
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 100)
			return false;
		if (s.gas < 50)
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
		l.add(new EcActionBuildHydraliskDen());
		return l;
	}
}
