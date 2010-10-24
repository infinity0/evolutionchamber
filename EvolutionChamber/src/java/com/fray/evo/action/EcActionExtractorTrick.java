package com.fray.evo.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;

public class EcActionExtractorTrick extends EcAction implements Serializable
{
	@Override
	public void execute(final EcBuildOrder s, final EcEvolver e)
	{
		s.minerals -= 25;
		s.drones -= 1;
		s.dronesOnMinerals -= 1;
		s.supplyUsed -= 1;
		s.extractorsBuilding++;
		s.addFutureAction(2, new Runnable()
		{
			@Override
			public void run()
			{
				if (e.debug)
					e.obtained(s," Extractor Trick Finished, Drone Restored");
				s.minerals += 19;
				s.drones += 1;
				s.dronesOnMinerals += 1;
				s.supplyUsed += 1;
				s.extractorsBuilding--;
			}
		});
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.gasExtractors + s.extractorsBuilding >= s.extractors())
			return true;
		if (s.supplyUsed < s.supply() - 1)
			return true;
		return false;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 75)
			return false;
		if (s.drones < 1)
			return false;
		if ((s.extractors() + s.extractorsBuilding) >= s.bases()*2)
			return false;
		return true;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		return l;
	}

}