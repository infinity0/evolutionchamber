package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;

public class EcActionBuildUltralisk extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildUltralisk()
	{
		super(300, 200, 6, 70, "Ultralisk", true);
	}

	@Override
	protected void postExecute(EcBuildOrder s, EcEvolver e)
	{
		s.ultralisks += 1;
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.ultraliskCavern == 0)
			return true;
		return false;
	}

	@Override
	public List<EcAction> requirements(EcState destination)
	{
		ArrayList<EcAction> l = new ArrayList<EcAction>();
		l.add(new EcActionBuildUltraliskCavern());
		destination.ultraliskCavern = Math.max(destination.ultraliskCavern, 1);
		return l;
	}
}
