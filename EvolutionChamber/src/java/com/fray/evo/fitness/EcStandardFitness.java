package com.fray.evo.fitness;

import com.fray.evo.EcState;

public class EcStandardFitness implements EcFitness {

	public double augmentScore(EcState current, EcState destination, double score, boolean waypoint)
	{
		EcState c = current;
		
		score = augmentScore(score, c.drones, destination.drones, 50, .5, waypoint);
		score = augmentScore(score, c.zerglings, destination.zerglings, 25, .25, waypoint);
		score = augmentScore(score, c.banelings, destination.banelings, 75, .75, waypoint);
		score = augmentScore(score, c.roaches, destination.roaches, 100, 1.0, waypoint);
		score = augmentScore(score, c.mutalisks, destination.mutalisks, 200, 2.0, waypoint);
		score = augmentScore(score, c.queens, destination.queens, 150, 1.5, waypoint);
		score = augmentScore(score, c.hydralisks, destination.hydralisks, 150, 1.5, waypoint);
		score = augmentScore(score, c.infestors, destination.infestors, 250, 2.5, waypoint);
		score = augmentScore(score, c.corruptors, destination.corruptors, 250, 2.5, waypoint);
		score = augmentScore(score, c.ultralisks, destination.ultralisks, 500, 5.0, waypoint);
		score = augmentScore(score, c.broodlords, destination.broodlords, 400, 4.0, waypoint);
		score = augmentScore(score, c.overlords, destination.overlords, 100, 1.0, waypoint);
		score = augmentScore(score, c.overseers, destination.overseers, 250, 2.5, waypoint);

		score = augmentScore(score, c.gasExtractors, destination.gasExtractors, 25, .25, waypoint);

		score = augmentScore(score, c.hatcheries, destination.hatcheries, 300, 3, waypoint);
		score = augmentDropoffScore(score, c.lairs, destination.lairs, 550, 5.5, waypoint);
		score = augmentDropoffScore(score, c.hives, destination.hives, 900, 9, waypoint);
		score = augmentDropoffScore(score, c.spawningPools, destination.spawningPools, 200, 2, waypoint);
		score = augmentDropoffScore(score, c.roachWarrens, destination.roachWarrens, 150, 1.5, waypoint);
		score = augmentDropoffScore(score, c.hydraliskDen, destination.hydraliskDen, 200, 2, waypoint);
		score = augmentDropoffScore(score, c.banelingNest, destination.banelingNest, 150, 1.5, waypoint);
		score = augmentDropoffScore(score, c.greaterSpire, destination.greaterSpire, 650, 6.5, waypoint);
		score = augmentDropoffScore(score, c.ultraliskCavern, destination.ultraliskCavern, 350, 3.5, waypoint);
		score = augmentDropoffScore(score, c.spire, destination.spire, 400, 4, waypoint);
		score = augmentDropoffScore(score, c.infestationPit, destination.infestationPit, 200, 2.0, waypoint);
		score = augmentDropoffScore(score, c.evolutionChambers, destination.evolutionChambers, 75, 0.75, waypoint);
		score = augmentScore(score, c.spineCrawlers, destination.spineCrawlers, 100, 1.00, waypoint);
		score = augmentScore(score, c.sporeCrawlers, destination.sporeCrawlers, 75, .75, waypoint);
		score = augmentDropoffScore(score, c.nydusNetwork, destination.nydusNetwork, 350, 3.00, waypoint);
		score = augmentScore(score, c.nydusWorm, destination.nydusWorm, 200, 2.00, waypoint);

		score = augmentScore(score, c.metabolicBoost, destination.metabolicBoost, 200, 2.0, waypoint);
		score = augmentScore(score, c.adrenalGlands, destination.adrenalGlands, 400, 4.0, waypoint);
		score = augmentScore(score, c.glialReconstitution, destination.glialReconstitution, 200, 2.0, waypoint);
		score = augmentScore(score, c.tunnelingClaws, destination.tunnelingClaws, 300, 3.0, waypoint);
		score = augmentScore(score, c.burrow, destination.burrow, 200, 2.0, waypoint);
		score = augmentScore(score, c.pneumatizedCarapace, destination.pneumatizedCarapace, 200, 2.0, waypoint);
		score = augmentScore(score, c.ventralSacs, destination.ventralSacs, 400, 4.0, waypoint);
		score = augmentScore(score, c.centrifugalHooks, destination.centrifugalHooks, 300, 3.0, waypoint);
		score = augmentScore(score, c.melee1, destination.melee1, 200, 2.0, waypoint);
		score = augmentScore(score, c.melee2, destination.melee2, 300, 3.0, waypoint);
		score = augmentScore(score, c.melee3, destination.melee3, 400, 4.0, waypoint);
		score = augmentScore(score, c.missile1, destination.missile1, 200, 2.0, waypoint);
		score = augmentScore(score, c.missile2, destination.missile2, 300, 3.0, waypoint);
		score = augmentScore(score, c.missile3, destination.missile3, 400, 4.0, waypoint);
		score = augmentScore(score, c.armor1, destination.armor1, 200, 3.0, waypoint);
		score = augmentScore(score, c.armor2, destination.armor2, 300, 3.0, waypoint);
		score = augmentScore(score, c.armor3, destination.armor3, 400, 3.0, waypoint);
		score = augmentScore(score, c.groovedSpines, destination.groovedSpines, 300, 3.0, waypoint);
		score = augmentScore(score, c.neuralParasite, destination.neuralParasite, 300, 3.0, waypoint);
		score = augmentScore(score, c.pathogenGlands, destination.pathogenGlands, 300, 3.0, waypoint);
		score = augmentScore(score, c.flyerAttack1, destination.flyerAttack1, 200, 2.0, waypoint);
		score = augmentScore(score, c.flyerAttack2, destination.flyerAttack2, 350, 3.5, waypoint);
		score = augmentScore(score, c.flyerAttack3, destination.flyerAttack3, 500, 5.0, waypoint);
		score = augmentScore(score, c.flyerArmor1, destination.flyerArmor1, 300, 3.0, waypoint);
		score = augmentScore(score, c.flyerArmor2, destination.flyerArmor2, 450, 4.5, waypoint);
		score = augmentScore(score, c.flyerArmor3, destination.flyerArmor3, 600, 6.0, waypoint);
		score = augmentScore(score, c.chitinousPlating, destination.chitinousPlating, 300, 3.0, waypoint);
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
			c.preTimeScore = score;
			score *= ((double) c.targetSeconds / (double) c.seconds) * ((double) c.targetSeconds / (double) c.seconds);
			c.timeBonus = score - c.preTimeScore;
			
			//System.out.println(String.format("PreTimeScore: %.2f",c.preTimeScore));
			//System.out.println(String.format("Time Bonus: %.2f",c.timeBonus));
			

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
