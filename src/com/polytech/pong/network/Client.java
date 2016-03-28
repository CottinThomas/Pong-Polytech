package com.polytech.pong.network;

import java.io.IOException;
import java.net.Socket;

public class Client extends NetObject{
	
	public Client(String serverIp){
		super();
		try{
			connectionSocket = new Socket(serverIp, 8095);
		} catch (IOException e) {
			System.err.println("IO exception. Aborted. Details:");
        	e.printStackTrace();
        }
	}
	
	@Override
	public void debug(String message){
		message = "[CLIENT] "+message;
		super.debug(message);
	}
}
