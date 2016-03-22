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
	private static final String HOST_WAITING_TEXT = "Waiting for another player";
	private static final String SLAVE_WAITING_TEXT = "Trying to join the host";

	private Thread waitingThread;
	private JLabel lbl_waiting;
	private String waitingText;
	private boolean isHost;
	private String hostIp;

	// Host constructor
	public WaitingPlayerBoard(Application application) {
		super(application);
		isHost = true;
		hostIp = null;
		waitingText = HOST_WAITING_TEXT;

		initMMI();
		launchWaitingThread();

	}
	
	// Slave constructor
	public WaitingPlayerBoard(Application application, String hostIp) {
		super(application);
		this.hostIp = hostIp;
		isHost = false;
		waitingText = SLAVE_WAITING_TEXT;

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

		

		if (isHost) {
			constraints.fill = GridBagConstraints.CENTER;
			constraints.weightx = 0.5;
			constraints.gridx = 0;
			constraints.gridy = 1;
			JLabel lbl_ip = new JLabel("Hosting on address : 127.0.0.1"); // TODO : set true address
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
					// Infinit loop while
					while (true) {
						lbl_waiting.setText(waitingText + "    ");
						Thread.sleep(500);
						lbl_waiting.setText(waitingText + " .  ");
						Thread.sleep(500);
						lbl_waiting.setText(waitingText + " .. ");
						Thread.sleep(500);
						lbl_waiting.setText(waitingText + " ...");
						Thread.sleep(500);
					}
				} catch (InterruptedException e) {
				}

			}
		});

		waitingThread.start();
	}

	private void launchServer() {
		// TODO : Launch server (need handler ?)
		// TODO : Subscribe playerJoin & invoke startGame()
	}

	private void startGame() {
		// Stopping thread
		if (waitingThread != null && waitingThread.isAlive())
			;
		{
			waitingThread.interrupt();
		}

		application.switchBoard(new GameBoard(application, isHost));

	}
}
