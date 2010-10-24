package com.fray.evo;

import java.util.ArrayList;
import java.util.List;

import com.fray.evo.fitness.EcFitness;
import com.fray.evo.fitness.EcStandardFitness;

public class EcState
{
	
	public EcState() {
		fitness = EcSettings.getFitnessFunction();
	}
	
	private EcFitness				fitness = null;
	
	public double preTimeScore = 0.0;
	public double timeBonus = 0.0;
	
	public double					minerals			= 50;
	public double					gas					= 0;
	public double					supplyUsed			= 6;

	public int						evolvingHatcheries	= 0;
	public int						evolvingLairs		= 0;
	public int						evolvingHives		= 0;
	public int						hatcheries			= 1;
	public int						lairs				= 0;
	public int						hives				= 0;
	public int						spawningPools		= 0;
	public int						evolutionChambers	= 0;
	public int						roachWarrens		= 0;
	public int						hydraliskDen		= 0;
	public int						banelingNest		= 0;
	public int						infestationPit		= 0;
	public int						greaterSpire		= 0;
	public int						ultraliskCavern		= 0;
	public int						gasExtractors		= 0;
	public int						spire				= 0;
	public int						spineCrawlers		= 0;
	public int						sporeCrawlers		= 0;
	public int						nydusNetwork		= 0;
	public int						nydusWorm			= 0;

	public int						drones				= 6;
	public int						overlords			= 1;
	public int						overseers			= 0;
	public int						zerglings			= 0;
	public int						banelings			= 0;
	public int						roaches				= 0;
	public int						mutalisks			= 0;
	public int						infestors			= 0;
	public int						queens				= 0;
	public int						hydralisks			= 0;
	public int						corruptors			= 0;
	public int						ultralisks			= 0;
	public int						broodlords			= 0;

	public boolean					metabolicBoost		= false;
	public boolean					adrenalGlands		= false;
	public boolean					glialReconstitution	= false;
	public boolean					tunnelingClaws		= false;
	public boolean					burrow				= false;
	public boolean					pneumatizedCarapace	= false;
	public boolean					ventralSacs			= false;
	public boolean					centrifugalHooks	= false;
	public boolean					melee1				= false;
	public boolean					melee2				= false;
	public boolean					melee3				= false;
	public boolean					missile1			= false;
	public boolean					missile2			= false;
	public boolean					missile3			= false;
	public boolean					armor1				= false;
	public boolean					armor2				= false;
	public boolean					armor3				= false;
	public boolean					groovedSpines		= false;
	public boolean					neuralParasite		= false;
	public boolean					pathogenGlands		= false;
	public boolean					flyerAttack1		= false;
	public boolean					flyerAttack2		= false;
	public boolean					flyerAttack3		= false;
	public boolean					flyerArmor1			= false;
	public boolean					flyerArmor2			= false;
	public boolean					flyerArmor3			= false;
	public boolean					chitinousPlating	= false;

	public int						seconds				= 0;
	public int						targetSeconds		= 0;
	public int						invalidActions		= 0;
	public double					actionLength		= 0;
	public int						waits;

	public transient List<EcState>	waypoints			= new ArrayList<EcState>();

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		EcState s = new EcState();
		assign(s);
		return s;
	}

	protected void assign(EcState s)
	{
		for (EcState st : waypoints)
			try
			{
				s.waypoints.add((EcState) st.clone());
			}
			catch (CloneNotSupportedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		s.minerals = minerals;
		s.gas = gas;
		s.supplyUsed = supplyUsed;

		s.hatcheries = hatcheries;
		s.lairs = lairs;
		s.hives = hives;
		s.spawningPools = spawningPools;
		s.banelingNest = banelingNest;
		s.evolutionChambers = evolutionChambers;
		s.roachWarrens = roachWarrens;
		s.hydraliskDen = hydraliskDen;
		s.infestationPit = infestationPit;
		s.greaterSpire = greaterSpire;
		s.ultraliskCavern = ultraliskCavern;
		s.gasExtractors = gasExtractors;
		s.spire = spire;
		s.greaterSpire = greaterSpire;
		s.spineCrawlers = spineCrawlers;
		s.sporeCrawlers = sporeCrawlers;
		s.nydusNetwork = nydusNetwork;
		s.nydusWorm = nydusWorm;

		s.zerglings = zerglings;
		s.banelings = banelings;
		s.roaches = roaches;
		s.mutalisks = mutalisks;
		s.drones = drones;
		s.queens = queens;
		s.hydralisks = hydralisks;
		s.infestors = infestors;
		s.corruptors = corruptors;
		s.ultralisks = ultralisks;
		s.broodlords = broodlords;
		s.overlords = overlords;
		s.overseers = overseers;

		s.metabolicBoost = metabolicBoost;
		s.adrenalGlands = adrenalGlands;
		s.glialReconstitution = glialReconstitution;
		s.tunnelingClaws = tunnelingClaws;
		s.burrow = burrow;
		s.pneumatizedCarapace = pneumatizedCarapace;
		s.ventralSacs = ventralSacs;
		s.centrifugalHooks = centrifugalHooks;
		s.melee1 = melee1;
		s.melee2 = melee2;
		s.melee3 = melee3;
		s.missile1 = missile1;
		s.missile2 = missile2;
		s.missile3 = missile3;
		s.armor1 = armor1;
		s.armor2 = armor2;
		s.armor3 = armor3;
		s.groovedSpines = groovedSpines;
		s.neuralParasite = neuralParasite;
		s.pathogenGlands = pathogenGlands;
		s.flyerAttack1 = flyerAttack1;
		s.flyerAttack2 = flyerAttack2;
		s.flyerAttack3 = flyerAttack3;
		s.flyerArmor1 = flyerArmor1;
		s.flyerArmor2 = flyerArmor2;
		s.flyerArmor3 = flyerArmor3;
		s.chitinousPlating = chitinousPlating;

		s.seconds = seconds;
		s.targetSeconds = targetSeconds;
		s.invalidActions = invalidActions;
		s.actionLength = actionLength;
	}

	public int supply()
	{
		return Math.min((overlords + overseers) * 8 + 2 * bases(),200);
	}

	public static EcState defaultDestination()
	{
		EcState d = new EcState();

		d.drones = 0;
		d.overlords = 0;
		d.hatcheries = 0;
		d.targetSeconds = 60 * 120;

		return d;
	}

	public double score(EcState candidate)
	{
		return fitness.score(candidate, this);
	}

	public void union(EcState s)
	{
		hatcheries = Math.max(s.hatcheries, hatcheries);
		lairs = Math.max(s.lairs, lairs);
		hives = Math.max(s.hives, hives);
		spawningPools = Math.max(s.spawningPools, spawningPools);
		banelingNest = Math.max(s.banelingNest, banelingNest);
		evolutionChambers = Math.max(s.evolutionChambers, evolutionChambers);
		roachWarrens = Math.max(s.roachWarrens, roachWarrens);
		hydraliskDen = Math.max(s.hydraliskDen, hydraliskDen);
		infestationPit = Math.max(s.infestationPit, infestationPit);
		greaterSpire = Math.max(s.greaterSpire, greaterSpire);
		ultraliskCavern = Math.max(s.ultraliskCavern, ultraliskCavern);
		gasExtractors = Math.max(s.gasExtractors, gasExtractors);
		spire = Math.max(s.spire, spire);
		greaterSpire = Math.max(s.greaterSpire, greaterSpire);
		spineCrawlers = Math.max(s.spineCrawlers, spineCrawlers);
		sporeCrawlers = Math.max(s.sporeCrawlers, sporeCrawlers);
		nydusNetwork = Math.max(s.nydusNetwork,nydusNetwork);
		nydusWorm = Math.max(s.nydusWorm,nydusWorm);

		zerglings = Math.max(s.zerglings, zerglings);
		banelings = Math.max(s.banelings, banelings);
		roaches = Math.max(s.roaches, roaches);
		mutalisks = Math.max(s.mutalisks, mutalisks);
		drones = Math.max(s.drones, drones);
		queens = Math.max(s.queens, queens);
		hydralisks = Math.max(s.hydralisks, hydralisks);
		infestors = Math.max(s.infestors, infestors);
		corruptors = Math.max(s.corruptors, corruptors);
		ultralisks = Math.max(s.ultralisks, ultralisks);
		broodlords = Math.max(s.broodlords, broodlords);
		overlords = Math.max(s.overlords, overlords);
		overseers = Math.max(s.overseers, overseers);

		metabolicBoost = s.metabolicBoost | metabolicBoost;
		adrenalGlands = s.adrenalGlands | adrenalGlands;
		glialReconstitution = s.glialReconstitution | glialReconstitution;
		tunnelingClaws = s.tunnelingClaws | tunnelingClaws;
		burrow = s.burrow | burrow;
		pneumatizedCarapace = s.pneumatizedCarapace | pneumatizedCarapace;
		ventralSacs = s.ventralSacs | ventralSacs;
		centrifugalHooks = s.centrifugalHooks | centrifugalHooks;
		melee1 = s.melee1 | melee1;
		melee2 = s.melee2 | melee2;
		melee3 = s.melee3 | melee3;
		missile1 = s.missile1 | missile1;
		missile2 = s.missile2 | missile2;
		missile3 = s.missile3 | missile3;
		armor1 = s.armor1 | armor1;
		armor2 = s.armor2 | armor2;
		armor3 = s.armor3 | armor3;
		groovedSpines = s.groovedSpines | groovedSpines;
		neuralParasite = s.neuralParasite | neuralParasite;
		pathogenGlands = s.pathogenGlands | pathogenGlands;
		flyerAttack1 = s.flyerAttack1 | flyerAttack1;
		flyerAttack2 = s.flyerAttack2 | flyerAttack2;
		flyerAttack3 = s.flyerAttack3 | flyerAttack3;
		flyerArmor1 = s.flyerArmor1 | flyerArmor1;
		flyerArmor2 = s.flyerArmor2 | flyerArmor2;
		flyerArmor3 = s.flyerArmor3 | flyerArmor3;
		chitinousPlating = s.chitinousPlating | chitinousPlating;

	}

	public boolean isSatisfied(EcState candidate)
	{
		EcState c = candidate;
		
		if (waypoints.size() > 0)
		{
			EcState state = defaultDestination();
			for (EcState s : waypoints)
			{
				state.union(s);
			}
			state.union(this);
			return state.isSatisfied(candidate);
		}
		
		if (c.drones < drones)
			return false;
		if (c.zerglings < zerglings)
			return false;
		if (c.banelings < banelings)
			return false;
		if (c.roaches < roaches)
			return false;
		if (c.hatcheries < hatcheries)
			return false;
		if (c.mutalisks < mutalisks)
			return false;
		if (c.queens < queens)
			return false;
		if (c.hydralisks < hydralisks)
			return false;
		if (c.infestors < infestors)
			return false;
		if (c.corruptors < corruptors)
			return false;
		if (c.ultralisks < ultralisks)
			return false;
		if (c.broodlords < broodlords)
			return false;
		if (c.overlords < overlords)
			return false;
		if (c.overseers < overseers)
			return false;

		if (c.hatcheries < hatcheries)
			return false;
		if (c.lairs < lairs)
			return false;
		if (c.hives < hives)
			return false;
		if (c.gasExtractors < gasExtractors)
			return false;
		if (c.spawningPools < spawningPools)
			return false;
		if (c.banelingNest < banelingNest)
			return false;
		if (c.roachWarrens < roachWarrens)
			return false;
		if (c.hydraliskDen < hydraliskDen)
			return false;
		if (c.infestationPit < infestationPit)
			return false;
		if (c.spire < spire)
			return false;
		if (c.greaterSpire < greaterSpire)
			return false;
		if (c.ultraliskCavern < ultraliskCavern)
			return false;
		if (c.evolutionChambers < evolutionChambers)
			return false;
		if (c.spineCrawlers < spineCrawlers)
			return false;
		if (c.sporeCrawlers < sporeCrawlers)
			return false;
		if (c.nydusNetwork < nydusNetwork)
			return false;
		if (c.nydusWorm < nydusWorm)
			return false;

		if ((!c.metabolicBoost) & metabolicBoost)
			return false;
		if ((!c.adrenalGlands) & adrenalGlands)
			return false;
		if ((!c.glialReconstitution) & glialReconstitution)
			return false;
		if ((!c.tunnelingClaws) & tunnelingClaws)
			return false;
		if ((!c.burrow) & burrow)
			return false;
		if ((!c.pneumatizedCarapace) & pneumatizedCarapace)
			return false;
		if ((!c.ventralSacs) & ventralSacs)
			return false;
		if ((!c.centrifugalHooks) & centrifugalHooks)
			return false;
		if ((!c.melee1) & melee1)
			return false;
		if ((!c.melee2) & melee2)
			return false;
		if ((!c.melee3) & melee3)
			return false;
		if ((!c.missile1) & missile1)
			return false;
		if ((!c.missile2) & missile2)
			return false;
		if ((!c.missile3) & missile3)
			return false;
		if ((!c.armor1) & armor1)
			return false;
		if ((!c.armor2) & armor2)
			return false;
		if ((!c.armor3) & armor3)
			return false;
		if ((!c.groovedSpines) & groovedSpines)
			return false;
		if ((!c.neuralParasite) & neuralParasite)
			return false;
		if ((!c.pathogenGlands) & pathogenGlands)
			return false;
		if ((!c.flyerAttack1) & flyerAttack1)
			return false;
		if ((!c.flyerAttack2) & flyerAttack2)
			return false;
		if ((!c.flyerAttack3) & flyerAttack3)
			return false;
		if ((!c.flyerArmor1) & flyerArmor1)
			return false;
		if ((!c.flyerArmor2) & flyerArmor2)
			return false;
		if ((!c.flyerArmor3) & flyerArmor3)
			return false;
		if ((!c.chitinousPlating) & chitinousPlating)
			return false;

		return true;
	}

	public int bases()
	{
		return hatcheries + lairs + evolvingHatcheries + evolvingLairs + hives + evolvingHives;
	}

	public int getSumStuff()
	{
		if (waypoints.size() > 0)
		{
			EcState state = defaultDestination();
			for (EcState s : waypoints)
			{
				state.union(s);
			}
			state.union(this);
			return state.getSumStuff();
		}
		
		int i = hatcheries + lairs + hives + spawningPools + evolutionChambers + roachWarrens + hydraliskDen
				+ banelingNest + infestationPit + greaterSpire + ultraliskCavern + gasExtractors + spire
				+ spineCrawlers + sporeCrawlers + nydusNetwork + nydusWorm

				+ drones + overlords + overseers + zerglings + banelings * 2 + roaches + mutalisks + infestors + queens
				+ hydralisks + corruptors + ultralisks + broodlords * 2;

		if (metabolicBoost)
			i++;
		if (adrenalGlands)
			i++;
		if (glialReconstitution)
			i++;
		if (tunnelingClaws)
			i++;
		if (burrow)
			i++;
		if (pneumatizedCarapace)
			i++;
		if (ventralSacs)
			i++;
		if (centrifugalHooks)
			i++;
		if (melee1)
			i++;
		if (melee2)
			i++;
		if (melee3)
			i++;
		if (missile1)
			i++;
		if (missile2)
			i++;
		if (missile3)
			i++;
		if (armor1)
			i++;
		if (armor2)
			i++;
		if (armor3)
			i++;
		if (groovedSpines)
			i++;
		if (neuralParasite)
			i++;
		if (pathogenGlands)
			i++;
		if (flyerAttack1)
			i++;
		if (flyerAttack2)
			i++;
		if (flyerAttack3)
			i++;
		if (flyerArmor1)
			i++;
		if (flyerArmor2)
			i++;
		if (flyerArmor3)
			i++;
		if (chitinousPlating)
			i++;
		for (EcState s : waypoints)
			i += s.getSumStuff();
		return i;
	}

	public boolean waypointMissed(EcBuildOrder candidate)
	{
		for (EcState s : waypoints)
		{
			if (candidate.seconds < s.targetSeconds)
				continue;
			if (s.isSatisfied(candidate))
				continue;
			return true;
		}
		return false;
	}
}
