package com.polytech.pong.board.launcher;

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

import com.polytech.pong.Application;
import com.polytech.pong.board.ABoard;
import com.polytech.pong.board.game.WaitingPlayerBoard;
import com.polytech.pong.ui.CustomJButton;

/**
 * Main application view
 * 
 * @author Cl�ment
 *
 */
public class LauncherBoard extends ABoard {

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
			pongLogo = ImageIO.read(this.getClass().getResource("/com/polytech/pong/resources/logo.png"));
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
				application.switchBoard(new WaitingPlayerBoard(application, null));
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
		JLabel footerName = new JLabel("Thomas Cottin & Clément Garin");
		footerName.setForeground(Color.WHITE);
		footerName.setAlignmentX(CENTER_ALIGNMENT);
		pnl_mainPanel.add(footerName, constraints);
		
		constraints.fill = GridBagConstraints.CENTER;
		constraints.weightx = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 3;
		constraints.insets = new Insets(0, 10, 1, 10);
		JLabel footerSchool = new JLabel("Polytech' Paris-Sud - APP3 INFO");
		footerSchool.setForeground(Color.WHITE);
		footerSchool.setAlignmentX(CENTER_ALIGNMENT);
		pnl_mainPanel.add(footerSchool, constraints);
		
		constraints.fill = GridBagConstraints.CENTER;
		constraints.weightx = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 3;
		constraints.insets = new Insets(0, 10, 20, 10);
		JLabel footerYear = new JLabel("2015 - 2016");
		footerYear.setForeground(Color.WHITE);
		footerYear.setAlignmentX(CENTER_ALIGNMENT);
		pnl_mainPanel.add(footerYear, constraints);

		add(pnl_mainPanel, BorderLayout.CENTER);
	}

}
