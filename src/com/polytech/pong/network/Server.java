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
		new Thread(new Client(server.connectionSocket)).start();
		boolean stop = false;
		while(!stop){
			Object message = server.getMessage();
			if(message instanceof Boolean){
				if(!(Boolean)message){
					server.closeConnection();
					server.debug("Connection stoped by client");
					stop = true;
				}
			}
			if(!server.connectionSocket.isClosed()){
				server.sendMessage("Bonjour");
			}
		}
	}

}
