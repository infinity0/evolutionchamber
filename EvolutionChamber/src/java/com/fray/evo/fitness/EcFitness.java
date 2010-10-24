package com.fray.evo.fitness;

import com.fray.evo.EcState;

public interface EcFitness {

	//public double augmentScore(EcState current, EcState desitnation, double score, boolean waypoint);
	public double score(EcState candidate, EcState metric);
	
	
}
