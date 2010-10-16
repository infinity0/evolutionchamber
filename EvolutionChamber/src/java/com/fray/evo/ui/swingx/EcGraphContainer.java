package com.fray.evo.ui.swingx;

import javax.swing.JPanel;

import org.jdesktop.swingx.JXGraph;

public class EcGraphContainer extends JPanel
{
	private JXGraph	graph;

	public EcGraphContainer()
	{
		this.add(graph = new JXGraph());
		
	}
}
