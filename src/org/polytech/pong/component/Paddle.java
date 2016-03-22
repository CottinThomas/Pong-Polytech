package org.polytech.pong.component;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class Paddle {

	public static final int PADDLE_WIDTH = 20; 
	public static final int PADDLE_HEIGHT = 100; 
	
	public static enum EColision {TOP, CENTER, BOTTOM};
	
	private Point position; // center of the paddle

	public Paddle(int x, int y) {
		this(new Point(x, y));
	}
	
	public Paddle(Point position)
	{
		this.position = position;
	}
	
	public boolean colisionDetected(Point objectCenter, Dimension objectDimension)
	{
		return false;
	}
	
	public Rectangle getRectangleRepresentation()
	{
		return new Rectangle(getTopLeftPoint(), new Dimension(PADDLE_WIDTH, PADDLE_HEIGHT));
	}
	
	public Point getPosition()
	{
		return position;
	}
	
	public void setY(int y)
	{
		position.y = y;
	}
	
	private Point getTopLeftPoint()
	{
		return new Point(position.x - PADDLE_WIDTH/2, position.y - PADDLE_HEIGHT/2);
	}



}
