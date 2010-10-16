package com.fray.evo.ui.swingx;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXList;

public class EcOutputContainer extends JPanel
{
	public EcOutputContainer()
	{
		add(new JXList());
		this.setBorder(BorderFactory.createBevelBorder(3));
	}
}
