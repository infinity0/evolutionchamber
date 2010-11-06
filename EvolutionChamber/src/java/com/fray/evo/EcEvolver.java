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
			Double score;
			evaluations++;
			s = populateBuildOrder((EcBuildOrder) source, arg0);
			score = destination.score(doEvaluate(s));
			return score;
		}
		catch (CloneNotSupportedException e)
		{
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
			return doSimpleEvaluate(s);
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			return "";
		}
	}

	public String doSimpleEvaluate(EcBuildOrder s)
	{
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
				sb.append((int)s.supplyUsed + "  " + a.toBuildOrderString(s) + "\tM:" + (int)s.minerals + "\tG:" + (int)s.gas + "\n");	
			}
			
			a.execute(s, this);
		}

		return "No finished build yet. Ran out of things to do.";
	}

	public String getYabotBuildOrder(IChromosome arg0)
	{
		//Yabot build order encoder
		EcBuildOrder s;
		try
		{
			s = populateBuildOrder((EcBuildOrder) source, arg0);
			
			return doYABOTEvaluate(s);
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
			return "";
		}
	}

	public String doYABOTEvaluate(EcBuildOrder s)
	{
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
					sb.append(" " + (int)s.supplyUsed + "  " + (int)s.minerals + "  " + (int)s.gas + "  " + s.timestamp() + " 1 " + a.yabotGetType(s) + "  " + a.yabotGetItem(s) + " 0" + a.yabotGetTag(s) + "|");
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

	public EcBuildOrder doEvaluate(EcBuildOrder s)
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
					
					if (s.settings.overDrone && s.drones < s.getOverDrones(s))
					{
						if (debug)
						{
							log.println("Failed to have the required " + s.getOverDrones(s) + " drones.");
							log.println(s.toCompleteString());
						}
					}
					else
					{
						if (debug)
						{
							log.println("Failed to meet waypoint. " + a);
							log.println(s.toCompleteString());
						}
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
							log.println(s.toCompleteString());
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

						log.print("-------Goal-------");
						log.println(destination.toUnitsOnlyString());
						log.println("---Final Output---");
						log.println(s.toCompleteString());
						log.println("------------------");
					}
					return s;
				}
			}
			
			if (debug && !(a instanceof EcActionWait))
				log.println(s.toShortString() + "\t" + a);

			a.execute(s, this);
		}
		if (debug)
		{
			log.println("Ran out of things to do.");
			log.println(s.toCompleteString());
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

	public void scout(EcBuildOrder s, String string)
	{
		log.println("@" + s.timestamp() + "\tScout: \t" + string.trim());
	}
	public EcState getDestination()
	{
		return destination;
	}

}
