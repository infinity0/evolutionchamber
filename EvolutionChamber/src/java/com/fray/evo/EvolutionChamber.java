package com.fray.evo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.GeneticOperator;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.event.GeneticEvent;
import org.jgap.event.GeneticEventListener;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

import com.fray.evo.action.EcAction;

public class EvolutionChamber
{
	//The seeds files. (one and a backup, in case execution stops while the file is half written)
	private static final String	SEEDS_EVO	= "c:\\seeds.evo";
	private static final String	SEEDS_EVO_2	= "c:\\seeds2.evo";
	
	static final double	BASE_CHANCE	= 1;
	static final int	CHROMOSOME_LENGTH = 50;
	static final int	NUM_THREADS = 4;
	static final int	POPULATION_SIZE	= 300;
	
	static Double	bestScore	= new Double(0);
	
	static List<EcBuildOrder> seeds = new ArrayList<EcBuildOrder>();
	
	public static void main(String[] args) throws InvalidConfigurationException
	{
		EcState s = importSource();
		EcState d = importDestination();
		EcAction.setup(d);

		//We are using the 'many small villages' vs 'one large city' method of evolution.
		for (int thread = 0; thread < NUM_THREADS; thread++)
		{
			Configuration conf = new DefaultConfiguration(thread + " thread.", thread + " thread.");

			final EcEvolver myFunc = new EcEvolver(s, d);
			conf.setFitnessFunction(myFunc);

			conf.addGeneticOperator(EcGeneticUtil.getInsertionOperator());
			conf.addGeneticOperator(EcGeneticUtil.getDeletionOperator());
			conf.addGeneticOperator(EcGeneticUtil.getTwiddleOperator());
			conf.addGeneticOperator(EcGeneticUtil.getSwapOperator());
			// conf.addGeneticOperator(getShortenOperator());
			// conf.addGeneticOperator(getLengthenOperator());
			conf.setPopulationSize(POPULATION_SIZE);
			conf.setSelectFromPrevGen(.9);
			conf.setPreservFittestIndividual(true);
			conf.setAlwaysCaculateFitness(false);
			conf.setKeepPopulationSizeConstant(false);

			Gene[] initialGenes = importInitialGenes(conf);
			Chromosome c = new Chromosome(conf, initialGenes);
			conf.setSampleChromosome(c);
			
			final Genotype population = Genotype.randomInitialGenotype(conf);
			
			if (thread == 0) //On first thread only
				loadOldBuildOrders(population, conf,myFunc);
			
			conf.getEventManager().addEventListener(GeneticEvent.GENOTYPE_EVOLVED_EVENT, new GeneticEventListener()
			{
				public void geneticEventFired(GeneticEvent a_firedEvent)
				{
					IChromosome fittestChromosome = population.getFittestChromosome();
					if (fittestChromosome.getFitnessValue() > bestScore)
					{
						synchronized (bestScore)
						{
							bestScore = fittestChromosome.getFitnessValue();
							displayBuildOrder(myFunc, fittestChromosome);
							System.out.println(new Date() + ": " + fittestChromosome.getFitnessValue());
							displayChromosome(fittestChromosome);
							saveSeeds(fittestChromosome);
							System.out.println();
						}
					}
				}
			});
			final Thread t1 = new Thread(population);
			t1.start();
		}
		while (true)
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}

	}

	private static void displayChromosome(IChromosome fittestChromosome)
	{
		int i = 0;
		for (Gene g : fittestChromosome.getGenes())
		{
			if (i++ == 100)
				break;
			if (((Integer)g.getAllele()).intValue() >= 10)
				System.out.print(((char)((int)'a'+(Integer)g.getAllele()-10)));
			else
				System.out.print(g.getAllele().toString());
		}
	}

	private static void displayBuildOrder(final EcEvolver myFunc, IChromosome fittestChromosome)
	{
		myFunc.debug = true;
		myFunc.evaluate(fittestChromosome);
		myFunc.debug = false;
	}

	private static synchronized void loadOldBuildOrders(Genotype population, Configuration conf, EcEvolver myFunc)
	{
		loadSeeds();
		
		int cindex = 0;
		for (EcBuildOrder bo : seeds)
		{
			try
			{
				Chromosome c = buildChromosome(conf, bo);
				System.out.println(myFunc.getFitnessValue(c));
				population.getPopulation().setChromosome(cindex++, c);
			}
			catch (InvalidConfigurationException e)
			{
				e.printStackTrace();
			}
		}
	}

	private static void loadSeeds()
	{
		try
		{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SEEDS_EVO));
			seeds = (List<EcBuildOrder>) ois.readObject();
			ois.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			try
			{
				ObjectInputStream ois;
				ois = new ObjectInputStream(new FileInputStream(SEEDS_EVO_2));
				seeds = (List<EcBuildOrder>) ois.readObject();
				ois.close();
			}
			catch (FileNotFoundException e1)
			{
				e1.printStackTrace();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			catch (ClassNotFoundException ex)
			{
				e.printStackTrace();
			}
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	static boolean haveSavedBefore = false;
	protected static synchronized void saveSeeds(IChromosome fittestChromosome)
	{
		EcBuildOrder bo = importSource();
		try
		{
			bo = EcEvolver.populateBuildOrder(bo, fittestChromosome);
			if (haveSavedBefore)
				seeds.remove(seeds.size()-1);
			haveSavedBefore = true;
			seeds.add(bo);
		}
		catch (CloneNotSupportedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SEEDS_EVO,false));
			oos.writeObject(seeds);
			oos.close();
			oos = new ObjectOutputStream(new FileOutputStream(SEEDS_EVO_2,false));
			oos.writeObject(seeds);
			oos.close();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static Gene[] importInitialGenes(Configuration conf)
	{
		ArrayList<Gene> genes = new ArrayList<Gene>();
		for (int i = 0; i < CHROMOSOME_LENGTH; i++)
			try
			{
				IntegerGene g = new IntegerGene(conf, 0, EcAction.actions.size()-1);
				g.setAllele(0);
				genes.add(g);
			}
			catch (InvalidConfigurationException e)
			{
				e.printStackTrace();
			}
		return genes.toArray(new Gene[genes.size()]);
	}

	private static EcBuildOrder importSource()
	{
		EcBuildOrder ecBuildOrder = new EcBuildOrder();
		ecBuildOrder.targetSeconds = importDestination().targetSeconds;
		return ecBuildOrder;
	}

	private static EcState importDestination()
	{
		return EcState.defaultDestination();
	}

	static Chromosome buildChromosome(Configuration conf, EcBuildOrder bo) throws InvalidConfigurationException
	{
		ArrayList<Gene> genes = new ArrayList<Gene>();
		int CC = 0;
		for (EcAction a : bo.actions)
		{
			if (++CC > CHROMOSOME_LENGTH)
				continue;
			IntegerGene g = new IntegerGene(conf, 0, EcAction.actions.size() - 1);
			Integer allele = EcAction.findAllele(a);
			if (allele == null)
				break;
			g.setAllele(allele);
			genes.add(g);
	
		}
		while (genes.size() < CHROMOSOME_LENGTH)
		{
			IntegerGene g = new IntegerGene(conf, 0, EcAction.actions.size() - 1);
			g.setAllele(0);
			genes.add(g);
		}
		Chromosome c = new Chromosome(conf);
		c.setGenes(genes.toArray(new Gene[genes.size()]));
		c.setIsSelectedForNextGeneration(true);
		return c;
	}
}
//public static void main2() throws InvalidConfigurationException
//{
//	Configuration conf = new DefaultConfiguration(" thread.", "No name.");
//
//	EcState s = importSource();
//	EcState d = importDestination();
//	boolean done = false;
//	String genes = "";
//	String best = null;
//	double bestScore = 0.0;
//
//	final EcEvolver myFunc = new EcEvolver(s, d);
//	while (genes.length() < 50)
//	{
//		genes = incrementGenes(genes.toCharArray());
//		double score = myFunc.evaluate(genes.toCharArray());
//		// System.out.println(score + ": " + genes);
//		if (bestScore < score)
//		{
//			best = genes;
//			bestScore = score;
//			System.out.println(score + ": " + genes);
//		}
//	}
//}
//
//private static IChromosome toChromosome(String geneString, Configuration conf) throws InvalidConfigurationException
//{
//	ArrayList<Gene> genes = new ArrayList<Gene>();
//	for (int i = 0; i < geneString.length(); i++)
//		try
//		{
//			IntegerGene g = new IntegerGene(conf, 0, 5);
//			g.setAllele(geneString.charAt(i) - '0');
//			genes.add(g);
//		}
//		catch (InvalidConfigurationException e)
//		{
//			e.printStackTrace();
//		}
//	Chromosome c = new Chromosome(conf);
//	c.setGenes(genes.toArray(new Gene[0]));
//	return c;
//}
//
//private static String incrementGenes(char[] genes)
//{
//	boolean doExtend = true;
//	char max = '5';
//	char min = '0';
//	for (int i = 0; i < genes.length; i++)
//		if (genes[i] != max)
//			doExtend = false;
//
//	if (!doExtend)
//	{
//		do
//		{
//			increment(genes, 0, min, max);
//		} while (!isValid(genes, min, max));
//		return new String(genes);
//	}
//	for (int i = 0; i < genes.length; i++)
//		genes[i] = min;
//	return new String(genes) + min;
//}
//
//private static boolean isValid(char[] genes, char min, char max)
//{
//	boolean[] b = new boolean[max - min + 1];
//	for (char c : genes)
//	{
//		b[c - min] = true; // Mark as seen.
//		if (c == '3' || c == '4') // Zergling/Queen requires Spawning pool.
//			if (b[2] == false)
//				return false;
//	}
//	return true;
//}
//
//private static void increment(char[] genes, int i, char min, char max)
//{
//	if (genes[i] == max)
//	{
//		genes[i] = min;
//		increment(genes, i + 1, min, max);
//	}
//	else
//		genes[i]++;
//}
