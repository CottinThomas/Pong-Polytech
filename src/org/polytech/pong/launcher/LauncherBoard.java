package org.polytech.pong.launcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.polytech.pong.Application;
import org.polytech.pong.Board;
import org.polytech.pong.game.GameBoard;
import org.polytech.pong.game.WaitingPlayerBoard;
import org.polytech.pong.ui.CustomJButton;

/**
 * Main application view
 * 
 * @author Clément
 *
 */
public class LauncherBoard extends Board {

	private static final long serialVersionUID = 1L;

	private BufferedImage pongLogo;

	public LauncherBoard(Application application) {
		super(application);

		initMMI();
	}

	private void initMMI() {
		setLayout(new BorderLayout());

		// Main pane configuration
		JPanel pnl_mainPanel = new JPanel();
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.CENTER;
		gridbag.setConstraints(pnl_mainPanel, constraints);
		pnl_mainPanel.setLayout(gridbag);
		pnl_mainPanel.setBackground(Color.BLACK);

		// Logo
		try {
			constraints.fill = GridBagConstraints.CENTER;
			constraints.weightx = 0.5;
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.gridwidth = 3;
			pongLogo = ImageIO.read(this.getClass().getResource("/org/polytech/pong/resources/logo.png"));
			JLabel picLabel = new JLabel(new ImageIcon(pongLogo));
			pnl_mainPanel.add(picLabel, constraints);

			// Reset
			constraints.gridwidth = 1;
			constraints.insets = new Insets(7, 7, 7, 7);

		} catch (IOException ex) {
			System.err.println("Cannot load logo.png");
		}

		// Host button
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 1;
		CustomJButton btn_host = new CustomJButton("HOST");
		btn_host.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				application.switchBoard(new WaitingPlayerBoard(application));
			}
		});
		pnl_mainPanel.add(btn_host, constraints);

		// Join button
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.5;
		constraints.gridx = 1;
		constraints.gridy = 1;
		CustomJButton btn_join = new CustomJButton("JOIN");
		btn_join.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				application.switchBoard(new JoinBoard(application));
			}
		});
		pnl_mainPanel.add(btn_join, constraints);

		// Quit button
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.5;
		constraints.gridx = 2;
		constraints.gridy = 1;
		CustomJButton btn_quit = new CustomJButton("QUIT");
		btn_quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				application.dispatchEvent(new WindowEvent(application, WindowEvent.WINDOW_CLOSING));
			}
		});
		pnl_mainPanel.add(btn_quit, constraints);

		// Footer
		constraints.fill = GridBagConstraints.CENTER;
		constraints.weightx = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 3;
		constraints.insets = new Insets(60, 10, 30, 10);
		JLabel footer = new JLabel("Polytech' Paris-Sud - APP3 INFO - Thomas Cottin & Clément Garin");
		footer.setForeground(Color.WHITE);
		footer.setAlignmentX(CENTER_ALIGNMENT);
		pnl_mainPanel.add(footer, constraints);

		add(pnl_mainPanel, BorderLayout.CENTER);
	}

}
