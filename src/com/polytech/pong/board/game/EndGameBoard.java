package com.polytech.pong.board.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import com.polytech.pong.Application;
import com.polytech.pong.board.ABoard;
import com.polytech.pong.board.launcher.LauncherBoard;
import com.polytech.pong.ui.CustomJButton;

@SuppressWarnings("serial")
public class EndGameBoard extends ABoard {

	public enum EGameEndStatus {VICOTRY, DEFEAT, CONNECTION_LOST};
	
	private static final String TEXT_VICTORY = "Victory !";
	private static final String TEXT_DEFEAT = "Defeat ...";
	private static final String TEXT_CONNECTION_LOST = "Connection lost";

	private EGameEndStatus status;

	public EndGameBoard(Application application, EGameEndStatus status) {
		super(application);
		this.status = status;
		

		initMMI();
		
		application.getServerHandler().stopServer();
	}

	private void initMMI() {
		this.setBackground(Color.BLACK);

		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.CENTER;
		gridbag.setConstraints(this, constraints);
		this.setLayout(gridbag);

		constraints.fill = GridBagConstraints.CENTER;
		constraints.weightx = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(7, 7, 7, 7);
		JLabel lbl_endgame = new JLabel();
		
		switch(status)
		{
		case CONNECTION_LOST:
			lbl_endgame.setText(TEXT_CONNECTION_LOST);
			break;
		case DEFEAT:
			lbl_endgame.setText(TEXT_DEFEAT);
			break;
		case VICOTRY:
			lbl_endgame.setText(TEXT_VICTORY);
			break;
		default:
			lbl_endgame.setText("UNKNOWN ERROR");
			break;
		}
		
		lbl_endgame.setFont(new Font("SansSerif", Font.BOLD, 50));
		lbl_endgame.setForeground(Color.WHITE);
		this.add(lbl_endgame, constraints);


		constraints.fill = GridBagConstraints.CENTER;
		constraints.weightx = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(40, 7, 7, 7);
		CustomJButton btn_host = new CustomJButton("< BACK TO TITLE SCREEN");
		btn_host.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				application.switchBoard(new LauncherBoard(application));
			}
		});
		
		this.add(btn_host, constraints);
	}

}
