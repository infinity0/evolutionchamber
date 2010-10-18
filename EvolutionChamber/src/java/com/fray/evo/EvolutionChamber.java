package com.fray.evo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.event.GeneticEvent;
import org.jgap.event.GeneticEventListener;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

import com.fray.evo.action.EcAction;
import com.fray.evo.util.EcFileSystem;

public class EvolutionChamber
{
	//The seeds files. (one and a backup, in case execution stops while the file is half written)
	private static File	SEEDS_EVO = null;
	private static File	SEEDS_EVO_2 = null;

	static
	{
		try
		{
			SEEDS_EVO	= new File(EcFileSystem.getTempPath(),"seeds.evo");
			SEEDS_EVO.getParentFile().mkdirs();
			SEEDS_EVO_2	= new File(EcFileSystem.getTempPath(),"seeds2.evo");
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static final double	BASE_CHANCE	= 1;
	public int	CHROMOSOME_LENGTH = 120;
	int	NUM_THREADS = 4;
	public int	POPULATION_SIZE	= 200;

	public Double	bestScore	= new Double(0);

	static List<EcBuildOrder> seeds = new ArrayList<EcBuildOrder>();
	private EcState	destination = EcState.defaultDestination();
	public ActionListener	onNewBuild;
	public List<Thread>	threads = new ArrayList<Thread>();
	private boolean	kill = false;

	public static void main(String[] args) throws InvalidConfigurationException
	{
		new EvolutionChamber().go();
	}

	public void go() throws InvalidConfigurationException
	{
		kill = false;
		EcState s = importSource();
		EcState d = getInternalDestination();
		EcAction.setup(d);
		CHROMOSOME_LENGTH = d.getSumStuff()+70;
		bestScore = new Double(0);

		//We are using the 'many small villages' vs 'one large city' method of evolution.
		for (int threadIndex = 0; threadIndex < NUM_THREADS; threadIndex++)
		{
			spawnEvolutionaryChamber(s, d, threadIndex);
		}
		if (onNewBuild == null)
		while (true)
			try
			{
				Thread.sleep(10000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
	}

	public void stop()
	{
		kill  = true;
		for (Thread t : threads)
			try
			{
				t.join();
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		threads.clear();

	}
	
	private void spawnEvolutionaryChamber(EcState s, EcState d, int threadIndex) throws InvalidConfigurationException
	{
		DefaultConfiguration.reset(threadIndex + " thread.");
		Configuration conf = new DefaultConfiguration(threadIndex + " thread.", threadIndex + " thread.");

		final EcEvolver myFunc = new EcEvolver(s, d);
		conf.setFitnessFunction(myFunc);

		conf.addGeneticOperator(EcGeneticUtil.getInsertionOperator(this));
		conf.addGeneticOperator(EcGeneticUtil.getDeletionOperator(this));
		conf.addGeneticOperator(EcGeneticUtil.getTwiddleOperator(this));
		conf.addGeneticOperator(EcGeneticUtil.getSwapOperator(this));
		conf.setPopulationSize(POPULATION_SIZE);
		conf.setSelectFromPrevGen(.9);
		conf.setPreservFittestIndividual(true);
		conf.setAlwaysCaculateFitness(false);
		conf.setKeepPopulationSizeConstant(false);

		Gene[] initialGenes = importInitialGenes(conf);
		Chromosome c = new Chromosome(conf, initialGenes);
		conf.setSampleChromosome(c);

		final Genotype population = Genotype.randomInitialGenotype(conf);

		if (threadIndex == 0) //On first thread only
			loadOldBuildOrders(population, conf,myFunc);

		final Thread t1 = new Thread(population);
		conf.getEventManager().addEventListener(GeneticEvent.GENOTYPE_EVOLVED_EVENT, new GeneticEventListener()
		{
			@Override
			public void geneticEventFired(GeneticEvent a_firedEvent)
			{
				IChromosome fittestChromosome = population.getFittestChromosome();
				if (kill)
					t1.interrupt();
				if (fittestChromosome.getFitnessValue() > bestScore)
				{
					synchronized (bestScore)
					{
						bestScore = fittestChromosome.getFitnessValue();
						
						ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
						PrintStream ps = new PrintStream(byteArrayOutputStream);
						if (onNewBuild != null)
							myFunc.log = ps;
							
						displayBuildOrder(myFunc, fittestChromosome);
						myFunc.log.println(new Date() + ": " + fittestChromosome.getFitnessValue());
						displayChromosome(fittestChromosome);
						saveSeeds(fittestChromosome);
						onNewBuild.actionPerformed(new ActionEvent(myFunc.evaluateGetBuildOrder(fittestChromosome),bestScore.intValue(),new String(byteArrayOutputStream.toByteArray())));
						System.out.println();
					}
				}
			}
		});
		t1.start();
		threads.add(t1);
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

	private void displayBuildOrder(final EcEvolver myFunc, IChromosome fittestChromosome)
	{
		myFunc.debug = true;
		myFunc.evaluateGetBuildOrder(fittestChromosome);
		myFunc.debug = false;
	}

	private synchronized void loadOldBuildOrders(Genotype population, Configuration conf, EcEvolver myFunc)
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
	protected synchronized void saveSeeds(IChromosome fittestChromosome)
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

	private Gene[] importInitialGenes(Configuration conf)
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

	private EcBuildOrder importSource()
	{
		EcBuildOrder ecBuildOrder = new EcBuildOrder();
		ecBuildOrder.targetSeconds = importDestination().targetSeconds;
		return ecBuildOrder;
	}


	public EcState importDestination()
	{
		try
		{
			return (EcState) getInternalDestination().clone();
		}
		catch (CloneNotSupportedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	Chromosome buildChromosome(Configuration conf, EcBuildOrder bo) throws InvalidConfigurationException
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

	public void setThreads(int digit)
	{
		NUM_THREADS = digit;
	}

	public void setDestination(EcState destination)
	{
		this.destination = destination;
	}

	public EcState getInternalDestination()
	{
		return destination;
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
