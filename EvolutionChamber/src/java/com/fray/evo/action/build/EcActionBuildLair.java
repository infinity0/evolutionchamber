package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.action.EcAction;

public class EcActionBuildLair extends EcAction implements Serializable
{

	@Override
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
				if (e.debug) e.log.println("@"+s.timestamp()+" Lairs+1");
				s.lairs +=1;
				s.evolvinghatcheries -=1;
			}});
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 150)
			return false;
		if (s.gas < 100)
			return false;
		if (s.hatcheries<=s.queensBuilding)
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
