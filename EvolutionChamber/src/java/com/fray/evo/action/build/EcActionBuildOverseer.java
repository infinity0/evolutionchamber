package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.action.EcAction;

public class EcActionBuildOverseer extends EcAction implements Serializable
{
	@Override
	public void execute(final EcBuildOrder s,final EcEvolver e)
	{
		s.minerals -=50;
		s.gas -=100;
		s.overlords -=1;
		s.addFutureAction(17,new Runnable(){
			@Override
			public void run()
			{
				if (e.debug) e.log.println("@"+s.timestamp()+" Overseer+1");
				s.overseers +=1;
			}});
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.lairs == 0)
			return true;
		return false;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 50)
			return false;
		if (s.gas < 100)
			return false;
		if (s.overlords < 1)
			return false;
		return true;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildLair());
		return l;
	}

}
