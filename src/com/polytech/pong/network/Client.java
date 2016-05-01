package com.polytech.pong.network;

import java.net.Socket;

public class Client extends NetObject{
	
	public Client(String ip, int port){
		super();
		try{
			debug("Trying to connect to "+ip+":"+port);
			connectionSocket = new Socket(ip, port);
			debug("Connected !");
			new Thread(super.messageHandler = new MessageHandler(this)).start();
		} catch (Exception e) {
			System.err.println("IO exception. Aborted. Details:");
        	e.printStackTrace();
        }
	}
	
	@Override
	public void debug(String message){
		message = "[CLIENT] "+message;
		super.debug(message);
	}
	
	public static void main(String[] args) throws Exception{
		Client client = new Client("127.0.0.1",NetObject.PORT);
		for(int i = 0; i < 10; i++){
			client.sendMessage("Coucou !");
		}
		client.sendCloseMessage();
		client.closeConnection();
		client.debug("I closed the connection.");
	}
}
