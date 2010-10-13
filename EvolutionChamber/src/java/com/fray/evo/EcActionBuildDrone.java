package com.fray.evo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EcActionBuildDrone extends EcAction implements Serializable
{
	public void execute(final EcBuildOrder s,final EcEvolver e)
	{
		s.minerals -=50;
		s.consumeLarva(e);
		s.supplyUsed += 1;
		s.addFutureAction(17,new Runnable(){
			@Override
			public void run()
			{
				if (e.debug) System.out.println("@"+s.seconds()+" Drone+1");
				s.drones +=1;
				s.dronesOnMinerals +=1;
			}});
	}

	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 50)
			return false;
		if (s.larva < 1)
			return false;
		if (!s.hasSupply(1))
			return false;
		return true;
	}

	@Override
	public List<EcAction> requirements()
	{
		return new ArrayList();
	}
	
}
