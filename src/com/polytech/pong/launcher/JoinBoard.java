package com.polytech.pong.launcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.polytech.pong.Application;
import com.polytech.pong.Board;
import com.polytech.pong.game.WaitingPlayerBoard;
import com.polytech.pong.ui.CustomJButton;
import com.polytech.pong.ui.CustomJTextField;

/**
 * View to join a hosted game
 * @author Clément
 *
 */
public class JoinBoard extends Board {
	
	private static final long serialVersionUID = 1L;

	public JoinBoard(Application application) {
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
	    
	    // Textfield
	    constraints.fill = GridBagConstraints.HORIZONTAL;
	    constraints.weightx = 0.5;
	    constraints.gridx = 0;
	    constraints.gridy = 0;
	    constraints.insets = new Insets(7, 7, 7, 7);
	    CustomJTextField txf_hostIp = new CustomJTextField("127.0.0.1");
	    pnl_mainPanel.add(txf_hostIp, constraints);
	    
	    // Join button
	    constraints.fill = GridBagConstraints.HORIZONTAL;
	    constraints.weightx = 0.1;
	    constraints.gridx = 1;
	    constraints.gridy = 0;
	    constraints.insets = new Insets(7, 7, 7, 7);
	    CustomJButton btn_join = new CustomJButton("JOIN");
	    btn_join.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				application.switchBoard(new WaitingPlayerBoard(application, txf_hostIp.getText()));
			}
		});
	    pnl_mainPanel.add(btn_join, constraints);
	    
	    // Back button
	    constraints.fill = GridBagConstraints.CENTER;
	    constraints.weightx = 0.1;
	    constraints.gridx = 0;
	    constraints.gridy = 2;
	    constraints.gridwidth = 2;
	    constraints.insets = new Insets(50, 7, 7, 7);
	    CustomJButton btn_back = new CustomJButton("< BACK");
	    btn_back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				application.switchBoard(new LauncherBoard(application));
			}
		});
	    pnl_mainPanel.add(btn_back, constraints);

	    add(pnl_mainPanel, BorderLayout.CENTER);
	}
}
