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
	public int				dronesGoingOnMinerals	= 6;	
	public int				dronesGoingOnGas	= 0;
	public int				dronesOnMinerals	= 0;
	public int				dronesOnGas			= 0;
	boolean					buildingLarva		= false;
	public int				evolvingSpires		= 0;
	public int				queensBuilding		= 0;
	public int				spiresInUse			= 0;
	public int				evolutionChambersInUse;

	transient MultiValueMap	futureAction		= new MultiValueMap();
	ArrayList<EcAction>		actions				= new ArrayList<EcAction>();

	public EcBuildOrder()
	{
        super();
        addFutureAction(2, new Runnable(){
            @Override
            public void run()
            {
                    dronesOnMinerals +=6;
                    dronesGoingOnMinerals -=6;
            }});
	}
	
	@Override
	public EcBuildOrder clone() throws CloneNotSupportedException
	{
		final EcBuildOrder s = new EcBuildOrder();
		s.larva = larva;
		s.dronesGoingOnMinerals = dronesGoingOnMinerals;
		s.dronesGoingOnGas = dronesGoingOnGas;
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
		return ("@" + timestamp() + "\tM:" + (int) minerals + "\tG:" + (int) gas + "\tL:" + larva + "\tS:"
				+ ((int) supplyUsed) + "/" + supply());
	}

	public String toLongString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("At time: " + timestamp());
		sb.append("\nMinerals: " + (int) minerals + "\tGas:      " + (int) gas + "\tSupply:   " + ((int) supplyUsed)
				+ "/" + supply() + "\tLarva: " + larva);

		append(sb, "Drones", drones);
		append(sb, "Overlords", overlords);
		append(sb, "Overseers", overseers);
		append(sb, "Queens", queens);
		append(sb, "Zerglings", zerglings);
		append(sb, "Banelings", banelings);
		append(sb, "Roaches", roaches);
		append(sb, "Hydralisks", hydralisks);
		append(sb, "Infestors", infestors);
		append(sb, "Mutalisks", mutalisks);
		append(sb, "Corruptors", corruptors);
		append(sb, "Ultralisks", ultralisks);
		append(sb, "Brood Lords", broodlords);

		append(sb, "Hatcheries", hatcheries);
		append(sb, "Lairs", lairs);
		append(sb, "Hives", hives);
		append(sb, "Gas Extractors", gasExtractors);
		append(sb, "Spawning Pools", spawningPools);
		append(sb, "Baneling Nest", banelingNest);
		append(sb, "Roach Warrens", roachWarrens);
		append(sb, "Hydralisk Den", hydraliskDen);
		append(sb, "Infestation Pit", infestationPit);
		append(sb, "Spire", spire);
		append(sb, "Ultralisk Cavern", ultraliskCavern);
		append(sb, "Greater Spire", greaterSpire);
		append(sb, "Evolution Chambers", evolutionChambers);
		append(sb, "Spine Crawlers", spineCrawlers);
		append(sb, "Spore Crawlers", sporeCrawlers);
		append(sb, "Nydus Networks", nydusNetwork);
		append(sb, "Nydus Worms", nydusWorm);

		append(sb, "Melee +1", melee1);
		append(sb, "Melee +2", melee2);
		append(sb, "Melee +3", melee3);
		append(sb, "Missile +1", missile1);
		append(sb, "Missile +2", missile2);
		append(sb, "Missile +3", missile3);
		append(sb, "Armor +1", armor1);
		append(sb, "Armor +2", armor2);
		append(sb, "Armor +3", armor3);
		append(sb, "Flyer Attack +1", flyerAttack1);
		append(sb, "Flyer Attack +2", flyerAttack2);
		append(sb, "Flyer Attack +3", flyerAttack3);
		append(sb, "Flyer Armor +1", flyerArmor1);
		append(sb, "Flyer Armor +2", flyerArmor2);
		append(sb, "Flyer Armor +3", flyerArmor3);
		append(sb, "Metabolic Boost", metabolicBoost);
		append(sb, "Adrenal Glands", adrenalGlands);
		append(sb, "Glial Reconstitution", glialReconstitution);
		append(sb, "Tunneling Claws", tunnelingClaws);
		append(sb, "Burrow", burrow);
		append(sb, "Pneumatized Carapace", pneumatizedCarapace);
		append(sb, "Ventral Sacs", ventralSacs);
		append(sb, "Centrifugal Hooks", centrifugalHooks);
		append(sb, "Grooved Spines", groovedSpines);
		append(sb, "Neural Parasite", neuralParasite);
		append(sb, "Pathogen Glands", pathogenGlands);
		append(sb, "Chitinous Plating", chitinousPlating);
		return sb.toString();
	}

	private void append(StringBuilder sb, String name, boolean doit)
	{
		if (doit)
			sb.append("\n" + name);
	}

	private void append(StringBuilder sb, String name, int count)
	{
		if (count > 0)
			sb.append("\n" + name + ": " + count);
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
	public boolean nothingGoingToHappen(double time)
	{
		for (double t : (Collection<Double>)futureAction.keySet())
			if (t > time)
				return false;
		return true;
	}

	public void consumeLarva(final EcEvolver e)
	{
		final EcBuildOrder t = this;
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
						e.obtained(t, " Larva+1");
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
		return bases() * 8;
	}

	int[]		patches				= new int[24];
	public int	extractorsBuilding	= 0;
	public int	hatcheriesBuilding	= 0;
	public int	spawningPoolsInUse	= 0;
	public int	roachWarrensInUse	= 0;
	public int	infestationPitInUse	= 0;
	public int	hatcheriesSpawningLarva = 0;

	// Mines minerals on all bases perfectly per one second.
	public double mineMinerals()
	{
		int drones = dronesOnMinerals;
		int mineralPatches = mineralPatches();
		for (int i = 0; i < mineralPatches; i++)
			patches[i] = 0;
		for (int i = 0; i < mineralPatches; i++)
			// Assign first drone
			if (drones > 0)
			{
				patches[i]++;
				drones--;
			}
		if (drones > 0)
			for (int i = 0; i < mineralPatches; i++)
				// Assign second drone
				if (drones > 0)
				{
					patches[i]++;
					drones--;
				}
		if (drones > 0)
			for (int i = 0; i < mineralPatches; i++)
				// Assign third drone
				if (drones > 0)
				{
					patches[i]++;
					drones--;
				}
		// Assume half the patches are close, and half are far, and the close
		// ones have more SCVs. (perfect)
		double mineralsMined = 0.0;
		for (int i = 0; i < mineralPatches; i++)
			if (i < mineralPatches / 2) // Close patch
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
				mineralsMined += 35.0 / 60.0; // Per TeamLiquid
			else if (patches[i] == 2)
				mineralsMined += 75.0 / 60.0; // Per TeamLiquid
			else
				mineralsMined += 100.0 / 60.0; // Per TeamLiquid
		return mineralsMined;
	}

	// Mines gas on all bases perfectly per one second.
	public double mineGas()
	{
		if (gasExtractors == 0)
			return 0;
		int drones = dronesOnGas;
		int[] extractors = new int[Math.min(gasExtractors,bases()*2)]; // Assign drones/patch
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
				gasMined += 38.0 / 60.0; // Per TeamLiquid
			else if (extractors[i] == 2)
				gasMined += 82.0 / 60.0; // Per TeamLiquid
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
		return seconds / 60 + ":" + (seconds%60 < 10 ? "0" : "") + seconds % 60;
	}
	
	public String timestampIncremented(int increment)
	{
		int incrementedSeconds = seconds + increment;
		return incrementedSeconds / 60 + ":" + (incrementedSeconds%60 < 10 ? "0" : "") + incrementedSeconds % 60;
	}

	public int extractors()
	{
		return (bases() + hatcheriesBuilding) * 2;
	}

	public void consumeHatch(int seconds)
	{
		boolean usehatch = false;
		boolean uselair = false;
		if (hatcheries > 0)
		{
			hatcheries--;
			evolvingHatcheries++;
			usehatch = true;
		}
		else if (lairs > 0)
		{
			lairs--;
			evolvingLairs++;
			uselair = true;
		}
		else
		{
			hives--;
			evolvingHives++;
		}
		final boolean useHatch = usehatch;
		final boolean useLair = uselair;
		addFutureAction(seconds, new Runnable()
		{
			@Override
			public void run()
			{
				if (useHatch)
				{
					evolvingHatcheries--;
					hatcheries++;
				}
				else if (useLair)
				{
					evolvingLairs--;
					lairs++;
				}
				else
				{
					evolvingHives--;
					hives++;
				}
			}
		});
	}


}
