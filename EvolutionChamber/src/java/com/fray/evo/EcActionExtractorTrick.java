package com.fray.evo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EcActionExtractorTrick extends EcAction implements Serializable
{
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
					System.out.println("@" + s.seconds() + " Extractor Trick, Drone+1");
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
		if (s.extractors() < s.gasExtractors+s.extractorsBuilding)
			return true;
		return false;
	}

	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 25)
			return false;
		if (s.drones < 1)
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