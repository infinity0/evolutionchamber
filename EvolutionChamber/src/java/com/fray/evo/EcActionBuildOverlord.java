package com.fray.evo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
				if (e.debug) System.out.println("@"+s.seconds()+" Overlord+1");
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
