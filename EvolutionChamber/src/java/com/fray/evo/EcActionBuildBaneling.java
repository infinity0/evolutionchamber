package com.fray.evo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EcActionBuildBaneling extends EcAction implements Serializable
{
	public void execute(final EcBuildOrder s,final EcEvolver e)
	{
		s.minerals -=25;
		s.gas -=25;
		s.zerglings -=1;
		s.addFutureAction(20,new Runnable(){
			@Override
			public void run()
			{
				if (e.debug) System.out.println("@"+s.seconds()+" Baneling+1");
				s.banelings +=1;
			}});
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.banelingNest == 0)
			return true;
		return super.canExecute(s);
	}
	
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 25)
			return false;
		if (s.gas < 25)
			return false;
		if (s.zerglings < 1)
			return false;
		return true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildBanelingNest());
		return l;
	}
	
}
