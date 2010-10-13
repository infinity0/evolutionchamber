package com.fray.evo.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;

public class EcActionWait extends EcAction implements Serializable
{
	boolean go = false;
	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		s.waits +=1;
	}
	
	@Override
	public boolean canExecute(EcBuildOrder s)
	{
		if (isPossible(s)) return true;
		s.seconds += 1;
		Collection<Runnable> futureActions = s.getFutureActions(s.seconds);
		if (futureActions != null)
			for (Runnable r : futureActions)
			{
				r.run();
				go = true;
			}
		s.accumulateMaterials();
		return false;
	}
	
	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		return go;
	}

	@Override
	public List<EcAction> requirements()
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		return l;
	}

}
