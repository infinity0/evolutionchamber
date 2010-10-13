package com.fray.evo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class EcActionUpgrade extends EcAction implements Serializable
{
	public int minerals = 0;
	public int gas = 0;
	public int time = 0;
	public String name = "EROAR";
	
	public EcActionUpgrade()
	{
		init();
	}
	
	@Override
	public void execute(final EcBuildOrder s, final EcEvolver e)
	{
		s.minerals -= minerals;
		s.gas -= gas;
		s.addFutureAction(time, new Runnable()
		{
			@Override
			public void run()
			{
				if (e.debug) System.out.println("@" + s.seconds() + " "+name);
				afterTime(s,e);
			}
		});
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.minerals < minerals)
			return false;
		if (s.gas < gas)
			return false;
		return true;
	}
	
	public abstract void init();
	protected void init(int minerals, int gas, int time, String name)
	{
		this.minerals = minerals;
		this.gas = gas;
		this.time = time;
		this.name = name;
	}
	
	@Override
	public String toString()
	{
		return name;
	}

	public abstract void afterTime(EcBuildOrder s, EcEvolver e);

}
