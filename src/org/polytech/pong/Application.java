package org.polytech.pong;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.polytech.pong.launcher.LauncherBoard;

/**
 * Main class. Contain "board" to display game content
 * @author Clément
 *
 */
public class Application extends JFrame{

	private static final long serialVersionUID = 1L;
	private Board currentBoard;
	private Board previousBoard;
	
	public Application() {
		initMMI();
	}
	
	private void initMMI()
	{
		currentBoard = new LauncherBoard(this);
		previousBoard = currentBoard; // Avoid null pointer if no switch occur
		add(currentBoard);
		
		setSize(660, 500);
		setTitle("Polytech APP3-INFO // Pong ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
	}
	
	public void switchBoard(Board board)
	{
		previousBoard = currentBoard;
		currentBoard = board;
		
		remove(previousBoard);
		add(currentBoard);
		
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public Board getPreviousBoard() {
		return previousBoard;
	}
	
	public static void main(String[] args) {
		Application application = new Application();
		application.setVisible(true);
	}
	
}
