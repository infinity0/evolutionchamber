package com.fray.evo;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.WeakHashMap;

import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.impl.IntegerGene;

import com.fray.evo.action.EcAction;
import com.fray.evo.util.EcCacheMap;

public class EcEvolver extends FitnessFunction
{
	EcState			source;
	private EcState	destination;
	public boolean	debug	= false;
	
	public EcCacheMap<String,Double> scoreMap = new EcCacheMap<String, Double>();

	public EcEvolver(EcState source, EcState destination)
	{
		this.source = source;
		this.destination = destination;
	}

	protected String getAlleleAsString(IChromosome c)
	{
		StringBuilder sb = new StringBuilder();
		for (Gene g : c.getGenes())
		{
			if (((Integer)g.getAllele()).intValue() >= 10)
				sb.append(((char)((int)'a'+(Integer)g.getAllele()-10)));
			else
				sb.append(g.getAllele().toString());
		}
		return sb.toString();
	}
	
	@Override
	protected double evaluate(IChromosome arg0)
	{
		EcBuildOrder s;
		try
		{
			String chrome = getAlleleAsString(arg0);
			Double score = scoreMap.get(chrome);
			if (score != null)
				return score.doubleValue();
			s = populateBuildOrder((EcBuildOrder) source, arg0);
			score = destination.score(doEvaluate(s));
			scoreMap.put(chrome,score);
			return score;
		}
		catch (CloneNotSupportedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Double.NEGATIVE_INFINITY;
	}

	public EcState evaluateGetBuildOrder(IChromosome arg0)
	{
		EcBuildOrder s;
		try
		{
			s = populateBuildOrder((EcBuildOrder) source, arg0);

			return doEvaluate(s);
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return null;
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

	public PrintStream log = System.out;
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
						log.println("Expired on " + a + ", " + s.invalidActions + "&" + s.actionLength);
					return s;
				}
				if (destination.isSatisfied(s))
				{
					if (debug)
					{
						log.println("Satisfied.");
						log.println("Invalid actions left to trim out: " + s.invalidActions);
						log.println("Number of actions in build order: " + i);
					}
					return s;
				}
			}
			if (debug)
				log.println(s.toString() + " -- " + a);

			a.execute(s, this);
		}
		return s;
	}

}
