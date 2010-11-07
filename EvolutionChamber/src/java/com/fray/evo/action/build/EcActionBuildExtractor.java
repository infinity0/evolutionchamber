package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.action.EcActionMineGas;
import com.fray.evo.action.EcActionMineMineral;

public class EcActionBuildExtractor extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildExtractor()
	{
		super(25, 0, 30, "Extractor");
	}

	@Override
	protected void preExecute(EcBuildOrder s)
	{
		s.extractorsBuilding++;
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
		if (s.extractorsBuilding == 0)
			throw new RuntimeException("wtf?");
		s.gasExtractors += 1;
		if (s.settings.pullWorkersFromGas == false)
		{
			s.dronesOnMinerals -= 3;
			s.dronesOnGas += 3;
		}
		s.extractorsBuilding--;
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.gasExtractors + s.extractorsBuilding >= s.extractors())
			return true;
		if (s.supplyUsed < s.settings.minimumExtractorSupply)
			return true;
		return false;
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
