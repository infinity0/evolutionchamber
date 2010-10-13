package com.fray.evo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EcActionBuildLair extends EcAction implements Serializable
{

	public void execute(final EcBuildOrder s,final EcEvolver e)
	{
		s.minerals -=150;
		s.gas -= 100;
		s.hatcheries -= 1;
		s.evolvinghatcheries +=1;
		s.addFutureAction(80,new Runnable(){
			@Override
			public void run()
			{
				if (e.debug) System.out.println("@"+s.seconds()+" Lairs+1");
				s.lairs +=1;
				s.evolvinghatcheries -=1;
			}});
	}

	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 150)
			return false;
		if (s.gas < 100)
			return false;
		if (s.hatcheries < 1)
			return false;
		return true;
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.hatcheries == 0)
			return true;
		if (s.spawningPools == 0)
			return true;
		return super.isInvalid(s);
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildSpawningPool());
		return l;
	}
	
}
