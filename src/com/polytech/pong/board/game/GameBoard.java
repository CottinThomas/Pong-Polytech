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
import com.polytech.pong.IServerEvent;
import com.polytech.pong.ServerHandler;
import com.polytech.pong.board.ABoard;
import com.polytech.pong.board.game.EndGameBoard.EGameEndStatus;
import com.polytech.pong.component.Ball;
import com.polytech.pong.component.Ball.EAngle;
import com.polytech.pong.component.Paddle;
import com.polytech.pong.network.IServerStatus.EServerStatus;
import com.polytech.pong.network.message.MasterMessage;
import com.polytech.pong.network.message.SlaveMessage;

// TODO : Mirror ball position for the opponent ?
// TODO : Transmit server received information;

@SuppressWarnings("serial")
public class GameBoard extends ABoard
{

	private Paddle playerPaddle;
	private Paddle opponentPaddle;
	private Ball ball;
	private int nbCollision;
	private int playerScore;
	private int opponentScore;
	private JLabel lbl_playerScore;
	private JLabel lbl_opponentScore;
	private ServerHandler serverHandler;
	private Timer timer;
	private boolean goal;
	private Application applicationD;

	public GameBoard(Application application)
	{
		super(application);
		this.applicationD = application;

		setBackground(Color.BLACK);
		setLayout(null);
		
		IServerEvent serverEvent = new IServerEvent() {
			
			@Override
			public void notifyServerStatus(EServerStatus status) {
				if(status == EServerStatus.DISCONNECTED)
				{
					applicationD.switchBoard(new EndGameBoard(applicationD, EGameEndStatus.CONNECTION_LOST));
				}				
			}
			
			@Override
			public void notifyMessageReceived(Object message) {				
			}
		};
		
		serverHandler = application.getServerHandler();
		serverHandler.addServerEvent(serverEvent);

		if(serverHandler.isServerHost())
		{
			System.err.println("MASTER");
			configureAsMaster();
		}
		else
		{
			configureAsSlave();
		}

		addPlayerPaddleMouseMoveListener();
		resetGame();
		startAnimation();
	}
	
	private void configureAsSlave()
	{
		opponentPaddle = new Paddle(new Point(40, Application.GAME_CONTENT_HEIGHT / 2));
		playerPaddle = new Paddle(
				new Point(Application.GAME_CONTENT_WIDTH - 40, Application.GAME_CONTENT_HEIGHT / 2));
		ball = new Ball(new Point(Application.GAME_CONTENT_WIDTH / 2, Application.GAME_CONTENT_HEIGHT / 2));
		
		serverHandler.addServerEvent(new IServerEvent() {
			
			@Override
			public void notifyServerStatus(EServerStatus status) {

			}
			
			@Override
			public void notifyMessageReceived(Object message) {
				if(message instanceof MasterMessage)
				{
					MasterMessage masterMessage = (MasterMessage) message;
					ball = masterMessage.getBall();
					opponentPaddle = masterMessage.getMasterPaddle();
					
					if(opponentScore != masterMessage.getMasterPoint() || playerScore != masterMessage.getSlavePoint())
					{
						opponentScore = masterMessage.getMasterPoint();
						playerScore = masterMessage.getSlavePoint();
						displayScore(playerScore, opponentScore);
					}
					
					
				}
			}
		});
	}
	
	private void configureAsMaster()
	{
		playerPaddle = new Paddle(new Point(40, Application.GAME_CONTENT_HEIGHT / 2));
		opponentPaddle = new Paddle(
				new Point(Application.GAME_CONTENT_WIDTH - 40, Application.GAME_CONTENT_HEIGHT / 2));
		ball = new Ball(new Point(Application.GAME_CONTENT_WIDTH / 2, Application.GAME_CONTENT_HEIGHT / 2));
		
		nbCollision = 0;
		ball.setDirection(Application.GAME_LEFT_DIRECTION);
		
		serverHandler.addServerEvent(new IServerEvent() {
			
			@Override
			public void notifyServerStatus(EServerStatus status) {
			}
			
			@Override
			public void notifyMessageReceived(Object message) {

				if(message instanceof SlaveMessage)
				{
					SlaveMessage slaveMessage = (SlaveMessage) message;
					opponentPaddle = slaveMessage.getSlavePaddle();
				}
			}
		});
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
				
				if(!serverHandler.isServerHost())
				{
					serverHandler.getServer().sendMessage(new SlaveMessage(playerPaddle));
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
				if(application.getServerHandler().isServerHost())
				{
					if(!goal)
					{
						computeAnimations();
					}
					serverHandler.getServer().sendMessage(new MasterMessage(playerScore, opponentScore, playerPaddle, ball));
				}
				

				repaint();
			}
		};

		timer = new Timer(5, animate);
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
				ball.setAngle(EAngle.NORTH, translation / 30);
			} else if (collision < 0)
			{
				translation = collision;
				ball.setAngle(EAngle.SOUTH, -translation / 30);
			} else
			{
				ball.setAngle(EAngle.CENTER);
			}

			nbCollision++;
		}

		ball.setSpeed(1 + (nbCollision / 4));
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
			opponentScore++;
			ball.setPosition(new Point(Application.GAME_CONTENT_WIDTH / 2, Application.GAME_CONTENT_HEIGHT / 2));
			ball.setDirection(Application.GAME_LEFT_DIRECTION);
			ball.setAngle(EAngle.CENTER);
			resetGame();
		} else if (ballPosition.x + Ball.BALLE_SIZE / 2 >= Application.GAME_CONTENT_WIDTH)
		{
			playerScore++;
			ball.setPosition(new Point(Application.GAME_CONTENT_WIDTH / 2, Application.GAME_CONTENT_HEIGHT / 2));
			ball.setDirection(Application.GAME_RIGHT_DIRECTION);
			ball.setAngle(EAngle.CENTER);
			resetGame();
		}
	}

	private void displayScore(int playerNbPoint, int opponentNbPoint)
	{
		if (lbl_playerScore == null)
		{
			lbl_playerScore = new JLabel();
			lbl_playerScore.setFont(new Font("SansSerif", Font.BOLD, 40));
			lbl_playerScore.setBounds((Application.GAME_CONTENT_WIDTH / 8) * 3, 0, 100, 100);
			lbl_playerScore.setForeground(Color.WHITE);
			add(lbl_playerScore);
		}
		if (lbl_opponentScore == null)
		{
			lbl_opponentScore = new JLabel();
			lbl_opponentScore.setFont(new Font("SansSerif", Font.BOLD, 40));
			lbl_opponentScore.setBounds((Application.GAME_CONTENT_WIDTH / 8) * 5, 0, 100, 100);
			lbl_opponentScore.setForeground(Color.WHITE);
			add(lbl_opponentScore);
		}
		
		if(serverHandler.isServerHost())
		{
			lbl_playerScore.setText(Integer.toString(playerNbPoint));
			lbl_opponentScore.setText(Integer.toString(opponentNbPoint));
		}
		else
		{
			lbl_playerScore.setText(Integer.toString(opponentNbPoint));
			lbl_opponentScore.setText(Integer.toString(playerNbPoint));
		}
		
		checkGameEnd();


	}
	
	private void checkGameEnd()
	{
		if(playerScore >= Application.POINTS_TO_WIN)
		{
			if(serverHandler.isServerHost())
			{
				serverHandler.getServer().sendMessage(new MasterMessage(playerScore, opponentScore, playerPaddle, ball));
			}
			timer.stop();
			application.switchBoard(new EndGameBoard(application, EGameEndStatus.VICOTRY));
		}
		else if(opponentScore >= Application.POINTS_TO_WIN)
		{
			if(serverHandler.isServerHost())
			{
				serverHandler.getServer().sendMessage(new MasterMessage(playerScore, opponentScore, playerPaddle, ball));
			}
			timer.stop();
			application.switchBoard(new EndGameBoard(application, EGameEndStatus.DEFEAT));
		}
	}
	
	private void resetGame()
	{
		displayScore(playerScore, opponentScore);
		nbCollision = 0;
		ball.setSpeed(0);
		
		goal = true;
		Thread waiting = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					goal = false;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		waiting.start();
	}

	
}
