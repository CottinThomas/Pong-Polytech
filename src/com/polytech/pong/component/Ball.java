package com.polytech.pong.component;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.StringJoiner;

/**
 * Ball implementation
 * 
 * @author Cl�ment
 *
 */
@SuppressWarnings("serial")
public class Ball implements Serializable{

	public static final int BALLE_SIZE = 15;
	private static final int MIN_SPEAD = 2;
	private static final int MAX_SPEAD = 7;

	public static enum EAngle {
		SOUTH, CENTER, NORTH
	};

	private Point position; // ball center;
	private int direction;
	private EAngle angle;
	private int speed;
	private double tranlateY;

	/**
	 * Create object
	 * 
	 * @param position
	 *            initial ball position. Note : the position point is the ball
	 *            center.
	 */
	public Ball(Point position) {
		this.position = position;
		direction = -1;
		angle = EAngle.CENTER;
		speed = MIN_SPEAD;

	}

	/**
	 * Compute the ball new position depending on ball direction and angle
	 */
	public void move() {

		position.x += direction * speed;
		switch (angle) {
		case CENTER:
			break;
		case NORTH:
			position.y -= tranlateY;
			break;
		case SOUTH:
			position.y += tranlateY;
			break;
		default:
			break;
		}

	}

	/**
	 * Swing Rectangle representation of the ball
	 * 
	 * @return Rectangle
	 */
	public Rectangle getRectangleRepresentation() {
		return new Rectangle(getTopLeftPoint(), new Dimension(BALLE_SIZE, BALLE_SIZE));
	}

	public void setPosition(Point newPosition) {
		position = newPosition;
	}

	public void setDirection(int direction) {
		if (direction >= 0) {
			this.direction = 1;
		} else {
			this.direction = -1;
		}

		// Generate new y translation used in the move() method
		tranlateY = Math.random() * (3 - 1) + 1;
	}

	public void setSpeed(int newSpeed) {
		if (newSpeed <= MIN_SPEAD) {
			speed = MIN_SPEAD;
		} else if (newSpeed > MAX_SPEAD) {
			speed = MAX_SPEAD;
		} else {
			speed = newSpeed;
		}
	}

	public void setAngle(EAngle angle) {
		this.angle = angle;
	}

	public void setAngle(EAngle angle, double tranlation) {
		this.angle = angle;
		this.tranlateY = tranlation;
	}

	public Point getPosition() {
		return position;
	}

	public int getDirection() {
		return direction;
	}

	public EAngle getAngle() {
		return angle;
	}

	/**
	 * Compute the ball top left point according to its size. Used to create the
	 * swing Rectangle representation.
	 * 
	 * @return Ball top left point
	 */
	private Point getTopLeftPoint() {
		return new Point(position.x - BALLE_SIZE / 2, position.y - BALLE_SIZE / 2);
	}
	
	@Override
	public String toString(){
		StringJoiner joiner = new StringJoiner("-");
		joiner.add(""+position.x).add(""+position.y).add(""+direction).add(angle.toString()).add(""+speed).add(""+tranlateY);
		return joiner.toString();
		
	}
}
