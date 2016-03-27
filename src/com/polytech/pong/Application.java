package com.polytech.pong;

import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.polytech.pong.board.ABoard;
import com.polytech.pong.board.launcher.LauncherBoard;


/**
 * Main class. Contain "board" to display game content
 * @author Clément
 *
 */
@SuppressWarnings("serial")
public class Application extends JFrame{

	public static final int GAME_CONTENT_WIDTH = 660;
	public static final int GAME_CONTENT_HEIGHT = 500;
	public static final int GAME_LEFT_DIRECTION = -1;
	public static final int GAME_RIGHT_DIRECTION = 1;
	
	private ServerHandler serverHandler;
	private ABoard currentBoard;
	private ABoard previousBoard;
	
	public Application() {
		serverHandler = new ServerHandler(this);
		initMMI();
	}
	
	private void initMMI()
	{
		setSize(GAME_CONTENT_WIDTH, GAME_CONTENT_HEIGHT); // Expected size (false due to windows borders)
		setTitle("Pong | Polytech APP3-INFO");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
		setVisible(true);
		
		// Compute real size for the JFrame content
        Insets i = getInsets();
        setSize(GAME_CONTENT_WIDTH+i.right+i.left,GAME_CONTENT_HEIGHT+i.bottom+i.top);
        
        // Adding default board
        currentBoard = new LauncherBoard(this);
		previousBoard = currentBoard; // Avoid null pointer if no switch occur
		add(currentBoard);
		SwingUtilities.updateComponentTreeUI(this); // Refresh ui
	}
	
	public int getWidth()
	{
		return getContentPane().getWidth();
	}
	
	public int getHeight()
	{
		return getContentPane().getHeight();
	}
	
	public void switchBoard(ABoard board)
	{
		previousBoard = currentBoard;
		currentBoard = board;
		
		remove(previousBoard);
		add(currentBoard);
		
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public ServerHandler getServerHandler()
	{
		return serverHandler;
	}
	
	public ABoard getCurrentBoard() {
		return currentBoard;
	}
	
	public ABoard getPreviousBoard() {
		return previousBoard;
	}
	
	public static void main(String[] args) {
		new Application();
	}
	
}
