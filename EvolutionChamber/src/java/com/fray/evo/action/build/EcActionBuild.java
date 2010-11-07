package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.action.EcAction;

public abstract class EcActionBuild extends EcAction implements Serializable
{
	public int minerals;
	public int gas;
	public int time;
	public String	name;
	
	public EcActionBuild(int minerals,int gas,int time,String name)
	{
		this.minerals = minerals;
		this.gas = gas;
		this.time = time;
		this.name = name;
	}
	protected boolean isPossibleResources(EcBuildOrder s)
	{
		if (s.minerals < minerals)
			return false;
		if (s.gas < gas)
			return false;
		return true;
	}
	protected void obtainOne(final EcBuildOrder s, EcEvolver e)
	{
		if (e.debug)
			e.obtained(s, " " + name +"+1");
	}
	
}
