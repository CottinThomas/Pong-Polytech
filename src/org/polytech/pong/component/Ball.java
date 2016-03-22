package org.polytech.pong.component;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class Ball {

	public static final int BALLE_SIZE = 15;
	private static final int MAX_STEP = 5;
	
	public static enum EAngle {EAST, CENTER, WEST};
	
	private Point position; // ball center;
	private int direction; 
	private EAngle angle;
	private int speed;
	private int step;
	
	public Ball(Point position)
	{
		this.position = position;
		direction = -1;
		angle = EAngle.CENTER;
		speed = MAX_STEP;
		step = 0;
	}
	
	public void move()
	{
		step++;
		if(step >= speed)
		{
			
			step = 0;
		}
	}
	
	public Rectangle getRectangleRepresentation()
	{
		return new Rectangle(getTopLeftPoint(), new Dimension(BALLE_SIZE, BALLE_SIZE));
	}
	
	public void setDirection(int direction)
	{
		if(direction >= 0)
		{
			this.direction = 1;
		}
		else
		{
			this.direction = -1;
		}
	}
	
	public void increaseSpeed()
	{
		if(speed - 1 > 0)
		{
			speed--;
		}
	}
	
	public void setAngle(EAngle angle)
	{
		this.angle = angle;
	}
	
	
	private Point getTopLeftPoint()
	{
		return new Point(position.x - BALLE_SIZE/2, position.y - BALLE_SIZE/2);
	}
}
