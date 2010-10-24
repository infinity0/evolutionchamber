package com.fray.evo;

import com.fray.evo.fitness.*;

public class EcSettings
{
	public static boolean useExtractorTrick = true;
	public static boolean pullWorkersFromGas = true;
	public static EcFitnessType fitnessType = EcFitnessType.STANDARD;

	
	private static EcFitness ff;
	
	public static EcFitness getFitnessFunction() {
		
		if(ff != null)
			return ff;
		
		switch(fitnessType) {
		case STANDARD:
			ff = new EcStandardFitness();
			break;
		case ECON:
			ff = new EcEconFitness();
			break;
		default:
			ff = new EcStandardFitness();
			break;
		}
		
		return ff;
		
	}
	
}
