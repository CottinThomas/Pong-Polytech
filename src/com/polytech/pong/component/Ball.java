package com.polytech.pong.component;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Ball implementation
 * 
 * @author Clément
 *
 */
public class Ball {

	public static final int BALLE_SIZE = 15;
	private static final int MIN_SPEAD = 5;
	private static final int MAX_SPEAD = 10;

	public static enum EAngle {
		SOUTH, CENTER, NORTH
	};

	private Point position; // ball center;
	private int direction;
	private EAngle angle;
	private int speed;

	/**
	 * Create object
	 * 
	 * @param position initial ball position. Note : the position point is the ball center.
	 */
	public Ball(Point position) {
		this.position = position;
		direction = -1;
		angle = EAngle.CENTER;
		speed = 10;

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
			position.y -= speed;
			break;
		case SOUTH:
			position.y += speed;
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

	public EAngle reverseAngle() {

		switch (angle) {
		case CENTER:
			angle = EAngle.CENTER;
			break;
		case SOUTH:
			angle = EAngle.NORTH;
			break;
		case NORTH:
			angle = EAngle.SOUTH;
			break;
		default:
			break;
		}

		return angle;
	}

	public Point getPosition() {
		return position;
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
}
