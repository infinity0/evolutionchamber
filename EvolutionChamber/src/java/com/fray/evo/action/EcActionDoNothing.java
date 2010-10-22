package com.fray.evo.action;

import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;

public class EcActionDoNothing extends EcAction
{

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		return true;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		return new ArrayList<EcAction>();
	}

}
