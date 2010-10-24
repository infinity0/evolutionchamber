package com.fray.evo.fitness;

import com.fray.evo.EcState;

public class EcStandardFitness implements EcFitness {

	public double augmentScore(EcState current, EcState desitnation, double score, boolean waypoint)
	{
		EcState c = current;
		
		score = augmentScore(score, c.drones, desitnation.drones, 50, .5, waypoint);
		score = augmentScore(score, c.zerglings, desitnation.zerglings, 25, .25, waypoint);
		score = augmentScore(score, c.banelings, desitnation.banelings, 75, .75, waypoint);
		score = augmentScore(score, c.roaches, desitnation.roaches, 100, 1.0, waypoint);
		score = augmentScore(score, c.mutalisks, desitnation.mutalisks, 200, 2.0, waypoint);
		score = augmentScore(score, c.queens, desitnation.queens, 150, 1.5, waypoint);
		score = augmentScore(score, c.hydralisks, desitnation.hydralisks, 150, 1.5, waypoint);
		score = augmentScore(score, c.infestors, desitnation.infestors, 250, 2.5, waypoint);
		score = augmentScore(score, c.corruptors, desitnation.corruptors, 250, 2.5, waypoint);
		score = augmentScore(score, c.ultralisks, desitnation.ultralisks, 500, 5.0, waypoint);
		score = augmentScore(score, c.broodlords, desitnation.broodlords, 400, 4.0, waypoint);
		score = augmentScore(score, c.overlords, desitnation.overlords, 100, 1.0, waypoint);
		score = augmentScore(score, c.overseers, desitnation.overseers, 250, 2.5, waypoint);

		score = augmentScore(score, c.hatcheries, desitnation.hatcheries, 300, 3, waypoint);
		score = augmentDropoffScore(score, c.lairs, desitnation.lairs, 550, 5.5, waypoint);
		score = augmentDropoffScore(score, c.hives, desitnation.hives, 900, 9, waypoint);
		score = augmentDropoffScore(score, c.spawningPools, desitnation.spawningPools, 200, 2, waypoint);
		score = augmentDropoffScore(score, c.roachWarrens, desitnation.roachWarrens, 150, 1.5, waypoint);
		score = augmentDropoffScore(score, c.hydraliskDen, desitnation.hydraliskDen, 200, 2, waypoint);
		score = augmentDropoffScore(score, c.banelingNest, desitnation.banelingNest, 150, 1.5, waypoint);
		score = augmentDropoffScore(score, c.greaterSpire, desitnation.greaterSpire, 650, 6.5, waypoint);
		score = augmentDropoffScore(score, c.ultraliskCavern, desitnation.ultraliskCavern, 350, 3.5, waypoint);
		score = augmentDropoffScore(score, c.spire, desitnation.spire, 400, 4, waypoint);
		score = augmentDropoffScore(score, c.infestationPit, desitnation.infestationPit, 200, 2.0, waypoint);
		score = augmentDropoffScore(score, c.evolutionChambers, desitnation.evolutionChambers, 75, 0.75, waypoint);
		score = augmentScore(score, c.spineCrawlers, desitnation.spineCrawlers, 100, 1.00, waypoint);
		score = augmentScore(score, c.sporeCrawlers, desitnation.sporeCrawlers, 75, .75, waypoint);
		score = augmentDropoffScore(score, c.nydusNetwork, desitnation.nydusNetwork, 350, 3.00, waypoint);
		score = augmentScore(score, c.nydusWorm, desitnation.nydusWorm, 200, 2.00, waypoint);

		score = augmentScore(score, c.metabolicBoost, desitnation.metabolicBoost, 200, 2.0, waypoint);
		score = augmentScore(score, c.adrenalGlands, desitnation.adrenalGlands, 400, 4.0, waypoint);
		score = augmentScore(score, c.glialReconstitution, desitnation.glialReconstitution, 200, 2.0, waypoint);
		score = augmentScore(score, c.tunnelingClaws, desitnation.tunnelingClaws, 300, 3.0, waypoint);
		score = augmentScore(score, c.burrow, desitnation.burrow, 200, 2.0, waypoint);
		score = augmentScore(score, c.pneumatizedCarapace, desitnation.pneumatizedCarapace, 200, 2.0, waypoint);
		score = augmentScore(score, c.ventralSacs, desitnation.ventralSacs, 400, 4.0, waypoint);
		score = augmentScore(score, c.centrifugalHooks, desitnation.centrifugalHooks, 300, 3.0, waypoint);
		score = augmentScore(score, c.melee1, desitnation.melee1, 200, 2.0, waypoint);
		score = augmentScore(score, c.melee2, desitnation.melee2, 300, 3.0, waypoint);
		score = augmentScore(score, c.melee3, desitnation.melee3, 400, 4.0, waypoint);
		score = augmentScore(score, c.missile1, desitnation.missile1, 200, 2.0, waypoint);
		score = augmentScore(score, c.missile2, desitnation.missile2, 300, 3.0, waypoint);
		score = augmentScore(score, c.missile3, desitnation.missile3, 400, 4.0, waypoint);
		score = augmentScore(score, c.armor1, desitnation.armor1, 200, 3.0, waypoint);
		score = augmentScore(score, c.armor2, desitnation.armor2, 300, 3.0, waypoint);
		score = augmentScore(score, c.armor3, desitnation.armor3, 400, 3.0, waypoint);
		score = augmentScore(score, c.groovedSpines, desitnation.groovedSpines, 300, 3.0, waypoint);
		score = augmentScore(score, c.neuralParasite, desitnation.neuralParasite, 300, 3.0, waypoint);
		score = augmentScore(score, c.pathogenGlands, desitnation.pathogenGlands, 300, 3.0, waypoint);
		score = augmentScore(score, c.flyerAttack1, desitnation.flyerAttack1, 200, 2.0, waypoint);
		score = augmentScore(score, c.flyerAttack2, desitnation.flyerAttack2, 350, 3.5, waypoint);
		score = augmentScore(score, c.flyerAttack3, desitnation.flyerAttack3, 500, 5.0, waypoint);
		score = augmentScore(score, c.flyerArmor1, desitnation.flyerArmor1, 300, 3.0, waypoint);
		score = augmentScore(score, c.flyerArmor2, desitnation.flyerArmor2, 450, 4.5, waypoint);
		score = augmentScore(score, c.flyerArmor3, desitnation.flyerArmor3, 600, 6.0, waypoint);
		score = augmentScore(score, c.chitinousPlating, desitnation.chitinousPlating, 300, 3.0, waypoint);
		return score;
	}

	private double augmentScore(double score, boolean a, boolean b, int mula, double mulb, boolean waypoint)
	{
		return augmentScore(score, a ? 1 : 0, b ? 1 : 0, mula, mulb, waypoint);
	}

	private double augmentScore(double score, int a, int b, double mula, double mulb, boolean waypoint)
	{
		score += Math.max(Math.min(a, b), 0) * mula;
		if (!waypoint)
			score += Math.max(a - b, 0) * mulb;
		return score;
	}

	private double augmentDropoffScore(double score, int a, int b, double mula, double mulb, boolean waypoint)
	{
		score += Math.max(Math.min(a, b), 0) * mula;
		if (!waypoint)
			for (int i = 0; i < Math.max(a - b, 0); i++)
			{
				mulb /= 2;
				score += mulb;
			}
		// score += Math.max(a - b, 0) * mulb;
		return score;
	}

	@Override
	public double score(EcState candidate, EcState metric) {
		EcState c = candidate;
		double score = 0;

		boolean keepgoing = true;
		EcState state = EcState.defaultDestination();
		for (EcState s : metric.waypoints)
		{
			if (keepgoing)
				state.union(s);
			if (!s.isSatisfied(c))
				keepgoing = false;
		}
		if (keepgoing)
		{
			state.union(metric);
			score = augmentScore(c, metric, score, false);
		}
		else
			score = augmentScore(c, metric, score, false);

		if (state.isSatisfied(c))
		{
			score *= ((double) c.targetSeconds / (double) c.seconds) * ((double) c.targetSeconds / (double) c.seconds);

			score = augmentScore(score, (int) c.minerals, (int) metric.minerals, .011, .011, false);
			score = augmentScore(score, (int) c.gas, (int) metric.gas, .015, .015, false);
		}
		else
		{

			score = augmentScore(score, (int) c.minerals, (int) metric.minerals, .0010, .0010, false);
			score = augmentScore(score, (int) c.gas, (int) metric.gas, .0015, .0015, false);
		}
		// score = Math.max(score - candidate.invalidActions -
		// candidate.actionLength - candidate.waits, 0);
		score = Math.max(score, 0);
		return score;
	}


}
