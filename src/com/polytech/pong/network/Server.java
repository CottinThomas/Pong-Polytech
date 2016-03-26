package com.polytech.pong.network;

import java.io.IOException;
import java.net.ServerSocket;

public class Server extends NetObject {
	
	private ServerSocket serverSocket;
	
	public Server(){
		super();
		try {
			serverSocket = new ServerSocket(8095);
			super.debug("Waiting for connection.");
			connectionSocket = serverSocket.accept();
			super.debug("Estabished connection.");
		} catch (IOException e){
			System.out.println("IO exception. Aborted. Details:");
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
			System.out.println("IO exception. Aborted. Details:");
			e.printStackTrace();
		}
		return false;
	}

}
