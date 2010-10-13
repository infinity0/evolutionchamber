package com.fray.evo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EcActionBuildHive extends EcAction implements Serializable
{

	public void execute(final EcBuildOrder s,final EcEvolver e)
	{
		s.minerals -=200;
		s.gas -= 150;
		s.lairs -= 1;
		s.evolvinghatcheries +=1;
		s.addFutureAction(100,new Runnable(){
			@Override
			public void run()
			{
				if (e.debug) System.out.println("@"+s.seconds()+" Hives+1");
				s.hives +=1;
				s.evolvingLairs -=1;
			}});
	}

	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 200)
			return false;
		if (s.gas < 150)
			return false;
		if (s.lairs < 1)
			return false;
		return true;
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.lairs == 0)
			return true;
		if (s.infestationPit == 0)
			return true;
		return super.isInvalid(s);
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildInfestationPit());
		return l;
	}
	
}
