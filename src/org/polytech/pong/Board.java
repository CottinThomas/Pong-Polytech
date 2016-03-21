package org.polytech.pong;

import javax.swing.JPanel;

public abstract class Board extends JPanel{

	protected Application application;
	
	public Board(Application application) {
		this.application = application;
	}
}
