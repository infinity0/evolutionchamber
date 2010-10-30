package com.fray.evo;

import java.io.PrintStream;

import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.impl.IntegerGene;

import com.fray.evo.action.EcAction;
import com.fray.evo.action.EcActionWait;
import com.fray.evo.action.EcActionExtractorTrick;
import com.fray.evo.action.build.EcActionBuildDrone;

public class EcEvolver extends FitnessFunction
{
	EcState								source;
	private EcState						destination;
	public boolean						debug		= false;
	public static long evaluations = 0;
	public static long cachehits = 0;

//	public static Map<Integer, Double>	scoreMap	= Collections.synchronizedMap(new EcCacheMap<Integer, Double>());

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
			if (((Integer) g.getAllele()).intValue() >= 10)
				sb.append(((char) ((int) 'a' + (Integer) g.getAllele() - 10)));
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
//			String chrome = getAlleleAsString(arg0);
			Double score;
//			score = scoreMap.get(chrome.hashCode());
//			if (score != null)
//			{
//				cachehits++;
//				return score.doubleValue();
//			}
			evaluations++;
			s = populateBuildOrder((EcBuildOrder) source, arg0);
			score = destination.score(doEvaluate(s));
			
			// System.out.println(chrome);
//			scoreMap.put(chrome.hashCode(), score);
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

	public String getBuildOrder(IChromosome arg0)
	{
		//this is basically just a copy from the doEvaluate() function adjusted to return a build order string
		EcBuildOrder s;
		try
		{
			s = populateBuildOrder((EcBuildOrder) source, arg0);
			
			StringBuilder sb = new StringBuilder();
			
			int i = 0;
			for (EcAction a : s.getActions())
			{
				i++;
				if (a.isInvalid(s))
				{
					continue;
				}
				while (!a.canExecute(s))
				{
					if (s.seconds >= s.targetSeconds || destination.waypointMissed(s))
					{
						return "No finished build yet. A waypoint was not reached.";
					}
					
					if (destination.isSatisfied(s))
					{
						return sb.toString();
					}
				}
				
				if (!(a instanceof EcActionWait) && !(a instanceof EcActionBuildDrone))
				{
					sb.append((int)s.supplyUsed + "  " + a.toBuildOrderString() + "\tM:" + (int)s.minerals + "\tG:" + (int)s.gas + "\n");	
				}
				
				a.execute(s, this);
			}

			return "No finished build yet. Ran out of things to do.";
			
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			return "";
		}
	}

	public String getYabotBuildOrder(IChromosome arg0)
	{
		//Yabot build order encoder
		EcBuildOrder s;
		try
		{
			s = populateBuildOrder((EcBuildOrder) source, arg0);
			
			StringBuilder sb = new StringBuilder();
			
			sb.append("1 [i] EC Optimized Build | 11 | EvolutionChamber | Add description here please. [/i] [s] ");
			
			int i = 0;
			for (EcAction a : s.getActions())
			{
				i++;
				if (a.isInvalid(s))
				{
					continue;
				}
				while (!a.canExecute(s))
				{					
					if (s.seconds >= s.targetSeconds || destination.waypointMissed(s))
					{
						return "No finished build yet. A waypoint was not reached.";
					}
					
					if (destination.isSatisfied(s))
					{
						sb.deleteCharAt(sb.length() - 1); // remove trailing pipe |
						sb.append(" [/s]"); // Add ending tag
						
						if (sb.length() > 770)
						{
							sb.append("\nBuild was too long. Please trim it by " + (sb.length() - 770) + " characters or try a new build.");
							sb.append("\nThis YABOT string will not work until you fix this!");
						}
						return sb.toString();
					}
				}
				
				if (!(a instanceof EcActionWait) && !(a instanceof EcActionBuildDrone))
				{
					if(!(a instanceof EcActionExtractorTrick))
					{
						sb.append(" " + (int)s.supplyUsed + "  " + (int)s.minerals + "  " + (int)s.gas + "  " + s.timestamp() + " 1 " + a.yabotGetType() + "  " + a.yabotGetItem() + " 0" + a.yabotGetTag() + "|");
					}
					else
					{
						// Yabot doesn't support extractor trick so the author suggested telling it to build an extractor and send a cancel string shortly after for the same building
						sb.append(" " + (int)s.supplyUsed + "  " + (int)s.minerals + "  " + (int)s.gas + "  " + s.timestamp() + " 1 " + "0" + "  " + "35" + " 0" + " Extractor_Trick " + "|");
						sb.append(" " + (int)s.supplyUsed + "  " + (int)s.minerals + "  " + (int)s.gas + "  " + s.timestampIncremented(3) + " 1 " + "0" + "  " + "35" + " 1" + " Extractor_Trick " + "|");
					}
				}
				
				a.execute(s, this);
			}

			return "No finished build yet. Ran out of things to do.";
			
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			return "";
		}
	}
	
	public static EcBuildOrder populateBuildOrder(EcBuildOrder source, IChromosome arg0)
			throws CloneNotSupportedException
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

	public PrintStream	log	= System.out;

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
				if (s.seconds >= s.targetSeconds || destination.waypointMissed(s))
				{
					if (debug)
					{
						log.println("Failed to meet waypoint. " + a);
						log.println(s.toLongString());
					}
					return s;
				}
				if (debug)
				{
					int waypointIndex = 0;
					for (EcState se : destination.waypoints)
					{
						if (se.targetSeconds == s.seconds && se.getSumStuff() > 0)
						{
							log.println("---Waypoint " + waypointIndex + "---");
							log.println(s.toLongString());
							log.println("----------------");
						}
						waypointIndex++;
					}
				}
				if (destination.isSatisfied(s))
				{ 
					if (debug)
					{
						log.println("Satisfied.");
						log.println("Number of actions in build order: " + (i - s.invalidActions));
						log.println("---Final Output---");
						log.println(s.toLongString());
						log.println("------------------");
					}
					return s;
				}
			}
			
			if (debug && !(a instanceof EcActionWait))
				log.println(s.toString() + "\t" + a);

			a.execute(s, this);
		}
		if (debug)
		{
			log.println("Ran out of things to do.");
			log.println(s.toLongString());
		}
		return s;
	}

	public void obtained(EcBuildOrder s, String string)
	{
		log.println("@" + s.timestamp() + "\tSpawned:\t" + string.trim());
	}

	public void evolved(EcBuildOrder s, String string)
	{
		log.println("@" + s.timestamp() + "\tEvolved:\t" + string.trim());
	}

	public void mining(EcBuildOrder s, String string)
	{
		log.println("@" + s.timestamp() + "\tMining: \t" + string.trim());
	}

}
