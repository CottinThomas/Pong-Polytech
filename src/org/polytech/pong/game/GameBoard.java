package org.polytech.pong.game;

import javax.swing.JLabel;

import org.polytech.pong.Application;
import org.polytech.pong.Board;

public class GameBoard extends Board {

	public GameBoard(Application application) {
		super(application);
		
		add(new JLabel("POO"));
	}
}
