package com.fray.evo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EcActionBuildHatchery extends EcAction implements Serializable
{
	@Override
	public void execute(final EcBuildOrder s, final EcEvolver e)
	{
		s.minerals -= 300;
		s.drones -=1;
		s.dronesOnMinerals -=1;
		s.supplyUsed -=1;
		s.addFutureAction(100,new Runnable(){
			@Override
			public void run()
			{
				if (e.debug) System.out.println("@"+s.seconds()+" Hatchery+1");
				s.hatcheries +=1;
			}});
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 300)
			return false;
		if (s.drones < 1)
			return false;
		return true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		return l;
	}

}
