package com.fray.evo;

import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.impl.IntegerGene;

public class EcEvolver extends FitnessFunction
{
	EcState			source;
	private EcState	destination;
	public boolean	debug	= false;

	public EcEvolver(EcState source, EcState destination)
	{
		this.source = source;
		this.destination = destination;
	}
//
//	public double evaluate(char[] charArray)
//	{
//		EcBuildOrder s;
//		try
//		{
//			s = (EcBuildOrder) source.clone();
//			for (char g1 : charArray)
//			{
//				int g = g1 - '0';
//				switch (g)
//				{
//				case 0:
//					s.addAction(new EcActionBuildDrone());
//					break;
//				case 1:
//					s.addAction(new EcActionBuildOverlord());
//					break;
//				case 2:
//					s.addAction(new EcActionBuildSpawningPool());
//					break;
//				case 3:
//					s.addAction(new EcActionBuildZergling());
//					break;
//				case 4:
//					s.addAction(new EcActionBuildQueen());
//					break;
//				case 5:
//					s.addAction(new EcActionBuildHatchery());
//					break;
//				case 6:
//					s.addAction(new EcActionBuildExtractor());
//					break;
//				case 7:
//					s.addAction(new EcActionBuildRoachWarren());
//					break;
//				case 8:
//					s.addAction(new EcActionBuildRoach());
//					break;
//				}
//			}
//
//			return destination.score(doEvaluate(s));
//		}
//		catch (CloneNotSupportedException e)
//		{
//			e.printStackTrace();
//		}
//		return Double.NEGATIVE_INFINITY;
//	}

	@Override
	protected double evaluate(IChromosome arg0)
	{
		EcBuildOrder s;
		try
		{
			s = populateBuildOrder((EcBuildOrder) source, arg0);

			return destination.score(doEvaluate(s));
		}
		catch (CloneNotSupportedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Double.NEGATIVE_INFINITY;
	}

	public static EcBuildOrder populateBuildOrder(EcBuildOrder source, IChromosome arg0) throws CloneNotSupportedException
	{
		EcBuildOrder s;
		s = source.clone();
		for (Gene g1 : arg0.getGenes())
		{
			IntegerGene g = (IntegerGene) g1;
			Integer i = (Integer) g.getAllele();
			try
			{
				s.addAction((EcAction) EcAction.actions.get(i).newInstance());
			}
			catch (InstantiationException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
		return s;
	}

	public EcState doEvaluate(EcBuildOrder s)
	{
		int i = 0;
		for (EcAction a : s.getActions())
		{
			i++;
			if (a.isInvalid(s))
			{
				s.invalidActions++;
				continue;
			}
			while (!a.canExecute(s))
			{
				if (s.seconds >= s.targetSeconds)
				{
					if (debug)
						System.out.println("Expired on " + a + ", " + s.invalidActions + "&" + s.actionLength);
					return s;
				}
				if (destination.isSatisfied(s))
				{
					if (debug)
						System.out.println("Satisfied." + s.invalidActions + "&" + s.actionLength + "("+i+")");
					return s;
				}
			}
			if (debug)
				System.out.println(s.toString() + " -- " + a);

			a.execute(s, this);
		}
		return s;
	}

}
