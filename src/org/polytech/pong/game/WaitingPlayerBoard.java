package org.polytech.pong.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.polytech.pong.Application;
import org.polytech.pong.Board;
import org.polytech.pong.ui.CustomJButton;

public class WaitingPlayerBoard extends Board {

	private static final long serialVersionUID = 1L;

	public WaitingPlayerBoard(Application application) {
		super(application);
		initMMI();
	}
	
	private void initMMI()
	{
		setLayout(new BorderLayout());
		
		// Main pane configuration
		JPanel pnl_mainPanel = new JPanel();
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.CENTER;
		gridbag.setConstraints(pnl_mainPanel, constraints);
		pnl_mainPanel.setLayout(gridbag);
		pnl_mainPanel.setBackground(Color.BLACK);
		
		constraints.fill = GridBagConstraints.CENTER;
		constraints.weightx = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(7, 7, 7, 7);
		JLabel lbl_waiting = new JLabel("Waiting for an other player ...");
		lbl_waiting.setFont(new Font("SansSerif", Font.BOLD, 20));
		lbl_waiting.setForeground(Color.WHITE);
		pnl_mainPanel.add(lbl_waiting, constraints);
		
		constraints.fill = GridBagConstraints.CENTER;
		constraints.weightx = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 1;
		CustomJButton btn_host = new CustomJButton("< BACK");
		btn_host.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				application.switchBoard(application.getPreviousBoard());
			}
		});
		pnl_mainPanel.add(btn_host, constraints);
		
		add(pnl_mainPanel, BorderLayout.CENTER);
	}
}
