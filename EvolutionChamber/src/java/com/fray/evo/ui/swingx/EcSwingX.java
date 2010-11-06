package com.fray.evo.ui.swingx;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXStatusBar;
import org.jgap.InvalidConfigurationException;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcReportable;
import com.fray.evo.EcSettings;
import com.fray.evo.EcState;
import com.fray.evo.EvolutionChamber;
import com.fray.evo.action.EcAction;
import com.fray.evo.util.EcAutoUpdate;

public class EcSwingX extends JXPanel implements EcReportable
{
	public static String		EC_VERSION		= "0018";
	private JTextArea			outputText;
	private JLabel				status1;
	private JLabel				status2;
	private JLabel				status3;
	protected long				timeStarted;
	protected long				lastUpdate;
	private String				simpleBuildOrder;
	private String				detailedBuildOrder;
	private String				yabotBuildOrder;
	private boolean				isDetailedBuildOrder;
	private boolean				isYabotBuildOrder;
	private boolean				isSimpleBuildOrder;
	int							gridy			= 0;
	private JXStatusBar			statusbar;
	private List<JComponent>	inputControls	= new ArrayList<JComponent>();

	EvolutionChamber			ec				= new EvolutionChamber();
	EcState[]					destination;
	private JButton				goButton;
	private JButton				stopButton;
	private JButton				clipboardButton;
	private JButton				switchSimpleButton;
	private JButton				switchDetailedButton;
	private JButton				switchYabotButton;
	private JTextArea			statsText;
	private JTabbedPane			tabPane;
	private JList				historyList;

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
				frame.setPreferredSize(new Dimension(900, 830));
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
			JPanel stuffPanel = leftbottom;
			{
				{
					addControlParts(leftbottom);
					tabPane = new JTabbedPane(JTabbedPane.LEFT);
					{
						JPanel start = new JPanel(new BorderLayout());
						addStart(start);
						tabPane.addTab("History", start);

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
						tabPane.addTab("Stats", stats);

						JPanel settings = new JPanel(new GridBagLayout());
						addSettings(settings);
						tabPane.addTab("Settings", settings);

						tabPane.setSelectedIndex(5);
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
		{ // Right
			JPanel right = new JPanel(new GridBagLayout());
			addOutputContainer(right);
			addOutputButtons(right);
			outside.setRightComponent(right);
		}

		add(outside);
		outside.setDividerLocation(395);
	}

	private void addStart(JPanel start)
	{
		historyList = new JList();
//		historyList.addPropertyChangeListener(new PropertyChangeListener()
//		{
//			@Override
//			public void propertyChange(PropertyChangeEvent arg0)
//			{
//				historyList.setFixedCellWidth(start.getWidth());
//			
//			}
//		});
		start.add(historyList);
		historyList.addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				displayBuild((EcBuildOrder) historyList.getSelectedValue());
			}
		});
		final PopupMenu deleteMenu = new PopupMenu("Options");
		MenuItem menuItem = new MenuItem("Delete");
		menuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ec.seeds.remove(historyList.getSelectedValue());
				refreshHistory();
				ec.saveSeeds();
			}
		});
		deleteMenu.add(menuItem);
		menuItem = new MenuItem("Load");
		menuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				expandWaypoints((EcState) historyList.getSelectedValue());
				readDestinations();
			}
		});
		deleteMenu.insert(menuItem, 0);
		historyList.add(deleteMenu);
		historyList.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent me)
			{
				// if right mouse button clicked (or me.isPopupTrigger())
				if (SwingUtilities.isRightMouseButton(me) && !historyList.isSelectionEmpty()
						&& historyList.locationToIndex(me.getPoint()) == historyList.getSelectedIndex())
				{
					deleteMenu.show(historyList, me.getX(), me.getY());
				}
			}
		});
		ec.loadSeeds();
		refreshHistory();
	}

	private void displayBuild(EcBuildOrder destination)
	{
		if (destination == null)
			return;
		EcBuildOrder source = new EcBuildOrder();
		EcBuildOrder source2 = new EcBuildOrder();
		EcBuildOrder source3 = new EcBuildOrder();
		EcEvolver evolver;
		try
		{
			evolver = new EcEvolver(source, destination.clone());
			ByteArrayOutputStream baos;
			evolver.log = new PrintStream(baos = new ByteArrayOutputStream());
			evolver.debug = true;
			for (EcAction a : destination.actions)
			{
				source.addAction(a.getClass().newInstance());
				source2.addAction(a.getClass().newInstance());
				source3.addAction(a.getClass().newInstance());
			}
			source.targetSeconds = destination.targetSeconds;
			source2.targetSeconds = destination.targetSeconds;
			source3.targetSeconds = destination.targetSeconds;
			EcBuildOrder result = evolver.doEvaluate(source);
			String detailedText = new String(baos.toByteArray());
			String simpleText = evolver.doSimpleEvaluate(source2);
			String yabotText = evolver.doYABOTEvaluate(source3);
			receiveBuildOrders(detailedText, simpleText, yabotText);
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
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

	private void refreshHistory()
	{
		ArrayList<EcBuildOrder> results = new ArrayList<EcBuildOrder>();
		for (EcBuildOrder destination : ec.seeds)
		{
			EcBuildOrder source = new EcBuildOrder();
			EcEvolver evolver = new EcEvolver(source, destination);
			evolver.debug = true;
			for (EcAction a : destination.actions)
				source.addAction(a);
			source.targetSeconds = destination.targetSeconds;
			EcBuildOrder result = evolver.doEvaluate(source);
			if (result.seconds > 60)
				results.add(destination);
		}
		historyList.setListData(results.toArray());
	}

	private void addSettings(JPanel settings)
	{
		{
			// somebody enlighten me please how this could be done easier... but
			// it works :)
			final String[] radioButtonCaptions = { "None", "Until saturation", "Allow overdroning" };
			final int defaultSelected;
			if (destination[destination.length - 1].settings.overDrone)
			{
				defaultSelected = 1;
			}
			else if (destination[destination.length - 1].settings.workerParity)
			{
				defaultSelected = 2;
			}
			else
			{
				defaultSelected = 0;
			}
			addRadioButtonBox(settings, "Enforce Worker Parity", radioButtonCaptions, defaultSelected,
					new CustomActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							if (getSelected(e).equals(radioButtonCaptions[1]))
							{
								destination[destination.length - 1].settings.workerParity = true;
								destination[destination.length - 1].settings.overDrone = false;
							}
							else if (getSelected(e).equals(radioButtonCaptions[2]))
							{
								destination[destination.length - 1].settings.workerParity = false;
								destination[destination.length - 1].settings.overDrone = true;
							}
							else
							{
								destination[destination.length - 1].settings.workerParity = false;
								destination[destination.length - 1].settings.overDrone = false;
							}
						}

						@Override
						void reverse(Object o)
						{
							//TODO: Code this up
						}
					});
			gridy++;
		}
		addCheck(settings, "Use Extractor Trick", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[destination.length - 1].settings.useExtractorTrick = getTrue(e);
			}

			@Override
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[destination.length - 1].settings.useExtractorTrick);
			}
		}).setSelected(destination[destination.length - 1].settings.useExtractorTrick);
		gridy++;
		addCheck(settings, "Pull/Push workers from/to gas", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[destination.length - 1].settings.pullWorkersFromGas = getTrue(e);
			}
			@Override
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[destination.length - 1].settings.pullWorkersFromGas);
			}
		}).setSelected(destination[destination.length - 1].settings.useExtractorTrick);
		gridy++;
		addCheck(settings, "Always pull/push 3 workers together", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[destination.length - 1].settings.pullThreeWorkersOnly = getTrue(e);
			}
			@Override
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[destination.length - 1].settings.pullThreeWorkersOnly);
			}
		}).setSelected(destination[destination.length - 1].settings.pullThreeWorkersOnly);
		gridy++;
		addInput(settings, "Minimum Pool Supply", new CustomActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				destination[destination.length - 1].settings.minimumPoolSupply = getDigit(e);
			}
			@Override
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[destination.length - 1].settings.minimumPoolSupply));
			}
		}).setText("2");
		gridy++;
		addInput(settings, "Minimum Extractor Supply", new CustomActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				destination[destination.length - 1].settings.minimumExtractorSupply = getDigit(e);
			}
			@Override
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[destination.length - 1].settings.minimumExtractorSupply));
			}
		}).setText("2");
		gridy++;
		addInput(settings, "Minimum Hatchery Supply", new CustomActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				destination[destination.length - 1].settings.minimumHatcherySupply = getDigit(e);

			}
			@Override
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[destination.length - 1].settings.minimumHatcherySupply));
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
						stats.append("\nStagnation Limit: " + ec.stagnationLimit);
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
		gridBagConstraints.gridwidth = 4;
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
		sb
				.append("\n\nPlease report any issues or new features you would like at: \nhttp://code.google.com/p/evolutionchamber/issues/list");
		sb.append("\n\nHow to use:");
		sb.append("\nEnter in what you would like to see as your end state. Hit Go. Be patient.");
		sb.append("\nThe build order will compute, and it can take several minutes to potentially hours.");
		sb.append("\nAll build orders are saved and may be viewed later.");
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
		sb.append("\nmulander (Auto-updater)");
		sb.append("\nUtena (Genetics)");
		sb.append("\nBumblebees (Features)");
		sb.append("\nQwerty10010 (Docs)");
		sb.append("\nAbydos1 (Terran/Protoss)");
		sb.append("\nInfinity0 (Terran/Protoss)");
		sb.append("\nAbout 10,493 other people, who's names I need to compile. (Testing)");
		sb.append("\n\nSpecial thanks to Incinerate. Those who know why, know why. ;-)");
		simpleBuildOrder = sb.toString();
		detailedBuildOrder = sb.toString();
		outputText.setText(sb.toString());
	}

	private void addInputContainer(final int i, final JPanel components)
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
		addInput(components, "Drones", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].drones = getDigit(e);
			}

			@Override
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].drones));
			}
		});
		addInput(components, "Deadline", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].targetSeconds = getDigit(e);
				((JTextField)e.getSource()).setText(
						Integer.toString(destination[i].targetSeconds / 60) + ":"
								+ Integer.toString(destination[i].targetSeconds % 60));
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].targetSeconds));
			}
		}).setText(
				Integer.toString(destination[i].targetSeconds / 60) + ":"
						+ Integer.toString(destination[i].targetSeconds % 60));
		gridy++;
		addInput(components, "Overlords", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].overlords = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].overlords));
			}
		});
		addInput(components, "Overseers", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].overseers = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].overseers));
			}
		});
		gridy++;
		if (i == 4) // only put this option on the Final waypoint.
		{
			addInput(components, "Scout Timing", new CustomActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					destination[destination.length-1].scoutDrone = getDigit(e);
				}
				void reverse(Object o)
				{
					JTextField c = (JTextField) o;
					c.setText(Integer.toString(destination[destination.length-1].scoutDrone));
				}
			});
		}
		addCheck(components, "Burrow", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].burrow = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].burrow);
			}
		});
		gridy++;
		addInput(components, "Queens", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].queens = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].queens));
			}
		});
		addCheck(components, "Pneumatized Carapace", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].pneumatizedCarapace = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].pneumatizedCarapace);
			}
		});
		gridy++;
		addInput(components, "Zerglings", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].zerglings = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].zerglings));
			}
		});
		addCheck(components, "Ventral Sacs", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].ventralSacs = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].ventralSacs);
			}
		});
		gridy++;
		addCheck(components, "Metabolic Boost", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].metabolicBoost = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].metabolicBoost);
			}
		});
		addCheck(components, "Adrenal Glands", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].adrenalGlands = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].adrenalGlands);
			}
		});
		gridy++;
		addInput(components, "Banelings", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].banelings = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].banelings));
			}
		});
		addCheck(components, "Centrifugal Hooks", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].centrifugalHooks = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].centrifugalHooks);
			}
		});
		gridy++;
		addInput(components, "Roaches", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].roaches = getDigit(e);

			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].roaches));
			}
		});
		gridy++;
		addCheck(components, "Glial Reconstitution", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].glialReconstitution = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].glialReconstitution);
			}
		});
		addCheck(components, "Tunneling Claws", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].tunnelingClaws = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].tunnelingClaws);
			}
		});
		gridy++;
		addInput(components, "Hydralisks", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].hydralisks = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].hydralisks));
			}
		});
		addCheck(components, "Grooved Spines", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].groovedSpines = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].groovedSpines);
			}
		});
		gridy++;
		addInput(components, "Infestors", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].infestors = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].infestors));
			}
		});
		gridy++;
		addCheck(components, "Neural Parasite", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].neuralParasite = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].neuralParasite);
			}
		});
		addCheck(components, "Pathogen Glands", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].pathogenGlands = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].pathogenGlands);
			}
		});
		gridy++;
		addInput(components, "Mutalisks", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].mutalisks = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].mutalisks));
			}
		});
		gridy++;
		addInput(components, "Ultralisks", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].ultralisks = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].ultralisks));
			}
		});
		addCheck(components, "Chitinous Plating", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].chitinousPlating = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].chitinousPlating);
			}
		});
		gridy++;
		addInput(components, "Corruptors", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].corruptors = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].corruptors));
			}
		});
		addInput(components, "Broodlords", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].broodlords = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].broodlords));
			}
		});
		gridy++;
		addCheck(components, "Melee +1", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].melee1 = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].melee1);
			}
		});
		addCheck(components, "+2", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].melee2 = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].melee2);
			}
		});
		addCheck(components, "+3", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].melee3 = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].melee3);
			}
		});
		gridy++;
		addCheck(components, "Missile +1", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].missile1 = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].missile1);
			}
		});
		addCheck(components, "+2", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].missile2 = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].missile2);
			}
		});
		addCheck(components, "+3", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].missile3 = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].missile3);
			}
		});
		gridy++;
		addCheck(components, "Carapace +1", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].armor1 = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].armor1);
			}
		});
		addCheck(components, "+2", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].armor2 = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].armor2);
			}
		});
		addCheck(components, "+3", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].armor3 = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].armor3);
			}
		});
		gridy++;
		addCheck(components, "Flyer Attack +1", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].flyerAttack1 = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].flyerAttack1);
			}
		});
		addCheck(components, "+2", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].flyerAttack2 = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].flyerAttack2);
			}
		});
		addCheck(components, "+3", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].flyerAttack3 = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].flyerAttack3);
			}
		});
		gridy++;
		addCheck(components, "Flyer Armor +1", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].flyerArmor1 = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].flyerArmor1);
			}
		});
		addCheck(components, "+2", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].flyerArmor2 = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].flyerArmor2);
			}
		});
		addCheck(components, "+3", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].flyerArmor3 = getTrue(e);
			}
			void reverse(Object o)
			{
				JCheckBox c = (JCheckBox) o;
				c.setSelected(destination[i].flyerArmor3);
			}
		});
		gridy++;
		addInput(components, "Bases", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].requiredBases = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].requiredBases));
			}
		});
		addInput(components, "Lairs", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].lairs = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].lairs));
			}
		});
		gridy++;
		addInput(components, "Hives", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].hives = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].hives));
			}
		});
		addInput(components, "Gas Extractors", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].gasExtractors = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].gasExtractors));
			}
		});
		gridy++;
		addInput(components, "Evo Chambers", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].evolutionChambers = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].evolutionChambers));
			}
		});
		gridy++;
		addInput(components, "Spine Crawlers", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].spineCrawlers = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].spineCrawlers));
			}
		});
		addInput(components, "Spore Crawlers", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].sporeCrawlers = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].sporeCrawlers));
			}
		});
		gridy++;
		addInput(components, "Spawning Pools", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].spawningPools = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].spawningPools));
			}
		});
		addInput(components, "Baneling Nests", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].banelingNest = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].banelingNest));
			}
		});
		gridy++;
		addInput(components, "Roach Warrens", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].roachWarrens = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].roachWarrens));
			}
		});
		addInput(components, "Hydralisk Dens", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].hydraliskDen = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].hydraliskDen));
			}
		});
		gridy++;
		addInput(components, "Infestation Pits", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].infestationPit = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].infestationPit));
			}
		});
		addInput(components, "Spires", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].spire = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].spire));
			}
		});
		gridy++;
		addInput(components, "Nydus Networks", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].nydusNetwork = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].nydusNetwork));
			}
		});
		addInput(components, "Nydus Worms", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].nydusWorm = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].nydusWorm));
			}
		});
		gridy++;
		addInput(components, "Ultralisk Caverns", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].ultraliskCavern = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].ultraliskCavern));
			}
		});
		addInput(components, "Greater Spires", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination[i].greaterSpire = getDigit(e);
			}
			void reverse(Object o)
			{
				JTextField c = (JTextField) o;
				c.setText(Integer.toString(destination[i].greaterSpire));
			}
		});
		gridy++;
		inputControls.add(addButton(components, "Reset all fields", 4, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				for (int i = 0; i < components.getComponentCount(); i++)
				{
					Component component = components.getComponent(i);
					if (component instanceof JTextField)
					{
						JTextField textField = (JTextField) component;
						if (textField.getText().indexOf(":") == -1) // is
						{
							// not
							// a
							// "Deadline"
							// field
							textField.setText("0");
							textField.getActionListeners()[0].actionPerformed(new ActionEvent(textField, 0, ""));
						}
					}
					else if (component instanceof JCheckBox)
					{
						JCheckBox checkBox = (JCheckBox) component;
						checkBox.setSelected(false);
						checkBox.getActionListeners()[0].actionPerformed(new ActionEvent(checkBox, 0, ""));
					}
				}
			}
		}));
	}

	private void readDestinations()
	{
		for (int i = 0; i < inputControls.size(); i++)
		{
			JComponent component = inputControls.get(i);
			if (component instanceof JTextField)
			{
				ActionListener actionListener = ((JTextField) component).getActionListeners()[0];
				if (actionListener instanceof CustomActionListener)
					((CustomActionListener)actionListener).reverse(component);
			}
			else if (component instanceof JCheckBox)
			{
				ActionListener actionListener = ((JCheckBox) component).getActionListeners()[0];
				if (actionListener instanceof CustomActionListener)
					((CustomActionListener)actionListener).reverse(component);
			}
		}
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

		switchDetailedButton = new JButton("Detailed format");
		isDetailedBuildOrder = true;
		gridBagConstraints.weightx = 0.25;
		component.add(switchDetailedButton, gridBagConstraints);
		switchDetailedButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				outputText.setText(detailedBuildOrder);
				outputText.setTabSize(4);
				isDetailedBuildOrder = true;
				isYabotBuildOrder = false;
				isSimpleBuildOrder = false;
			}
		});

		switchSimpleButton = new JButton("Simple format");
		isSimpleBuildOrder = false;
		gridBagConstraints.weightx = 0.25;
		component.add(switchSimpleButton, gridBagConstraints);
		switchSimpleButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				outputText.setText(simpleBuildOrder);
				outputText.setTabSize(14);
				isSimpleBuildOrder = true;
				isYabotBuildOrder = false;
				isDetailedBuildOrder = false;
			}
		});

		switchYabotButton = new JButton("YABOT format");
		isYabotBuildOrder = false;
		gridBagConstraints.weightx = 0.25;
		component.add(switchYabotButton, gridBagConstraints);
		switchYabotButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				outputText.setText(yabotBuildOrder);
				outputText.setTabSize(14);
				isYabotBuildOrder = true;
				isSimpleBuildOrder = false;
				isDetailedBuildOrder = false;
			}
		});
	}

	private void addControlParts(JPanel component)
	{
		addInput(component, "Processors", new CustomActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ec.setThreads(getDigit(e));
				((JTextField) e.getSource()).setText(Integer.toString(ec.getThreads()));
			}
			void reverse(Object o)
			{
				((JTextField) o).setText(Integer.toString(ec.getThreads()));
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
				historyList.setEnabled(true);
				timeStarted = 0;
				for (JComponent j : inputControls)
					j.setEnabled(true);
				lastUpdate = 0;
				refreshHistory();
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
				tabPane.setSelectedIndex(6);
				timeStarted = new Date().getTime();
				goButton.setEnabled(false);
				stopButton.setEnabled(true);
				historyList.setEnabled(false);

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

	private JLabel addLabel(JPanel container, String string)
	{
		final JLabel label = new JLabel();

		GridBagConstraints gridBagConstraints;
		label.setText(string);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 0.5;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		container.add(label, gridBagConstraints);

		return label;
	}

	private JButton addButton(JPanel container, String string, int gridwidth, ActionListener actionListener)
	{
		final JButton button = new JButton();

		button.setText(string);
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = .25;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridwidth = gridwidth;
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
			EcState finalDestination = collapseWaypoints();
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

	private EcState collapseWaypoints() throws CloneNotSupportedException
	{
		EcState finalDestination = (EcState) destination[destination.length - 1].clone();
		for (int i = 0; i < destination.length - 1; i++)
		{
			if (destination[i].getSumStuff() > 1)
				finalDestination.waypoints.add((EcState) destination[i].clone());

		}
		return finalDestination;
	}

	private void expandWaypoints(EcState s)
	{
		try
		{
			destination[destination.length - 1] = (EcState) s.clone();
			for (int i = 0; i < s.waypoints.size(); i++)
			{
				destination[i] = (EcState) s.waypoints.get(i).clone();
			}
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
	}

	private JPanel addRadioButtonBox(JPanel container, String title, String[] captions, int defaultSelected,
			final CustomActionListener a)
	{
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);

		JRadioButton[] buttons = new JRadioButton[captions.length];
		ButtonGroup group = new ButtonGroup();
		JPanel radioButtonBox = new JPanel();
		radioButtonBox.setBorder(BorderFactory.createTitledBorder(title));

		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i] = new JRadioButton(captions[i]);
			buttons[i].addActionListener(a);
			inputControls.add(buttons[i]);
			group.add(buttons[i]);
			if (i == defaultSelected)
				buttons[i].setSelected(true);
			radioButtonBox.add(buttons[i]);
		}
		container.add(radioButtonBox, gridBagConstraints);
		return radioButtonBox;
	}

	protected String getSelected(ActionEvent e)
	{
		JRadioButton radioButton = (JRadioButton) e.getSource();
		return radioButton.getText();
	}

	protected boolean getTrue(ActionEvent e)
	{
		JCheckBox tf = (JCheckBox) e.getSource();
		this.ec.bestScore = new Double(0);
		return tf.isSelected();
	}

	private JTextField addInput(JPanel container, String name, final CustomActionListener a)
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
		textField.addActionListener(a);
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

	private JCheckBox addCheck(JPanel container, String name, final CustomActionListener a)
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
		checkBox.addActionListener(a);
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
	public void bestScore(final EcState finalState, int intValue, final String detailedText, final String simpleText,
			final String yabotText)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				receiveBuildOrders(detailedText, simpleText, yabotText);
				lastUpdate = new Date().getTime();
			}

		});
	}

	private void receiveBuildOrders(final String detailedText, final String simpleText, final String yabotText)
	{
		simpleBuildOrder = simpleText;
		detailedBuildOrder = detailedText;
		yabotBuildOrder = yabotText;
		if (isSimpleBuildOrder)
		{
			outputText.setText(simpleText);
		}
		else if (isYabotBuildOrder)
		{
			outputText.setText(yabotBuildOrder);
		}
		else
		{
			outputText.setText(detailedText);
		}
	}

	@Override
	public void threadScore(int threadIndex, String output)
	{
		// TODO Auto-generated method stub

	}

}
