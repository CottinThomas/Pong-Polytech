package com.polytech.pong.network;

import java.io.IOException;
import java.net.ServerSocket;

public class Server extends NetObject{
	
	private ServerSocket serverSocket;
	
	public Server(){
		super();
		try {
			debug("Creating new socket on port "+PORT);
			serverSocket = new ServerSocket(PORT);
			debug("Waiting for connection.");
			connectionSocket = serverSocket.accept();
			debug("Estabished connection.");
			new Thread(super.messageHandler = new MessageHandler(this)).start();
		} catch (IOException e){
			System.err.println("IO exception. Aborted. Details:");
        	e.printStackTrace();
		}
	}
	
	@Override
	public void debug(String message){
		message = "[SERVER] "+message;
		super.debug(message);
	}
	
	@Override
	public boolean closeConnection(){
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
		while(open)
			open = server.sendMessage("Bonjour");
		server.messageHandler = null;
		server.debug("The connection socket is closed");
	}

}
