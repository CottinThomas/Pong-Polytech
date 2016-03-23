package com.polytech.pong.component;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Paddle implementation
 * @author Clément
 *
 */
public class Paddle {

	public static final int PADDLE_WIDTH = 20; 
	public static final int PADDLE_HEIGHT = 100; 
	
	public static enum EColision {TOP, MIDDLE, BOTTOM};
	
	private Point position; // center of the paddle
	
	/**
	 * Create object
	 * @param position initial paddle position. Note : the position point is the paddle center.
	 */
	public Paddle(Point position)
	{
		this.position = position;
	}
	
	/**
	 * Compute if an object collide with the paddle
	 * @param objectRec 
	 * @return The collision position
	 */
	public EColision colisionDetected(Rectangle objectRec)
	{
		Rectangle paddleRec = getRectangleRepresentation();
		
		// Collision
		if (paddleRec.x < objectRec.x + objectRec.width &&
				   paddleRec.x + paddleRec.width > objectRec.x &&
				   paddleRec.y < objectRec.y + objectRec.height &&
				   paddleRec.height + paddleRec.y > objectRec.y) 
		{
			
			if(objectRec.y < paddleRec.y + PADDLE_HEIGHT/3)
			{
				return EColision.TOP;
			}
			else if(objectRec.y > paddleRec.y + (PADDLE_HEIGHT/3 * 2))
			{
				return EColision.BOTTOM;
			}
			else
			{
				return EColision.MIDDLE;
			}
		}
		
		return null;
	}
	
	/**
	 * Swing Rectangle representation of the paddle
	 * 
	 * @return Rectangle
	 */
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
	
	/**
	 * Compute the paddle top left point according to its size. Used to create the
	 * swing Rectangle representation.
	 * 
	 * @return Paddle top left point
	 */
	private Point getTopLeftPoint()
	{
		return new Point(position.x - PADDLE_WIDTH/2, position.y - PADDLE_HEIGHT/2);
	}



}
