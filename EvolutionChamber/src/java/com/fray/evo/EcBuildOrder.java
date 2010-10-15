package com.fray.evo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.map.MultiValueMap;

import com.fray.evo.action.EcAction;

public class EcBuildOrder extends EcState implements Serializable
{
	static final long		serialVersionUID	= 1L;
	public int				larva				= 3;
	public int				dronesOnMinerals	= 6;
	public int				dronesOnGas			= 0;
	boolean					buildingLarva		= false;
	public int				evolvingLairs;
	public int				evolvingSpires		= 0;
	public int				queensBuilding		= 0;
	public int				spiresInUse			= 0;
	public int				evolutionChambersInUse;

	transient MultiValueMap	futureAction		= new MultiValueMap();
	ArrayList<EcAction>		actions				= new ArrayList<EcAction>();

	@Override
	public EcBuildOrder clone() throws CloneNotSupportedException
	{
		EcBuildOrder s = new EcBuildOrder();
		s.larva = larva;
		s.dronesOnMinerals = dronesOnMinerals;
		s.dronesOnGas = dronesOnGas;
		s.buildingLarva = buildingLarva;
		s.queensBuilding = queensBuilding;
		s.evolutionChambersInUse = evolutionChambersInUse;
		assign(s);
		return s;
	}

	@Override
	public String toString()
	{
		return ("@" + timestamp() + " M:" + (int) minerals + " G:" + (int) gas + " L:" + larva + " S:" + supplyUsed + "/" + supply());
	}

	public List<EcAction> getActions()
	{
		return actions;
	}

	public void addAction(EcAction ecActionBuildDrone)
	{
		actions.add(ecActionBuildDrone);
	}

	public void addFutureAction(double time, Runnable runnable)
	{
		time = seconds + time;
		futureAction.put(time, runnable);
		actionLength++;
	}

	public Collection<Runnable> getFutureActions(double time)
	{
		Object result = futureAction.get(time);
		if (result == null)
			return null;
		return (Collection<Runnable>) result;
	}

	public void consumeLarva(final EcEvolver e)
	{
		larva -= 1;
		if (!buildingLarva)
		{
			buildingLarva = true;
			addFutureAction(15, new Runnable()
			{
				@Override
				public void run()
				{
					if (e.debug)
						System.out.println("@" + timestamp() + " Larva+1");
					larva = Math.max(Math.min(larva + bases(), bases() * 3), larva);
					if (larva < 3 * bases())
						addFutureAction(15, this);
					else
						buildingLarva = false;
				}
			});
		}
	}

	public boolean hasSupply(double i)
	{
		if (supplyUsed + i <= supply())
			return true;
		return false;
	}

	public int mineralPatches()
	{
		if (patches.length < bases() * 8)
			patches = new int[bases() * 8];
		return hatcheries * 8;
	}

	int[]		patches				= new int[24];
	public int	extractorsBuilding	= 0;

	// Mines minerals on all bases perfectly per one second.
	public double mineMinerals()
	{
		int drones = dronesOnMinerals;
		for (int i = 0; i < mineralPatches(); i++)
			patches[i] = 0;
		for (int i = 0; i < mineralPatches(); i++)
			// Assign first drone
			if (drones > 0)
			{
				patches[i]++;
				drones--;
			}
		if (drones > 0)
			for (int i = 0; i < mineralPatches(); i++)
				// Assign second drone
				if (drones > 0)
				{
					patches[i]++;
					drones--;
				}
		if (drones > 0)
			for (int i = 0; i < mineralPatches(); i++)
				// Assign third drone
				if (drones > 0)
				{
					patches[i]++;
					drones--;
				}
		// Assume half the patches are close, and half are far, and the close
		// ones have more SCVs. (perfect)
		double mineralsMined = 0.0;
		for (int i = 0; i < mineralPatches(); i++)
			if (i < mineralPatches() / 2) // Close patch
				if (patches[i] == 0)
					;
				else if (patches[i] == 1)
					mineralsMined += 45.0 / 60.0; // Per TeamLiquid
				else if (patches[i] == 2)
					mineralsMined += 90.0 / 60.0; // Per TeamLiquid
				else
					mineralsMined += 102.0 / 60.0; // Per TeamLiquid
			else if (patches[i] == 0)
				;
			else if (patches[i] == 1)
				mineralsMined += 39.0 / 60.0; // Per TeamLiquid
			else if (patches[i] == 2)
				mineralsMined += 78.0 / 60.0; // Per TeamLiquid
			else
				mineralsMined += 102.0 / 60.0; // Per TeamLiquid
		return mineralsMined;
	}

	// Mines gas on all bases perfectly per one second.
	public double mineGas()
	{
		if (gasExtractors == 0)
			return 0;
		int drones = dronesOnGas;
		int[] extractors = new int[gasExtractors]; // Assign drones/patch
		for (int i = 0; i < extractors.length; i++)
			extractors[i] = 0;
		for (int i = 0; i < extractors.length; i++)
			// Assign first drone
			if (drones > 0)
			{
				extractors[i]++;
				drones--;
			}
		for (int i = 0; i < extractors.length; i++)
			// Assign second drone
			if (drones > 0)
			{
				extractors[i]++;
				drones--;
			}
		for (int i = 0; i < extractors.length; i++)
			// Assign third drone
			if (drones > 0)
			{
				extractors[i]++;
				drones--;
			}
		double gasMined = 0.0;
		for (int i = 0; i < extractors.length; i++)
			if (extractors[i] == 0)
				;
			else if (extractors[i] == 1)
				gasMined += 42.0 / 60.0; // Per TeamLiquid
			else if (extractors[i] == 2)
				gasMined += 84.0 / 60.0; // Per TeamLiquid
			else
				gasMined += 114.0 / 60.0; // Per TeamLiquid
		return gasMined;
	}

	public void accumulateMaterials()
	{
		minerals += mineMinerals();
		gas += mineGas();
	}

	public String timestamp()
	{
		return seconds / 60 + ":" + seconds % 60;
	}

	public int extractors()
	{
		return (bases()) * 2;
	}

	public int bases()
	{
		return hatcheries + lairs + evolvinghatcheries + evolvingLairs + hives;
	}

}
