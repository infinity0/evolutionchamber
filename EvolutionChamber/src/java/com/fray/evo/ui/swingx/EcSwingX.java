package com.fray.evo.ui.swingx;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXStatusBar;
import org.jgap.InvalidConfigurationException;

import com.fray.evo.EcEvolver;
import com.fray.evo.EcReportable;
import com.fray.evo.EcSettings;
import com.fray.evo.EcState;
import com.fray.evo.EvolutionChamber;
import com.fray.evo.util.EcAutoUpdate;

public class EcSwingX extends JXPanel implements EcReportable
{
	public static String		EC_VERSION		= "0017";
	private JTextArea			outputText;
	private JLabel				status1;
	private JLabel				status2;
	private JLabel				status3;
	protected long				timeStarted;
	protected long				lastUpdate;
	private String				simpleBuildOrder;
	private String				detailedBuildOrder;
	private boolean				isSimpleBuildOrder;
	int							gridy			= 0;
	private JXStatusBar			statusbar;
	private List<JComponent>	inputControls	= new ArrayList<JComponent>();

	EvolutionChamber			ec				= new EvolutionChamber();
	EcState[]					destination;
	private JButton				goButton;
	private JButton				stopButton;
	private JButton				clipboardButton;
	private JButton				switchOutputButton;
	private JTextArea			statsText;
	private JTabbedPane			tabPane;

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					javax.swing.UIManager.setLookAndFeel("com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				final JFrame frame = new JFrame();
				frame.setTitle("Evolution Chamber v" + EC_VERSION);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(new EcSwingX());
				frame.setPreferredSize(new Dimension(900, 800));
				frame.pack();
				frame.setLocationRelativeTo(null);

				final JFrame updateFrame = new JFrame();
				updateFrame.setTitle("Automatic Update");
				JLabel waiting = new JLabel("Checking for updates...");
				updateFrame.getContentPane().setLayout(new FlowLayout());
				updateFrame.getContentPane().add(waiting);
				updateFrame.setSize(new Dimension(250, 70));
				updateFrame.setLocationRelativeTo(null);
				updateFrame.setVisible(true);
				
				SwingUtilities.invokeLater(new Runnable()
				{
					@Override
					public void run()
					{
						EcAutoUpdate ecUpdater = checkForUpdates();
						// Show the main window only when there are no updates
						// running
						frame.setVisible(!ecUpdater.isUpdating());
						updateFrame.setVisible(ecUpdater.isUpdating());
					}
				});
			}
		});
	}

	public EcSwingX()
	{
		initializeWaypoints();

		setLayout(new BorderLayout());

		JSplitPane outside = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		{ // Left
			JPanel leftbottom = new JPanel(new GridBagLayout());
			JScrollPane stuffPanel = new JScrollPane(leftbottom);
			{
				{
					addControlParts(leftbottom);
					tabPane = new JTabbedPane(JTabbedPane.LEFT);
					{
						for (int i = 0; i < 5; i++)
						{
							JPanel lb = new JPanel(new GridBagLayout());
							if (i == 4)
								tabPane.addTab("Final", lb);
							else
								tabPane.addTab("WP" + Integer.toString(i), lb);
							addInputContainer(i, lb);
						}
						JPanel stats = new JPanel(new BorderLayout());
						addStats(stats);
						JPanel settings = new JPanel(new GridBagLayout());
						addSettings(settings);
						tabPane.addTab("Stats", stats);
						tabPane.addTab("Settings", settings);
						tabPane.setSelectedIndex(4);
					}
					GridBagConstraints gridBagConstraints = new GridBagConstraints();
					gridBagConstraints.anchor = GridBagConstraints.WEST;
					gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
					gridBagConstraints.weightx = .25;
					gridBagConstraints.gridy = gridy;
					gridBagConstraints.gridwidth = 4;
					gridBagConstraints.insets = new Insets(1, 1, 1, 1);
					leftbottom.add(tabPane, gridBagConstraints);
					addStatusBar(leftbottom);
				}
			}
			outside.setLeftComponent(stuffPanel);
		}
		{ //Right
			JPanel right = new JPanel(new GridBagLayout());
			addOutputContainer(right);
			addOutputButtons(right);
			outside.setRightComponent(right);
		}

		add(outside);
		outside.setDividerLocation(395);
	}

	private void addSettings(JPanel settings)
	{
		addCheck(settings, "Use Extractor Trick", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				EcSettings.useExtractorTrick = getTrue(e);
			}
		}).setSelected(EcSettings.useExtractorTrick);
		gridy++;
		addCheck(settings, "Pull/Push workers to/from gas", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				EcSettings.pullWorkersFromGas = getTrue(e);
			}
		}).setSelected(EcSettings.useExtractorTrick);
		gridy++;
		addInput(settings, "Minimum Pool Supply", new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EcSettings.minimumPoolSupply = getDigit(e);
				
			}
		}).setText("2");
		gridy++;
		addInput(settings, "Minimum Extractor Supply", new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EcSettings.minimumExtractorSupply = getDigit(e);
				
			}
		}).setText("2");
		gridy++;
		addInput(settings, "Minimum Hatchery Supply", new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EcSettings.minimumHatcherySupply = getDigit(e);
				
			}
		}).setText("2");
	}

	private void initializeWaypoints()
	{
		try
		{
			destination = new EcState[5];
			destination[0] = (EcState) ec.getInternalDestination().clone();
			destination[0].targetSeconds = 3 * 60;
			destination[1] = (EcState) ec.getInternalDestination().clone();
			destination[1].targetSeconds = 6 * 60;
			destination[2] = (EcState) ec.getInternalDestination().clone();
			destination[2].targetSeconds = 9 * 60;
			destination[3] = (EcState) ec.getInternalDestination().clone();
			destination[3].targetSeconds = 12 * 60;
			destination[4] = (EcState) ec.getInternalDestination().clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
	}

	private void addStats(JPanel stats)
	{
		stats.add(statsText = new JTextArea());
		statsText.setEditable(false);
		statsText.setAlignmentX(0);
		statsText.setAlignmentY(0);
		statsText.setTabSize(4);
	}

	private void addStatusBar(JPanel leftbottom)
	{
		statusbar = new JXStatusBar();
		status1 = new JLabel("Ready.");
		statusbar.add(status1);
		status2 = new JLabel("Not Running.");
		statusbar.add(status2);
		status3 = new JLabel("");
		statusbar.add(status3);

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.SOUTH;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = .5;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.gridy = gridy + 1;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		leftbottom.add(statusbar, gridBagConstraints);
		Timer t = new Timer(200, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (timeStarted == 0)
					status1.setText("Ready");
				else
				{
					long ms = new Date().getTime() - timeStarted;
					long seconds = ms / 1000;
					long minutes = seconds / 60;
					long hours = minutes / 60;
					status1.setText("Running for " + hours % 60 + ":" + minutes % 60 + ":" + seconds % 60);
				}
				if (lastUpdate != 0)
				{
					long ms = new Date().getTime() - lastUpdate;
					long seconds = ms / 1000;
					long minutes = seconds / 60;
					long hours = minutes / 60;
					status2.setText("Last update: " + hours % 60 + ":" + minutes % 60 + ":" + seconds % 60 + " ago");
					{
						double evalseconds = (System.currentTimeMillis() - timeStarted);
						evalseconds = evalseconds / 1000.0;
						double permsPerSecond = EcEvolver.evaluations;
						permsPerSecond /= evalseconds;
						StringBuilder stats = new StringBuilder();
						int threadIndex = 0;
						stats.append(EcEvolver.evaluations / 1000 + "K games played.");
						stats.append("\n" + ec.CHROMOSOME_LENGTH + " maximum length of build order.");
						stats.append("\nStagnation Limit: "+ec.stagnationLimit);
						stats.append("\n" + (int) permsPerSecond + " games played/second.");
						stats.append("\nMutation Rate: " + ec.BASE_MUTATION_RATE / ec.CHROMOSOME_LENGTH);
						for (Double d : ec.bestScores)
							stats.append("\nProcessor " + threadIndex + " age: ("
									+ ec.evolutionsSinceDiscovery[threadIndex++] + ") score: " + d);
						statsText.setText(stats.toString());
					}
				}
				statusbar.repaint();
			}
		});
		t.start();
	}

	private void addOutputContainer(JPanel component)
	{
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		component.add(new JScrollPane(outputText = new JTextArea()), gridBagConstraints);
		outputText.setAlignmentX(0);
		outputText.setAlignmentY(0);
		outputText.setTabSize(4);
		outputText.setEditable(false);
		StringBuilder sb = new StringBuilder();
		sb.append("Hello! Welcome to the Evolution Chamber.");
		sb.append("\nTo start, enter in some units you would like to have.");
		sb.append("\nWhen you have decided what you would like, hit Start.");
		sb.append("\n\nPlease report any issues or new features you would like at: \nhttp://code.google.com/p/evolutionchamber/issues/list");
		sb.append("\n\nHow to use:");
		sb.append("\nEnter in what you would like to see as your end state. Hit Go. Be patient.");
		sb.append("\nThe build order will compute, and it can take several minutes to potentially hours.");
		sb.append("\nAll build orders are automatically saved when you exit or hit stop, so, if you put in");
		sb.append("\nthe same build order, you will get the same result.");
		sb.append("\n\nRemember that this is evolutionary science, so don't be surprised if things seem wonky!");
		sb.append("\n\nHow to use waypoints:");
		sb.append("\nTo use waypoints, enter first what you would like at the end of the build.");
		sb.append("\nThen go to a waypoint slot, enter a deadline time, and units.");
		sb.append("\nAll the waypoints are cumulative, so if you enter 6 zergling@3:00 on WP1,");
		sb.append("\n7 roach@6:00 on WP2, and 6 muta on final, you will end up with 6 lings,");
		sb.append("\n7 roaches, and 6 mutas by the time it finds a valid build.");
		sb.append("\n\nCurrent staff:");
		sb.append("\nAzzurite (UI)");
		sb.append("\nDocMaboul (Timing)");
		sb.append("\nLomilar (Lead)");
		sb.append("\nNetprobe (Auto-updater)");
		sb.append("\nUtena (Genetics)");
		sb.append("\nAbout 27 other people, who's names I need to compile (Testing)");
		simpleBuildOrder = sb.toString();
		detailedBuildOrder = sb.toString();
		outputText.setText(sb.toString());
	}

	private void addInputContainer(final int i, JPanel component)
	{
		// addInput(component, "", new ActionListener()
		// {
		// public void actionPerformed(ActionEvent e)
		// {
		// ec.POPULATION_SIZE = getDigit(e);
		// }
		// }).setText("30");
		// addInput(component, "Chromosome Length", new ActionListener()
		// {
		// public void actionPerformed(ActionEvent e)
		// {
		// ec.CHROMOSOME_LENGTH = getDigit(e);
		// }
		// }).setText("120");
		addInput(component, "Drones", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].drones = getDigit(e);
			}
		});
		addInput(component, "Deadline", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].targetSeconds = getDigit(e);
			}
		}).setText(
				Integer.toString(destination[i].targetSeconds / 60) + ":"
						+ Integer.toString(destination[i].targetSeconds % 60));
		gridy++;
		addInput(component, "Overlords", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].overlords = getDigit(e);
			}
		});
		addInput(component, "Overseers", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].overseers = getDigit(e);
			}
		});
		gridy++;
		addCheck(component, "Pneumatized Carapace", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].pneumatizedCarapace = getTrue(e);
			}
		});
		addCheck(component, "Ventral Sacs", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].ventralSacs = getTrue(e);
			}
		});
		gridy++;
		addInput(component, "Queens", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].queens = getDigit(e);
			}
		});
		addCheck(component, "Burrow", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].burrow = getTrue(e);
			}
		});
		gridy++;
		addInput(component, "Zerglings", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].zerglings = getDigit(e);
			}
		});
		gridy++;
		addCheck(component, "Metabolic Boost", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].metabolicBoost = getTrue(e);
			}
		});
		addCheck(component, "Adrenal Glands", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].adrenalGlands = getTrue(e);
			}
		});
		gridy++;
		addInput(component, "Banelings", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].banelings = getDigit(e);
			}
		});
		addCheck(component, "Centrifugal Hooks", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].centrifugalHooks = getTrue(e);
			}
		});
		gridy++;
		addInput(component, "Roaches", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].roaches = getDigit(e);

			}
		});
		gridy++;
		addCheck(component, "Glial Reconstitution", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].glialReconstitution = getTrue(e);
			}
		});
		addCheck(component, "Tunneling Claws", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].tunnelingClaws = getTrue(e);
			}
		});
		gridy++;
		addInput(component, "Hydralisks", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].hydralisks = getDigit(e);
			}
		});
		addCheck(component, "Grooved Spines", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].groovedSpines = getTrue(e);
			}
		});
		gridy++;
		addInput(component, "Infestors", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].infestors = getDigit(e);
			}
		});
		gridy++;
		addCheck(component, "Neural Parasite", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].neuralParasite = getTrue(e);
			}
		});
		addCheck(component, "Pathogen Glands", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].pathogenGlands = getTrue(e);
			}
		});
		gridy++;
		addInput(component, "Mutalisks", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].mutalisks = getDigit(e);
			}
		});
		gridy++;
		addInput(component, "Ultralisks", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].ultralisks = getDigit(e);
			}
		});
		addCheck(component, "Chitinous Plating", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].chitinousPlating = getTrue(e);
			}
		});
		gridy++;
		addInput(component, "Corruptors", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].corruptors = getDigit(e);
			}
		});
		addInput(component, "Broodlords", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].broodlords = getDigit(e);
			}
		});
		gridy++;
		addCheck(component, "Melee +1", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].melee1 = getTrue(e);
			}
		});
		addCheck(component, "+2", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].melee2 = getTrue(e);
			}
		});
		addCheck(component, "+3", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].melee3 = getTrue(e);
			}
		});
		gridy++;
		addCheck(component, "Missile +1", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].missile1 = getTrue(e);
			}
		});
		addCheck(component, "+2", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].missile2 = getTrue(e);
			}
		});
		addCheck(component, "+3", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].missile3 = getTrue(e);
			}
		});
		gridy++;
		addCheck(component, "Carapace +1", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].armor1 = getTrue(e);
			}
		});
		addCheck(component, "+2", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].armor2 = getTrue(e);
			}
		});
		addCheck(component, "+3", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].armor3 = getTrue(e);
			}
		});
		gridy++;
		addCheck(component, "Flyer Attack +1", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].flyerAttack1 = getTrue(e);
			}
		});
		addCheck(component, "+2", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].flyerAttack2 = getTrue(e);
			}
		});
		addCheck(component, "+3", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].flyerAttack3 = getTrue(e);
			}
		});
		gridy++;
		addCheck(component, "Flyer Armor +1", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].flyerArmor1 = getTrue(e);
			}
		});
		addCheck(component, "+2", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].flyerArmor2 = getTrue(e);
			}
		});
		addCheck(component, "+3", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].flyerArmor3 = getTrue(e);
			}
		});
		gridy++;
		addInput(component, "Hatcheries", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].hatcheries = getDigit(e);
			}
		});
		addInput(component, "Lairs", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].lairs = getDigit(e);
			}
		});
		gridy++;
		addInput(component, "Hives", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].hives = getDigit(e);
			}
		});
		addInput(component, "Gas Extractors", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].gasExtractors = getDigit(e);
			}
		});
		gridy++;
		addInput(component, "Evolution Chambers", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].evolutionChambers = getDigit(e);
			}
		});
		gridy++;
		addInput(component, "Spine Crawlers", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].spineCrawlers = getDigit(e);
			}
		});
		addInput(component, "Spore Crawlers", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].sporeCrawlers = getDigit(e);
			}
		});
		gridy++;
		addInput(component, "Spawning Pools", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].spawningPools = getDigit(e);
			}
		});
		addInput(component, "Baneling Nests", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].banelingNest = getDigit(e);
			}
		});
		gridy++;
		addInput(component, "Roach Warrens", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].roachWarrens = getDigit(e);
			}
		});
		addInput(component, "Hydralisk Dens", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].hydraliskDen = getDigit(e);
			}
		});
		gridy++;
		addInput(component, "Infestation Pits", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].infestationPit = getDigit(e);
			}
		});
		addInput(component, "Spires", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].spire = getDigit(e);
			}
		});
		gridy++;
		addInput(component, "Nydus Networks", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].nydusNetwork = getDigit(e);
			}
		});
		addInput(component, "Nydus Worms", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].nydusWorm = getDigit(e);
			}
		});
		gridy++;
		addInput(component, "Ultralisk Caverns", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].ultraliskCavern = getDigit(e);
			}
		});
		addInput(component, "Greater Spires", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].greaterSpire = getDigit(e);
			}
		});
	}
	
	private void addOutputButtons(JPanel component)
	{
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		gridBagConstraints.weightx = 0.25;
		clipboardButton = new JButton("Copy to clipboard");
		component.add(clipboardButton, gridBagConstraints);
		clipboardButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(new StringSelection(outputText.getText()), null);
			}
		});
		
		switchOutputButton = new JButton("Switch to simple format");
		isSimpleBuildOrder = false;
		gridBagConstraints.weightx = 0.75;
		component.add(switchOutputButton, gridBagConstraints);
		switchOutputButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (isSimpleBuildOrder) {
					outputText.setText(detailedBuildOrder);
					outputText.setTabSize(4);
					switchOutputButton.setText("Switch to simple format");
					isSimpleBuildOrder = false;
				} else {
					outputText.setText(simpleBuildOrder);
					outputText.setTabSize(14);
					switchOutputButton.setText("Switch to detailed format");
					isSimpleBuildOrder = true;
				}
			}
		});
	}
	
	private void addControlParts(JPanel component)
	{
		addInput(component, "Processors", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ec.setThreads(getDigit(e));
				((JTextField) e.getSource()).setText(Integer.toString(ec.getThreads()));
			}
		}).setText(Integer.toString(ec.getThreads()));
		stopButton = addButton(component, "Stop", new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				ec.stop();
				goButton.setEnabled(true);
				stopButton.setEnabled(false);
				timeStarted = 0;
				for (JComponent j : inputControls)
					j.setEnabled(true);
				lastUpdate = 0;
			}
		});
		stopButton.setEnabled(false);
		final EcReportable ri = this;
		goButton = addButton(component, "Start", new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ec.reportInterface = ri;

				for (JComponent j : inputControls)
					j.setEnabled(false);
				restartChamber();
				tabPane.setSelectedIndex(5);
				timeStarted = new Date().getTime();
				goButton.setEnabled(false);
				stopButton.setEnabled(true);

				EcEvolver.evaluations = 0;
				EcEvolver.cachehits = 0;
			}
		});
		gridy++;
	}

	private JButton addButton(JPanel container, String string, ActionListener actionListener)
	{
		final JButton button = new JButton();

		button.setText(string);
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = .25;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		container.add(button, gridBagConstraints);
		button.addActionListener(actionListener);
		return button;
	}

	protected int getDigit(ActionEvent e)
	{
		JTextField tf = (JTextField) e.getSource();
		String text = tf.getText();
		try
		{
			if (text.contains(":"))
			{
				String[] split = text.split(":");
				if (Integer.parseInt(split[0]) < 0)
					throw new NumberFormatException();
				if (Integer.parseInt(split[1]) < 0)
					throw new NumberFormatException();
				return Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);
			}

			Integer i = Integer.parseInt(text);
			if (i < 0)
				throw new NumberFormatException();
			return i;
		}
		catch (ArrayIndexOutOfBoundsException ex)
		{
			tf.setText("0");
			return 0;
		}
		catch (NumberFormatException ex)
		{
			tf.setText("0");
			return 0;
		}
	}

	private void restartChamber()
	{
		if (ec.threads.size() > 0)
			ec.stop();
		try
		{
			EcState finalDestination = (EcState) destination[destination.length - 1].clone();
			for (int i = 0; i < destination.length - 1; i++)
				finalDestination.waypoints.add((EcState) destination[i].clone());

			ec.setDestination(finalDestination);
			ec.go();
		}
		catch (InvalidConfigurationException e1)
		{
			e1.printStackTrace();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
	}

	protected boolean getTrue(ActionEvent e)
	{
		JCheckBox tf = (JCheckBox) e.getSource();
		this.ec.bestScore = new Double(0);
		return tf.isSelected();
	}

	private JTextField addInput(JPanel container, String name, final ActionListener a)
	{
		GridBagConstraints gridBagConstraints;

		JXLabel label = new JXLabel("  " + name);
		label.setAlignmentX(.5f);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = .25;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		container.add(label, gridBagConstraints);

		final JTextField textField = new JTextField();
		textField.setColumns(5);
		textField.setText("0");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = .25;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		container.add(textField, gridBagConstraints);
		textField.addFocusListener(new FocusListener()
		{

			@Override
			public void focusLost(FocusEvent e)
			{
				a.actionPerformed(new ActionEvent(e.getSource(), 0, "changed"));
			}

			@Override
			public void focusGained(FocusEvent e)
			{
			}
		});
		inputControls.add(label);
		inputControls.add(textField);
		return textField;
	}

	private JCheckBox addCheck(JPanel container, String name, final ActionListener a)
	{
		GridBagConstraints gridBagConstraints;

		final JCheckBox checkBox = new JCheckBox();
		checkBox.setText(name);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = .5;
		if (name.length() == 2)
			gridBagConstraints.gridwidth = 1;
		else
			gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		container.add(checkBox, gridBagConstraints);
		checkBox.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				a.actionPerformed(new ActionEvent(checkBox, 0, "changed"));
			}
		});
		inputControls.add(checkBox);
		return checkBox;
	}

	private static EcAutoUpdate checkForUpdates()
	{
		EcAutoUpdate ecUpdater = new EcAutoUpdate(EC_VERSION);
		if (ecUpdater.isUpdateAvailable())
		{
			JOptionPane pane = new JOptionPane(
					"A newer version of the Evolution Chamber is available. Do you want to update now?");
			Object[] options = new String[] { "Yes", "No" };
			pane.setOptions(options);
			JDialog dialog = pane.createDialog(new JFrame(), "Evolution Chamber Update Available");
			dialog.setVisible(true);

			Object obj = pane.getValue();

			if (options[0].equals(obj))
			{ // User selection = "Yes"
				JFrame updateFrame = new JFrame();
				updateFrame.setTitle("Updating");

				final JProgressBar updateProgress = new JProgressBar(0, 100);
				updateProgress.setValue(0);
				updateProgress.setStringPainted(true);
				updateFrame.add(updateProgress);
				updateFrame.setPreferredSize(new Dimension(200, 100));
				updateFrame.pack();
				updateFrame.setLocationRelativeTo(null);
				updateFrame.setVisible(true);
				ecUpdater.addPropertyChangeListener(new PropertyChangeListener()
				{
					public void propertyChange(PropertyChangeEvent evt)
					{
						if ("progress".equals(evt.getPropertyName()))
						{
							updateProgress.setValue((Integer) evt.getNewValue());
						}
					}
				});
				ecUpdater.execute();
			}
		}
		return ecUpdater;
	}

	@Override
	public void bestScore(final EcState finalState, int intValue, final String detailedText, final String simpleText)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				simpleBuildOrder = simpleText;
				detailedBuildOrder = detailedText;
				if (isSimpleBuildOrder) 
				{
					outputText.setText(simpleText);
				} else {
					outputText.setText(detailedText);
				}
				lastUpdate = new Date().getTime();
			}
		});
	}

	@Override
	public void threadScore(int threadIndex, String output)
	{
		// TODO Auto-generated method stub

	}

}
