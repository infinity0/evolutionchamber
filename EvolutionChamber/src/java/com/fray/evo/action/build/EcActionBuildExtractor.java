package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcSettings;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.EcActionMineGas;
import com.fray.evo.action.EcActionMineMineral;

public class EcActionBuildExtractor extends EcAction implements Serializable
{
	@Override
	public void execute(final EcBuildOrder s, final EcEvolver e)
	{
		s.minerals -= 25;
		s.drones -= 1;
		s.dronesOnMinerals -= 1;
		s.supplyUsed -= 1;
		s.extractorsBuilding++;
		s.addFutureAction(30, new Runnable()
		{
			@Override
			public void run()
			{
				if (s.extractorsBuilding == 0)
					return;
				if (e.debug)
					e.obtained(s, " Extractor+1");
				s.gasExtractors += 1;
				if (s.settings.pullWorkersFromGas == false)
				{
					s.dronesOnMinerals -= 3;
					s.dronesOnGas += 3;
				}
				s.extractorsBuilding--;
			}
		});
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.gasExtractors + s.extractorsBuilding >= s.extractors())
			return true;
		if(s.supplyUsed < s.settings.minimumExtractorSupply)
			return true;
		return false;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < 25)
			return false;
		if (s.drones < 1)
			return false;
		return true;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		if (destination.settings.pullWorkersFromGas)
		{
			l.add(new EcActionMineGas());
			l.add(new EcActionMineMineral());
		}
		return l;
	}

}
