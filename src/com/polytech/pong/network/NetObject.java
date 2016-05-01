package com.polytech.pong.network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.polytech.pong.Application;
import com.polytech.pong.GameStatus;

public abstract class NetObject {
	
	public static final int PORT = 8092;
	
	protected Socket connectionSocket;
	protected MessageHandler messageHandler;
	
	public Object getMessage(Object message){
		if(message instanceof String){
			debug(message.toString());
			if(message.toString().equals("close")){
				debug("Received close connection message");
				this.closeConnection();
			}
		}
		return message;
	}
	
	public boolean sendMessage(Object message){
		try{			
			if(connectionSocket.isClosed())
				return false;
			ObjectOutputStream out = new ObjectOutputStream(connectionSocket.getOutputStream());
			out.writeObject(message);
			out.flush();
			return true;
		} catch (IOException e) {
			System.err.println("IO exception. Aborted. Details:");
        	e.printStackTrace();
        }
		return false;
	}
	
	public boolean closeConnection(){
		try {
			connectionSocket.close();
			return true;
		} catch (IOException e) {
			System.out.println("IO exception. Aborted. Details:");
			e.printStackTrace();
		}
		return false;
	}
	
	public void debug(String message){
		if(Application.STATUS == GameStatus.DEBUG)
			System.out.println("[DEBUG] "+message);
	}
	
	public void sendCloseMessage(){
		sendMessage("close");
	}

}
