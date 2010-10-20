package com.fray.evo;

public class EcState
{
	public double	minerals			= 50;
	public double	gas					= 0;
	public double	supplyUsed			= 6;

	public int		evolvingHatcheries	= 0;
	public int		evolvingLairs		= 0;
	public int		evolvingHives		= 0;
	public int		hatcheries			= 1;
	public int		lairs				= 0;
	public int		hives				= 0;
	public int		spawningPools		= 0;
	public int		evolutionChambers	= 0;
	public int		roachWarrens		= 0;
	public int		hydraliskDen		= 0;
	public int		banelingNest		= 0;
	public int		infestationPit		= 0;
	public int		greaterSpire		= 0;
	public int		ultraliskCavern		= 0;
	public int		gasExtractors		= 0;
	public int		spire				= 0;
	public int		spineCrawlers		= 0;
	public int		sporeCrawlers		= 0;

	public int		drones				= 6;
	public int		overlords			= 1;
	public int		overseers			= 0;
	public int		zerglings			= 0;
	public int		banelings			= 0;
	public int		roaches				= 0;
	public int		mutalisks			= 0;
	public int		infestors			= 0;
	public int		queens				= 0;
	public int		hydralisks			= 0;
	public int		corruptors			= 0;
	public int		ultralisks			= 0;
	public int		broodlords			= 0;

	public boolean	metabolicBoost		= false;
	public boolean	adrenalGlands		= false;
	public boolean	glialReconstitution	= false;
	public boolean	tunnelingClaws		= false;
	public boolean	burrow				= false;
	public boolean	pneumatizedCarapace	= false;
	public boolean	ventralSacs			= false;
	public boolean	centrifugalHooks	= false;
	public boolean	melee1				= false;
	public boolean	melee2				= false;
	public boolean	melee3				= false;
	public boolean	missile1			= false;
	public boolean	missile2			= false;
	public boolean	missile3			= false;
	public boolean	armor1				= false;
	public boolean	armor2				= false;
	public boolean	armor3				= false;
	public boolean	groovedSpines		= false;
	public boolean	neuralParasite		= false;
	public boolean	pathogenGlands		= false;
	public boolean	flyerAttack1		= false;
	public boolean	flyerAttack2		= false;
	public boolean	flyerAttack3		= false;
	public boolean	flyerArmor1			= false;
	public boolean	flyerArmor2			= false;
	public boolean	flyerArmor3			= false;
	public boolean	chitinousPlating	= false;

	public int		seconds				= 0;
	public int		targetSeconds		= 0;
	public int		invalidActions		= 0;
	public double	actionLength		= 0;
	public int		waits;

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		EcState s = new EcState();
		assign(s);
		return s;
	}

	protected void assign(EcState s)
	{
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
		return (overlords + overseers) * 8 + 2 * bases();
	}

	public static EcState defaultDestination()
	{
		EcState d = new EcState();

		d.drones = 6;
		d.overlords = 1;

		d.hatcheries = 0;
		// d.lairs = 1;
		// d.hives = 1;
		// d.spawningPools = 1;
		// d.evolutionChambers = 1;
		// d.roachWarrens = 1;
		// //d.hydraliskDen = 1;
		// d.banelingNest = 1;
		// d.infestationPit = 1;
		// d.greaterSpire = 1;
		// d.ultraliskCavern = 1;
		// d.gasExtractors = 1;
		// d.spire = 1;
		// d.spineCrawlers = 2;
		//
		// d.zerglings = 6;
		// d.queens = 1;
		// d.banelings = 1;
		// d.roaches = 7;
		// d.mutalisks = 5;
		// d.infestors = 5;
		// d.hydralisks = 50;
		// d.corruptors = 1;
		// d.ultralisks = 1;
		// d.broodlords = 1;

		// d.metabolicBoost = true;
		// d.adrenalGlands = true;
		// d.glialReconstitution = true;
		// d.tunnelingClaws = true;
		// d.burrow = true;
		// d.pneumatizedCarapace = true;
		// d.ventralSacs = true;
		// d.centrifugalHooks = true;
		// d.melee1 = true;
		// d.melee2 = true;
		// d.melee3 = true;
		// d.missile1 = true;
		// d.missile2 = true;
		// d.missile3 = true;
		// d.armor1 = true;
		// d.armor2 = true;
		// d.armor3 = true;
		// d.groovedSpines = true;
		// d.neuralParasite = true;
		// d.pathogenGlands = true;
		// d.flyerAttack1 = true;
		// d.flyerAttack2 = true;
		// d.flyerAttack3 = true;
		// d.flyerArmor1 = true;
		// d.flyerArmor2 = true;
		// d.flyerArmor3 = true;
		// d.chitinousPlating = true;
		d.targetSeconds = 60 * 120;

		return d;
	}

	public double score(EcState candidate)
	{
		EcState c = candidate;
		double score = 0;
		score = augmentScore(score, c.drones, drones, 50, 2);
		score = augmentScore(score, c.zerglings, zerglings, 25, .25);
		score = augmentScore(score, c.banelings, banelings, 75, .75);
		score = augmentScore(score, c.roaches, roaches, 100, 1);
		score = augmentScore(score, c.hatcheries, hatcheries, 300, 3);
		score = augmentScore(score, c.lairs, lairs, 550, 5.5);
		score = augmentScore(score, c.mutalisks, mutalisks, 200, 2);
		score = augmentScore(score, c.queens, queens, 150, 1.5);
		score = augmentScore(score, c.hydralisks, hydralisks, 150, 1.5);
		score = augmentScore(score, c.infestors, infestors, 250, 2.5);
		score = augmentScore(score, c.corruptors, corruptors, 250, 2.5);
		score = augmentScore(score, c.ultralisks, ultralisks, 500, 5);
		score = augmentScore(score, c.broodlords, broodlords, 400, 4);
		score = augmentScore(score, c.overlords, overlords, 100, 1);
		score = augmentScore(score, c.overseers, overseers, 250, 2.5);
		score = augmentScore(score, c.spawningPools, spawningPools, 200, 2);
		score = augmentScore(score, c.roachWarrens, roachWarrens, 150, 1.5);
		score = augmentScore(score, c.hydraliskDen, hydraliskDen, 200, 2);
		score = augmentScore(score, c.banelingNest, banelingNest, 150, 1.5);
		score = augmentScore(score, c.greaterSpire, greaterSpire, 650, 6.5);
		score = augmentScore(score, c.ultraliskCavern, ultraliskCavern, 350, 3.5);
		score = augmentScore(score, c.spire, spire, 400, 4);
		score = augmentScore(score, c.infestationPit, infestationPit, 200, 2.0);
		score = augmentScore(score, c.evolutionChambers, evolutionChambers, 75, 0.75);
		score = augmentScore(score, c.spineCrawlers, spineCrawlers, 100, 1.00);
		score = augmentScore(score, c.sporeCrawlers, sporeCrawlers, 75, .75);
		score = augmentScore(score, (int) c.minerals, (int) minerals, .001, .001);
		score = augmentScore(score, (int) c.gas, (int) gas, .001, .001);

		score = augmentScore(score, c.metabolicBoost, metabolicBoost, 200, 2.0);
		score = augmentScore(score, c.adrenalGlands, adrenalGlands, 400, 4.0);
		score = augmentScore(score, c.glialReconstitution, glialReconstitution, 200, 2.0);
		score = augmentScore(score, c.tunnelingClaws, tunnelingClaws, 300, 3.0);
		score = augmentScore(score, c.burrow, burrow, 200, 2.0);
		score = augmentScore(score, c.pneumatizedCarapace, pneumatizedCarapace, 200, 2.0);
		score = augmentScore(score, c.ventralSacs, ventralSacs, 400, 4.0);
		score = augmentScore(score, c.centrifugalHooks, centrifugalHooks, 300, 3.0);
		score = augmentScore(score, c.melee1, melee1, 200, 2.0);
		score = augmentScore(score, c.melee2, melee2, 300, 3.0);
		score = augmentScore(score, c.melee3, melee3, 400, 4.0);
		score = augmentScore(score, c.missile1, missile1, 200, 2.0);
		score = augmentScore(score, c.missile2, missile2, 300, 3.0);
		score = augmentScore(score, c.missile3, missile3, 400, 4.0);
		score = augmentScore(score, c.armor1, armor1, 200, 3.0);
		score = augmentScore(score, c.armor2, armor2, 300, 3.0);
		score = augmentScore(score, c.armor3, armor3, 400, 3.0);
		score = augmentScore(score, c.groovedSpines, groovedSpines, 300, 3.0);
		score = augmentScore(score, c.neuralParasite, neuralParasite, 300, 3.0);
		score = augmentScore(score, c.pathogenGlands, pathogenGlands, 300, 3.0);
		score = augmentScore(score, c.flyerAttack1, flyerAttack1, 200, 2.0);
		score = augmentScore(score, c.flyerAttack2, flyerAttack2, 350, 3.5);
		score = augmentScore(score, c.flyerAttack3, flyerAttack3, 500, 5.0);
		score = augmentScore(score, c.flyerArmor1, flyerArmor1, 300, 3.0);
		score = augmentScore(score, c.flyerArmor2, flyerArmor2, 450, 4.5);
		score = augmentScore(score, c.flyerArmor3, flyerArmor3, 600, 6.0);
		score = augmentScore(score, c.chitinousPlating, chitinousPlating, 300, 3.0);

		if (isSatisfied(c))
			score *= ((double) c.targetSeconds / (double) c.seconds) * ((double) c.targetSeconds / (double) c.seconds);

		return Math.max(score - candidate.invalidActions - candidate.actionLength - candidate.waits, 0);
	}

	private double augmentScore(double score, boolean a, boolean b, int mula, double mulb)
	{
		return augmentScore(score, a ? 1 : 0, b ? 1 : 0, mula, mulb);
	}

	private double augmentScore(double score, int a, int b, double mula, double mulb)
	{
		score += Math.max(Math.min(a, b), 0) * mula;
		score += Math.max(a - b, 0) * mulb;
		return score;
	}

	public boolean isSatisfied(EcState candidate)
	{
		EcState c = candidate;
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
		int i = hatcheries + lairs + hives + spawningPools + evolutionChambers + roachWarrens + hydraliskDen
				+ banelingNest + infestationPit + greaterSpire + ultraliskCavern + gasExtractors + spire
				+ spineCrawlers + sporeCrawlers

				+ drones + overlords + overseers + zerglings + banelings*2 + roaches + mutalisks + infestors + queens
				+ hydralisks + corruptors + ultralisks + broodlords*2;

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
		return i;
	}
}
