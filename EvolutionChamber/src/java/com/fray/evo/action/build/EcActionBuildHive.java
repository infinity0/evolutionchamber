package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.action.EcAction;

public class EcActionBuildHive extends EcAction implements Serializable
{

	@Override
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
				if (e.debug) System.out.println("@"+s.timestamp()+" Hives+1");
				s.hives +=1;
				s.evolvingLairs -=1;
			}});
	}

	@Override
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
