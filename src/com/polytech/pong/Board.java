package com.polytech.pong;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * A board represent a game screen (ex. launcher, menu, game, etc).
 * It's included in the application JFrame
 * @author Clément
 *
 */
@SuppressWarnings("serial")
public abstract class Board extends JPanel{

	protected Application application;
	
	public Board(Application application) {
		this.application = application;
		this.setPreferredSize(new Dimension(Application.GAME_CONTENT_WIDTH, Application.GAME_CONTENT_HEIGHT));
	}
	
	public void setBackground(Color color)
	{
		super.setBackground(color);
	}
}
