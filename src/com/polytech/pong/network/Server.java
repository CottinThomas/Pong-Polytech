package com.polytech.pong.network;

import java.io.IOException;
import java.net.ServerSocket;

import com.polytech.pong.network.IServerStatus.EServerStatus;

public class Server extends NetObject {

	private ServerSocket serverSocket;
	private Thread messageThread;

	public Server() {
		super();
	}
	
	@Override
	public void run() {
		try {

			try {

				debug("Creating new socket on port " + PORT);
				serverSocket = new ServerSocket(PORT);
				debug("Waiting for connection.");
				connectionSocket = serverSocket.accept();
				debug("Estabished connection.");
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

		} catch (InterruptedException e1) {
			stopServer();
			e1.printStackTrace();
		}
	}

	private void stopServer() {

		try {
			if (connectionSocket != null) {
				connectionSocket.close();
			}
			if (serverSocket != null) {
				serverSocket.close();
			}
			if (messageThread != null && messageThread.isAlive()) {
				messageThread.interrupt();
			}
		} catch (IOException e) {
		}
	}

	@Override
	public void interrupt() {
		super.interrupt();
		try {
			serverSocket.close(); // Fermeture du flux si l'interruption n'a pas
									// fonctionné.
		} catch (IOException e) {
		}
	}

	@Override
	public void debug(String message) {
		message = "[SERVER] " + message;
		super.debug(message);
	}

	@Override
	public boolean closeConnection() {
		super.closeConnection();
		try {
			serverSocket.close();
			return true;
		} catch (IOException e) {
			System.err.println("IO exception. Aborted. Details:");
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.debug("Envoi du message Bonjour au client");
		boolean open = true;
		while (open)
			open = server.sendMessage("Bonjour");
		server.debug("The connection socket is closed");
	}

}
