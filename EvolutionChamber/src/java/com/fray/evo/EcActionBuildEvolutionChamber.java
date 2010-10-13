package com.fray.evo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EcActionBuildEvolutionChamber extends EcAction implements Serializable
{

	public void execute(final EcBuildOrder s,final EcEvolver e)
	{
		s.minerals -=75;
		s.drones -=1;
		s.dronesOnMinerals -=1;
		s.supplyUsed -=1;
		s.addFutureAction(35,new Runnable(){
			@Override
			public void run()
			{
				if (e.debug) System.out.println("@"+s.seconds()+" Evolution Chamber+1");
				s.evolutionChambers +=1;
			}});
	}

	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 75)
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
