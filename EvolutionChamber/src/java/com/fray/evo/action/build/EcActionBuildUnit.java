package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;

public abstract class EcActionBuildUnit extends EcActionBuild implements Serializable
{
	public int supply;
	public boolean consumeLarva;
	
	public EcActionBuildUnit(int minerals, int gas, int supply, int time, String name, boolean consumeLarva)
	{
		super(minerals, gas, time, name);
		this.supply = supply;
		this.consumeLarva = consumeLarva;
	}

	@Override
	public void execute(final EcBuildOrder s, final EcEvolver e)
	{
		s.minerals -= minerals;
		s.gas -= gas;
		if (consumeLarva)
			s.consumeLarva(e);
		preExecute(s);
		s.addFutureAction(time, new Runnable()
		{
			@Override
			public void run()
			{
				obtainOne(s, e);
				postExecute(s,e);
			}
		});
	}
	@Override
	protected boolean isPossibleResources(EcBuildOrder s)
	{
		if (!s.hasSupply(supply))
			return false;
		if (consumeLarva)
			if (s.larva < 1)
				return false;
		return super.isPossibleResources(s);
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		return isPossibleResources(s);
	}
	
	protected abstract void postExecute(EcBuildOrder s, EcEvolver e);

	protected void preExecute(EcBuildOrder s){}

}
