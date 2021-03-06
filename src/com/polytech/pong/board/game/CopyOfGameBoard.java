package com.polytech.pong.board.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

import com.polytech.pong.Application;
import com.polytech.pong.board.ABoard;
import com.polytech.pong.component.Ball;
import com.polytech.pong.component.Ball.EAngle;
import com.polytech.pong.component.Paddle;

// TODO : Mirror ball position for the opponent ?
// TODO : Transmit server received information;

@SuppressWarnings("serial")
public class CopyOfGameBoard extends ABoard
{

	private Paddle playerPaddle;
	private Paddle opponentPaddle;
	private Ball ball;
	private int nbCollision;
	private JLabel playerScore;
	private JLabel opponentScore;

	public CopyOfGameBoard(Application application)
	{
		super(application);

		setBackground(Color.BLACK);
		setLayout(null);

		nbCollision = 0;

		playerPaddle = new Paddle(new Point(40, Application.GAME_CONTENT_HEIGHT / 2));
		opponentPaddle = new Paddle(
				new Point(Application.GAME_CONTENT_WIDTH - 40, Application.GAME_CONTENT_HEIGHT / 2));
		ball = new Ball(new Point(Application.GAME_CONTENT_WIDTH / 2, Application.GAME_CONTENT_HEIGHT / 2));

		// TODO : Remove ball test conf
		ball.setDirection(Application.GAME_LEFT_DIRECTION);

		displayScore(0, 0);
		addPlayerPaddleMouseMoveListener();
		startAnimation();
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		// Drawing paddle & ball
		Color c = Color.WHITE;
		g.setColor(c);

		Rectangle playerRect = playerPaddle.getRectangleRepresentation();
		Rectangle opponentRect = opponentPaddle.getRectangleRepresentation();
		Rectangle ballRect = ball.getRectangleRepresentation();

		g.fillRect(playerRect.x, playerRect.y, playerRect.width, playerRect.height);
		g.fillRect(opponentRect.x, opponentRect.y, opponentRect.width, opponentRect.height);
		g.fillRect(ballRect.x, ballRect.y, ballRect.width, ballRect.height);

		Graphics2D g2d = (Graphics2D) g.create();
		float[] dash4 = { 10f, 10f, 10f };
		BasicStroke bs4 = new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash4, 10f);
		g2d.setStroke(bs4);
		g2d.drawLine(Application.GAME_CONTENT_WIDTH / 2, 0, Application.GAME_CONTENT_WIDTH / 2,
				Application.GAME_CONTENT_HEIGHT);
		g2d.dispose();
	}

	public static void main(String[] args)
	{
		Application application = new Application();
		application.switchBoard(new CopyOfGameBoard(application));
		
		

	}

	private void addPlayerPaddleMouseMoveListener()
	{
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e)
			{
				int mouseY = e.getY();
				if (mouseY < Paddle.PADDLE_HEIGHT / 2)
				{
					playerPaddle.setY(Paddle.PADDLE_HEIGHT / 2);
				} else if (mouseY > Application.GAME_CONTENT_HEIGHT - Paddle.PADDLE_HEIGHT / 2)
				{
					playerPaddle.setY(Application.GAME_CONTENT_HEIGHT - Paddle.PADDLE_HEIGHT / 2);
				} else
				{
					playerPaddle.setY(mouseY);
				}
			}

			@Override
			public void mouseDragged(MouseEvent e)
			{
			}
		});

		// TODO : remove after network implementation
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e)
			{
				int mouseY = e.getY();
				if (mouseY < Paddle.PADDLE_HEIGHT / 2)
				{
					opponentPaddle.setY(Paddle.PADDLE_HEIGHT / 2);
				} else if (mouseY > Application.GAME_CONTENT_HEIGHT - Paddle.PADDLE_HEIGHT / 2)
				{
					opponentPaddle.setY(Application.GAME_CONTENT_HEIGHT - Paddle.PADDLE_HEIGHT / 2);
				} else
				{
					opponentPaddle.setY(mouseY);
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
			public void actionPerformed(ActionEvent ae)
			{
				// TODO : Remove after server implementation
//				if(application.getServerHandler().isServerHost())
//				{
//					computeAnimations();
//				}
				
				computeAnimations();
				repaint();
			}
		};

		Timer timer = new Timer(15, animate);
		timer.start();
	}

	private void computeAnimations()
	{
		// Move ball
		ball.move();
		Point ballPosition = ball.getPosition();

		// Collision with edge ?
		manageEdgeCollision(ballPosition);

		// Collision with paddles ?
		managePaddleCollision();
	}

	private void managePaddleCollision()
	{
		int playerPaddleCol = playerPaddle.colisionDetected(ball.getRectangleRepresentation());
		int opponentPaddleCol = opponentPaddle.colisionDetected(ball.getRectangleRepresentation());

		if (playerPaddleCol != Paddle.NO_COLISION_DETECTED || opponentPaddleCol != Paddle.NO_COLISION_DETECTED)
		{
			int collision = (playerPaddleCol != Paddle.NO_COLISION_DETECTED) ? playerPaddleCol : opponentPaddleCol;

			// Must be invoke before setAngle(), else the translation will be
			// overwritten
			ball.setDirection((playerPaddleCol != Paddle.NO_COLISION_DETECTED) ? Application.GAME_RIGHT_DIRECTION
					: Application.GAME_LEFT_DIRECTION);

			double translation;
			if (collision > 0)
			{
				translation = collision;
				ball.setAngle(EAngle.NORTH, translation / 20);
			} else if (collision < 0)
			{
				translation = collision;
				ball.setAngle(EAngle.SOUTH, -translation / 20);
			} else
			{
				ball.setAngle(EAngle.CENTER);
			}

			nbCollision++;
		}

		ball.setSpeed(nbCollision / 2);
	}

	private void manageEdgeCollision(Point ballPosition)
	{

		// Top & bottom
		if ((ballPosition.y - Ball.BALLE_SIZE / 2 <= 0))
		{
			ball.setAngle(EAngle.SOUTH);
		} else if (ballPosition.y + Ball.BALLE_SIZE / 2 >= Application.GAME_CONTENT_HEIGHT)
		{
			ball.setAngle(EAngle.NORTH);
		}

		// TODO : if point, reset ball + notification
		if (ballPosition.x - Ball.BALLE_SIZE / 2 <= 0)
		{
			System.err.println("point for opponent");
			ball.setPosition(new Point(Application.GAME_CONTENT_WIDTH / 2, Application.GAME_CONTENT_HEIGHT / 2));
			ball.setDirection(Application.GAME_LEFT_DIRECTION);
			ball.setAngle(EAngle.CENTER);
		} else if (ballPosition.x + Ball.BALLE_SIZE / 2 >= Application.GAME_CONTENT_WIDTH)
		{
			System.err.println("point for player");
			ball.setPosition(new Point(Application.GAME_CONTENT_WIDTH / 2, Application.GAME_CONTENT_HEIGHT / 2));
			ball.setDirection(Application.GAME_RIGHT_DIRECTION);
			ball.setAngle(EAngle.CENTER);
		}
	}

	private void displayScore(int playerNbPoint, int opponentNbPoint)
	{
		if (playerScore == null)
		{
			playerScore = new JLabel();
			playerScore.setFont(new Font("SansSerif", Font.BOLD, 40));
			playerScore.setBounds((Application.GAME_CONTENT_WIDTH / 8) * 3, 0, 100, 100);
			playerScore.setForeground(Color.WHITE);
			add(playerScore);
		}
		if (opponentScore == null)
		{
			opponentScore = new JLabel();
			opponentScore.setFont(new Font("SansSerif", Font.BOLD, 40));
			opponentScore.setBounds((Application.GAME_CONTENT_WIDTH / 8) * 5, 0, 100, 100);
			opponentScore.setForeground(Color.WHITE);
			add(opponentScore);
		}

		playerScore.setText(Integer.toString(playerNbPoint));
		opponentScore.setText(Integer.toString(opponentNbPoint));

	}
}
