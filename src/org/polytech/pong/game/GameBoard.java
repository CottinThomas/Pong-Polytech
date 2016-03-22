package org.polytech.pong.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.polytech.pong.Application;
import org.polytech.pong.Board;
import org.polytech.pong.component.Ball;
import org.polytech.pong.component.Paddle;

@SuppressWarnings("serial")
public class GameBoard extends Board {

	private Paddle playerPaddle;
	private Paddle opponentPaddle;
	private Ball ball;


	public GameBoard(Application application, boolean isHost) {
		super(application);
		setBackground(Color.BLACK);

		playerPaddle = new Paddle(40, Application.GAME_CONTENT_HEIGHT / 2);
		opponentPaddle = new Paddle(Application.GAME_CONTENT_WIDTH - 40, Application.GAME_CONTENT_HEIGHT / 2);
		ball = new Ball(new Point(Application.GAME_CONTENT_WIDTH/2, Application.GAME_CONTENT_HEIGHT/2));
		
		addPlayerPaddleMouseMoveListener();
		startAnimation();


	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Drawing paddle
		Color c = Color.WHITE;
		g.setColor(c);

		Rectangle playerRect = playerPaddle.getRectangleRepresentation();
		Rectangle opponentRect = opponentPaddle.getRectangleRepresentation();
		Rectangle ballRect = ball.getRectangleRepresentation();
		
		g.fillRect(playerRect.x, playerRect.y, playerRect.width, playerRect.height);
		g.fillRect(opponentRect.x, opponentRect.y, opponentRect.width, opponentRect.height);
		g.fillRect(ballRect.x, ballRect.y, ballRect.width, ballRect.height);
		
		Graphics2D g2d = (Graphics2D) g.create();
		float[] dash4 = {10f, 10f, 10f};
		BasicStroke bs4 = new BasicStroke(4, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND, 1.0f, dash4, 10f);
		g2d.setStroke(bs4);
        g2d.drawLine(Application.GAME_CONTENT_WIDTH/2, 0, Application.GAME_CONTENT_WIDTH/2, Application.GAME_CONTENT_HEIGHT);
        g2d.dispose();

	}

	public static void main(String[] args) {
		Application application = new Application();
		application.switchBoard(new GameBoard(application, true));
		application.setVisible(true);

		System.err.println(application.getSize());
		System.err.println(application.getCurrentBoard().getSize());

	}
	
	private void addPlayerPaddleMouseMoveListener()
	{
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e)
			{
				int mouseY = e.getY();
				if (mouseY < Paddle.PADDLE_HEIGHT / 2) {
					playerPaddle.setY(Paddle.PADDLE_HEIGHT / 2);
				} else if (mouseY > Application.GAME_CONTENT_HEIGHT - Paddle.PADDLE_HEIGHT / 2) {
					playerPaddle.setY(Application.GAME_CONTENT_HEIGHT - Paddle.PADDLE_HEIGHT / 2);
				} else {
					playerPaddle.setY(mouseY);
				}
			}

			@Override
			public void mouseDragged(MouseEvent e)
			{
			}
		});
	}
	
	private void startAnimation()
	{
		ActionListener animate = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				repaint();
			}
		};
		
		Timer timer = new Timer(50, animate);
		timer.start();
	}
}
