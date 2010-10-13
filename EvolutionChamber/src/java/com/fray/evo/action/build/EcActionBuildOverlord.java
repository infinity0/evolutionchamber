package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.action.EcAction;

public class EcActionBuildOverlord extends EcAction implements Serializable
{
	public void execute(final EcBuildOrder s,final EcEvolver e)
	{
		s.minerals -= 100;
		s.consumeLarva(e);
		s.addFutureAction(25,new Runnable(){
			@Override
			public void run()
			{
				if (e.debug) System.out.println("@"+s.timestamp()+" Overlord+1");
				s.overlords +=1;
			}});
	}

	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 100)
			return false;
		if (s.larva < 1)
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
