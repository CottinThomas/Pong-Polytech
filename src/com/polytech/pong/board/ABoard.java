package com.polytech.pong.board;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.polytech.pong.Application;

/**
 * A board represent a game screen (ex. launcher, menu, game, etc).
 * It's included in the application JFrame
 * @author Clément
 *
 */
@SuppressWarnings("serial")
public abstract class ABoard extends JPanel{

	protected Application application;
	
	public ABoard(Application application) {
		this.application = application;
		this.setPreferredSize(new Dimension(Application.GAME_CONTENT_WIDTH, Application.GAME_CONTENT_HEIGHT));
	}
	
	public void setBackground(Color color)
	{
		super.setBackground(color);
	}
}
