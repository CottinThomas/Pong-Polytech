package com.polytech.pong.board.game;

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

import com.polytech.pong.Application;
import com.polytech.pong.IServerEvent;
import com.polytech.pong.board.ABoard;
import com.polytech.pong.network.IServerStatus.EServerStatus;
import com.polytech.pong.ui.CustomJButton;

public class WaitingPlayerBoard extends ABoard {

	private static final long serialVersionUID = 1L;
	private static final String HOST_WAITING_TEXT = "Waiting for another player";
	private static final String SLAVE_WAITING_TEXT = "Trying to join the host";

	private Thread waitingThread;
	private JLabel lbl_waiting;
	private String waitingText;
	private String hostIp;
	private IServerEvent serverEvent;

	public WaitingPlayerBoard(final Application application, String ip) {
		super(application);
		hostIp = ip;

		serverEvent = new IServerEvent() {
			
			@Override
			public void notifyServerStatus(EServerStatus status) {
				if(status == EServerStatus.CONNECTED)
				{
					// Stopping thread
					if (waitingThread != null && waitingThread.isAlive())
						;
					{
						waitingThread.interrupt();
					}

					application.getServerHandler().removeServerEvent(serverEvent);
					application.switchBoard(new GameBoard(application));
				}				
			}
			
			@Override
			public void notifyMessageReceived(Object message) {				
			}
		};
		
		// host
		if(ip == null || ip.isEmpty())
		{
			waitingText = HOST_WAITING_TEXT;
			hostIp = application.getServerHandler().createServer();
		}
		// slave
		else
		{
			waitingText = SLAVE_WAITING_TEXT;
			application.getServerHandler().createServer(ip);
		}
		
		application.getServerHandler().addServerEvent(serverEvent);
		
		initMMI();
		launchWaitingThread();
		

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

		constraints.fill = GridBagConstraints.CENTER;
		constraints.weightx = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(7, 7, 7, 7);
		lbl_waiting = new JLabel(waitingText);
		lbl_waiting.setFont(new Font("SansSerif", Font.BOLD, 20));
		lbl_waiting.setForeground(Color.WHITE);
		pnl_mainPanel.add(lbl_waiting, constraints);

		if (application.getServerHandler().isServerHost()) {
			constraints.fill = GridBagConstraints.CENTER;
			constraints.weightx = 0.5;
			constraints.gridx = 0;
			constraints.gridy = 1;
			JLabel lbl_ip = new JLabel("Hosting on address : " + hostIp); 
			lbl_ip.setForeground(Color.WHITE);
			pnl_mainPanel.add(lbl_ip, constraints);
		}

		constraints.fill = GridBagConstraints.CENTER;
		constraints.weightx = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(40, 7, 7, 7);
		CustomJButton btn_host = new CustomJButton("< BACK");
		btn_host.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Stopping thread
				if (waitingThread != null && waitingThread.isAlive())
					;
				{
					waitingThread.interrupt();
				}

				application.getServerHandler().stopServer();
				application.switchBoard(application.getPreviousBoard());
			}
		});
		pnl_mainPanel.add(btn_host, constraints);

		add(pnl_mainPanel, BorderLayout.CENTER);
	}

	private void launchWaitingThread() {
		waitingThread = new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					while (!application.getServerHandler().isServerConnected()) {
						lbl_waiting.setText(waitingText + "    ");
						Thread.sleep(500);
						lbl_waiting.setText(waitingText + " .  ");
						Thread.sleep(500);
						lbl_waiting.setText(waitingText + " .. ");
						Thread.sleep(500);
						lbl_waiting.setText(waitingText + " ...");
						Thread.sleep(500);
					}
					
					application.switchBoard(new GameBoard(application));
				} catch (InterruptedException e) {
				}
			}
		});

		waitingThread.start();
	}


}
