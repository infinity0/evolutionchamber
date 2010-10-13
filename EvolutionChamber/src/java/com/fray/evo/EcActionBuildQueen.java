package com.fray.evo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EcActionBuildQueen extends EcAction implements Serializable
{
	@Override
	public void execute(final EcBuildOrder s,final EcEvolver e)
	{
		s.supplyUsed+=2;
		s.minerals-=150;
		s.queensBuilding++;
		s.addFutureAction(50, new Runnable(){

			@Override
			public void run()
			{
				if (e.debug) System.out.println("@"+s.seconds()+" Queen+1");
				s.queens+=1;
				s.queensBuilding--;
				s.addFutureAction(45, new Runnable(){
					@Override
					public void run()
					{
						if (e.debug) System.out.println("@"+s.seconds()+" Larva+4");
						s.larva += 4;
						s.addFutureAction(40,this);
					}});
			}});
	}

	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.spawningPools == 0)
			return true;
		if (s.queensBuilding+s.queens >= s.bases())
			return true;
		return false;
	}
	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 150)
			return false;
		if (s.hasSupply(2) == false)
			return false;
		return true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildSpawningPool());
		return l;
	}

}
