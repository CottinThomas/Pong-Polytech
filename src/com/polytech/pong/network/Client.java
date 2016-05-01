package com.polytech.pong.network;

import java.io.IOException;
import java.net.Socket;

import com.polytech.pong.network.IServerStatus.EServerStatus;

public class Client extends NetObject {

	private String ip;
	private int port;
	private Thread messageThread;

	public Client(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;

	}
	
	@Override
	public void run() {

		try {
			try {
				debug("Trying to connect to " + ip + ":" + port);
				connectionSocket = new Socket(ip, port);
				debug("Connected !");
				messageThread = new MessageHandler(this);
				messageThread.start();
				
				for (IServerStatus subscriber : subscribers) {
					subscriber.notifyServerStatus(EServerStatus.CONNECTED);
				}

			} catch (IOException e) {
				stopServer();
				e.printStackTrace();
			}

			while (true) {
				sleep(50);
			}
		} catch (InterruptedException e) {
			stopServer();
			e.printStackTrace();
		}
	}

	private void stopServer() {

		try {
			if (connectionSocket != null) {
				connectionSocket.close();
			}
		} catch (IOException e) {
		}

	}

	@Override
	public void debug(String message) {
		message = "[CLIENT] " + message;
		super.debug(message);
	}

	public static void main(String[] args) throws Exception {
		Client client = new Client("127.0.0.1", NetObject.PORT);
		for (int i = 0; i < 10; i++) {
			client.sendMessage("Coucou !");
		}
		client.sendCloseMessage();
		client.closeConnection();
		client.debug("I closed the connection.");
	}
}
