package com.polytech.pong.component;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.StringJoiner;

/**
 * Paddle implementation
 * 
 * @author Clément
 *
 */
@SuppressWarnings("serial")
public class Paddle implements Serializable {

	public static final int PADDLE_WIDTH = 20;
	public static final int PADDLE_HEIGHT = 120;
	public static final int NO_COLISION_DETECTED = 9999;

	private static final double PADDLE_TOP_LIMITER = (PADDLE_HEIGHT / 5) * 2;
	private static final double PADDLE_BOTTOM_LIMITER = (PADDLE_HEIGHT / 5) * 3;
	private static final double PADDLE_BOTTOM_WIDTH = PADDLE_HEIGHT - PADDLE_BOTTOM_LIMITER;
	private static final double PADDLE_TOP_WIDTH = PADDLE_TOP_LIMITER;

	public static enum EColision {
		TOP, MIDDLE, BOTTOM
	};

	private Point position; // center of the paddle

	/**
	 * Create object
	 * 
	 * @param position
	 *            initial paddle position. Note : the position point is the
	 *            paddle center.
	 */
	public Paddle(Point position) {
		this.position = position;
	}

	/**
	 * Compute if an object collide with the paddle
	 * 
	 * @param objectRec
	 * @return -1 to -100 for bottom, 0 for center and 1 to 100 for top
	 */
	public int colisionDetected(Rectangle objectRec) {
		Rectangle paddleRec = getRectangleRepresentation();

		// Collision
		if (paddleRec.x < objectRec.x + objectRec.width && paddleRec.x + paddleRec.width > objectRec.x
				&& paddleRec.y < objectRec.y + objectRec.height && paddleRec.height + paddleRec.y > objectRec.y) {

			double objectCenter = objectRec.y + (objectRec.width / 2);
			int colisionPosition;

			if (objectCenter < paddleRec.y + (PADDLE_TOP_LIMITER)) {

				// Case collide but object center is above paddle top
				if (objectCenter < paddleRec.y) {
					colisionPosition = (int) ((((paddleRec.y + PADDLE_TOP_LIMITER)
							- (objectCenter + objectRec.height / 2)) / (PADDLE_TOP_WIDTH)) * 100);
				} else {
					colisionPosition = (int) ((((paddleRec.y + PADDLE_TOP_LIMITER) - (objectCenter))
							/ (PADDLE_TOP_WIDTH)) * 100);
				}

				return colisionPosition;
			} else if (objectCenter > paddleRec.y + PADDLE_BOTTOM_LIMITER) {

				// Case collide but object center is under paddle bottom
				if (objectCenter > paddleRec.y + PADDLE_HEIGHT) {
					colisionPosition = (int) ((((objectCenter - objectRec.height / 2)
							- (paddleRec.y + PADDLE_BOTTOM_LIMITER)) / (PADDLE_BOTTOM_WIDTH)) * 100);
				} else {
					colisionPosition = (int) ((((objectCenter) - (paddleRec.y + PADDLE_BOTTOM_LIMITER))
							/ (PADDLE_BOTTOM_WIDTH)) * 100);
				}

				return -colisionPosition;
			} else {
				return 0;
			}

		}

		return NO_COLISION_DETECTED;
	}

	/**
	 * Swing Rectangle representation of the paddle
	 * 
	 * @return Rectangle
	 */
	public Rectangle getRectangleRepresentation() {
		return new Rectangle(getTopLeftPoint(), new Dimension(PADDLE_WIDTH, PADDLE_HEIGHT));
	}

	public Point getPosition() {
		return position;
	}

	public void setY(int y) {
		position.y = y;
	}

	/**
	 * Compute the paddle top left point according to its size. Used to create
	 * the swing Rectangle representation.
	 * 
	 * @return Paddle top left point
	 */
	private Point getTopLeftPoint() {
		return new Point(position.x - PADDLE_WIDTH / 2, position.y - PADDLE_HEIGHT / 2);
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner("-");
		joiner.add("" + position.x).add("" + position.y);
		return joiner.toString();
	}

}
