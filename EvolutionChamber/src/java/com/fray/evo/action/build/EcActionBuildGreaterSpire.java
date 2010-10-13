package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.action.EcAction;

public class EcActionBuildGreaterSpire extends EcAction implements Serializable
{

	@Override
	public void execute(final EcBuildOrder s,final EcEvolver e)
	{
		s.minerals -=100;
		s.gas -= 150;
		s.spire -= 1;
		s.evolvingSpires +=1;
		s.addFutureAction(100,new Runnable(){
			@Override
			public void run()
			{
				if (e.debug) System.out.println("@"+s.timestamp()+" Greater Spire+1");
				s.greaterSpire +=1;
				s.evolvingSpires -=1;
			}});
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 100)
			return false;
		if (s.gas < 150)
			return false;
		if (s.spire < 1)
			return false;
		return true;
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.hives == 0)
			return true;
		if (s.spire == 0)
			return true;
		return super.isInvalid(s);
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildSpire());
		l.add(new EcActionBuildHive());
		return l;
	}

}
