package com.fray.evo.ui.swingx;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EcInputContainer extends JPanel
{
	public EcInputContainer()
	{
		this.setLayout(new GridBagLayout());
		addInput("Drone");
		addInput("Drone");
		addInput("Drone");
		addInput("Drone");this.setBorder(BorderFactory.createBevelBorder(3));
	}

	private void addInput(String name)
	{
		GridBagConstraints gridBagConstraints;

		JLabel strictTextFieldLabel = new JLabel();
		strictTextFieldLabel.setName(name);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(1, 1, 1, 1);
		add(strictTextFieldLabel, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
        JTextField nonStrictTextField = new JTextField(); 
        gridBagConstraints = new GridBagConstraints(); 
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER; 
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL; 
        gridBagConstraints.insets = new Insets(1, 1, 1, 1); 
        add(nonStrictTextField, gridBagConstraints); 
	}
}
