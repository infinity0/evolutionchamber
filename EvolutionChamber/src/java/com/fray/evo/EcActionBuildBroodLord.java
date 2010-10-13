package com.fray.evo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EcActionBuildBroodLord extends EcAction implements Serializable
{
	public void execute(final EcBuildOrder s,final EcEvolver e)
	{
		s.minerals -=150;
		s.gas -= 150;
		s.corruptors -= 1;
		s.addFutureAction(34,new Runnable(){
			@Override
			public void run()
			{
				if (e.debug) System.out.println("@"+s.seconds()+" Brood Lord+1");
				s.broodlords +=1;
			}});
	}

	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 150)
			return false;
		if (s.gas < 150)
			return false;
		if (s.corruptors < 1)
			return false;
		return true;
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.hives == 0)
			return true;
		if (s.greaterSpire == 0)
			return true;
		return super.isInvalid(s);
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildGreaterSpire());
		return l;
	}
}
