package com.fray.evo.ui.swingx;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
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

import com.fray.evo.EcReportable;
import com.fray.evo.EcState;
import com.fray.evo.EvolutionChamber;

public class EcSwingX extends JXPanel implements EcReportable
{
	private JTextArea			outputText;
	private JLabel				status2;
	private JLabel				status1;
	protected long				timeStarted;
	protected long				lastUpdate;
	int							gridy		= 0;
	private JXStatusBar			statusbar;
	private List<JComponent>	textBoxes	= new ArrayList<JComponent>();

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
				JFrame frame = new JFrame();
				frame.setTitle("Evolution Chamber");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(new EcSwingX());
				frame.setPreferredSize(new Dimension(800, 800));
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

	public EcSwingX()
	{
		try
		{
			destination = (EcState) ec.getInternalDestination().clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}

		setLayout(new BorderLayout());

		JSplitPane outside = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		JPanel leftbottom = new JPanel(new GridBagLayout());
		JScrollPane stuffPanel = new JScrollPane(leftbottom);
		outside.setLeftComponent(stuffPanel);
		JPanel right = new JPanel(new FlowLayout());
		outside.setRightComponent(new JScrollPane(right));

		addInputContainer(leftbottom);
		addStatusBar(leftbottom);
		addOutputContainer(right);

		add(outside);
		outside.setDividerLocation(340);
	}

	private void addStatusBar(JPanel leftbottom)
	{
		statusbar = new JXStatusBar();
		status1 = new JLabel("Ready.");
		status2 = new JLabel("Not Running.");
		statusbar.add(status1);
		statusbar.add(status2);

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.SOUTH;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = .5;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.gridy = gridy + 1;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		leftbottom.add(statusbar, gridBagConstraints);
		Timer t = new Timer(1000, new ActionListener()
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
				if (lastUpdate == 0)
					;
				else
				{
					long ms = new Date().getTime() - lastUpdate;
					long seconds = ms / 1000;
					long minutes = seconds / 60;
					long hours = minutes / 60;
					status2.setText("Last update: " + hours % 60 + ":" + minutes % 60 + ":" + seconds % 60 + " ago");
				}
				statusbar.invalidate();
			}
		});
		t.start();
	}

	private void addOutputContainer(JPanel component)
	{
		component.add(outputText = new JTextArea());
		outputText.setAlignmentX(0);
		outputText.setAlignmentY(0);
		StringBuilder sb = new StringBuilder();
		sb.append("Hello! Welcome to the Evolution Chamber.");
		sb.append("\nTo start, enter in some units you would like to have.");
		sb.append("\nWhen you have decided what you would like, hit Start.");
		sb.append("\n\nPlease report any issues at \nhttp://code.google.com/p/evolutionchamber/issues/list");
		sb.append("\n\nFixed in this release (0005):");
		sb.append("\nFields in editable state after starting the calculation");
		sb.append("\nExtremely slow mouse scroll on the results text area.");
		sb.append("\nApplication window has no title");
		sb.append("\nLast updated field keeps running after pressing stop");
		sb.append("\nMultiple upgrades can be researched at the same time from one building");
		sb.append("\nFileNotFoundException etc\\seeds.evo raised from EvolutionChamber.java:222");
		sb.append("\nBuildExtractor executed twice in a b/o targeting mineral only units.");
		sb.append("\n\nAdded in this release (0005):");
		sb.append("\nPerformance improvement (may cause memory issues)");
		sb.append("\nSpore Crawlers");
		outputText.setText(sb.toString());
	}

	EvolutionChamber	ec	= new EvolutionChamber();
	EcState				destination;
	private JButton		goButton;
	private JButton		stopButton;

	private void addInputContainer(JPanel component)
	{
		addInput(component, "Processors", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ec.setThreads(getDigit(e));
			}
		}).setText("4");
		gridy++;
		stopButton = addButton(component, "Stop", new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				ec.stop();
				goButton.setEnabled(true);
				stopButton.setEnabled(false);
				timeStarted = 0;
				for (JComponent j : textBoxes)
					j.setEnabled(true);
				lastUpdate = 0;
			}
		});
		stopButton.setEnabled(false);
		goButton = addButton(component, "Start", new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				ec.onNewBuild = new ActionListener()
				{
					@Override
					public void actionPerformed(final ActionEvent e)
					{
						SwingUtilities.invokeLater(new Runnable()
						{
							@Override
							public void run()
							{
								EcState destination = (EcState) e.getSource();
								outputText.setText(e.getActionCommand());
								lastUpdate = new Date().getTime();
							}
						});
					}
				};
				for (JComponent j : textBoxes)
					j.setEnabled(false);
				restartChamber();
				timeStarted = new Date().getTime();
				goButton.setEnabled(false);
				stopButton.setEnabled(true);
			}
		});
		gridy++;
		// addInput(component, "Target number of seconds", new ActionListener()
		// {
		// public void actionPerformed(ActionEvent e)
		// {
		// destination.targetSeconds = getDigit(e);
		// }
		// }).setText("600");
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
				destination.drones = getDigit(e);
			}
		}).setText("6");
		addCheck(component, "Burrow", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.burrow = getTrue(e);
			}
		});
		gridy++;
		addInput(component, "Overlords", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.overlords = getDigit(e);
			}
		}).setText("1");
		addInput(component, "Overseers", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.overseers = getDigit(e);
			}
		});
		gridy++;
		addCheck(component, "Pneumatized Carapace", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.pneumatizedCarapace = getTrue(e);
			}
		});
		addCheck(component, "Ventral Sacs", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.ventralSacs = getTrue(e);
			}
		});
		gridy++;
		addInput(component, "Queens", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.queens = getDigit(e);
			}
		});
		gridy++;
		addInput(component, "Zerglings", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.zerglings = getDigit(e);
			}
		});
		gridy++;
		addCheck(component, "Metabolic Boost", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.metabolicBoost = getTrue(e);
			}
		});
		addCheck(component, "Adrenal Glands", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.adrenalGlands = getTrue(e);
			}
		});
		gridy++;
		addInput(component, "Banelings", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.banelings = getDigit(e);
			}
		});
		addCheck(component, "Centrifugal Hooks", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.centrifugalHooks = getTrue(e);
			}
		});
		gridy++;
		addInput(component, "Roaches", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.roaches = getDigit(e);

			}
		});
		gridy++;
		addCheck(component, "Glial Reconstitution", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.glialReconstitution = getTrue(e);
			}
		});
		addCheck(component, "Tunneling Claws", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.tunnelingClaws = getTrue(e);
			}
		});
		gridy++;
		addInput(component, "Hydralisks", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.hydralisks = getDigit(e);
			}
		});
		addCheck(component, "Grooved Spines", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.groovedSpines = getTrue(e);
			}
		});
		gridy++;
		addInput(component, "Infestors", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.infestors = getDigit(e);
			}
		});
		gridy++;
		addCheck(component, "Neural Parasite", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.neuralParasite = getTrue(e);
			}
		});
		addCheck(component, "Pathogen Glands", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.pathogenGlands = getTrue(e);
			}
		});
		gridy++;
		addInput(component, "Mutalisks", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.mutalisks = getDigit(e);
			}
		});
		gridy++;
		addInput(component, "Ultralisks", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.ultralisks = getDigit(e);
			}
		});
		addCheck(component, "Chitinous Plating", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.chitinousPlating = getTrue(e);
			}
		});
		gridy++;
		addInput(component, "Corruptors", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.corruptors = getDigit(e);
			}
		});
		addInput(component, "Broodlords", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.broodlords = getDigit(e);
			}
		});
		gridy++;
		addCheck(component, "Melee +1", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.melee1 = getTrue(e);
			}
		});
		addCheck(component, "+2", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.melee2 = getTrue(e);
			}
		});
		addCheck(component, "+3", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.melee3 = getTrue(e);
			}
		});
		gridy++;
		addCheck(component, "Missile +1", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.missile1 = getTrue(e);
			}
		});
		addCheck(component, "+2", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.missile2 = getTrue(e);
			}
		});
		addCheck(component, "+3", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.missile3 = getTrue(e);
			}
		});
		gridy++;
		addCheck(component, "Carapace +1", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.armor1 = getTrue(e);
			}
		});
		addCheck(component, "+2", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.armor2 = getTrue(e);
			}
		});
		addCheck(component, "+3", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.armor3 = getTrue(e);
			}
		});
		gridy++;
		addCheck(component, "Flyer Attack +1", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.flyerAttack1 = getTrue(e);
			}
		});
		addCheck(component, "+2", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.flyerAttack2 = getTrue(e);
			}
		});
		addCheck(component, "+3", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.flyerAttack3 = getTrue(e);
			}
		});
		gridy++;
		addCheck(component, "Flyer Armor +1", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.flyerArmor1 = getTrue(e);
			}
		});
		addCheck(component, "+2", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.flyerArmor2 = getTrue(e);
			}
		});
		addCheck(component, "+3", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.flyerArmor3 = getTrue(e);
			}
		});
		gridy++;
		addInput(component, "Hatcheries", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.hatcheries = getDigit(e);
			}
		});
		addInput(component, "Lairs", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.lairs = getDigit(e);
			}
		});
		gridy++;
		addInput(component, "Hives", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.hives = getDigit(e);
			}
		});
		addInput(component, "Gas Extractors", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.gasExtractors = getDigit(e);
			}
		});
		gridy++;
		addInput(component, "Evolution Chambers", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.evolutionChambers = getDigit(e);
			}
		});
		gridy++;
		addInput(component, "Spine Crawlers", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.spineCrawlers = getDigit(e);
			}
		});
		addInput(component, "Spore Crawlers", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.sporeCrawlers = getDigit(e);
			}
		});
		gridy++;
		addInput(component, "Spawning Pools", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.spawningPools = getDigit(e);
			}
		});
		addInput(component, "Baneling Nests", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.banelingNest = getDigit(e);
			}
		});
		gridy++;
		addInput(component, "Roach Warrens", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.roachWarrens = getDigit(e);
			}
		});
		addInput(component, "Hydralisk Dens", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.hydraliskDen = getDigit(e);
			}
		});
		gridy++;
		addInput(component, "Infestation Pits", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.infestationPit = getDigit(e);
			}
		});
		addInput(component, "Spires", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.spire = getDigit(e);
			}
		});
		gridy++;
		addInput(component, "Ultralisk Caverns", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.ultraliskCavern = getDigit(e);
			}
		});
		addInput(component, "Greater Spires", new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				destination.greaterSpire = getDigit(e);
			}
		});
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
		gridBagConstraints.gridwidth = 2;
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
			Integer i = Integer.parseInt(text);
			return i;
		}
		catch (NumberFormatException ex)
		{
			return 0;
		}
	}

	private void restartChamber()
	{
		if (ec.threads.size() > 0)
			ec.stop();
		try
		{
			ec.setDestination((EcState) destination.clone());
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
		textBoxes.add(textField);
		return textField;
	}

	private void addCheck(JPanel container, String name, final ActionListener a)
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
		textBoxes.add(checkBox);
	}

}
