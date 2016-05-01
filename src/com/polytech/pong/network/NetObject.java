package com.polytech.pong.network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.polytech.pong.Application;
import com.polytech.pong.GameStatus;

public abstract class NetObject extends Thread{
	
	public static final int PORT = 8092;
	
	protected Socket connectionSocket;
	protected List<IServerStatus> subscribers;
	
	public NetObject() {
		subscribers = new ArrayList<>();
	}
	
	
	public Object getMessage(Object message){
		
		for(IServerStatus subscriber : subscribers)
		{
			subscriber.notifyMessageReceived(message);
		}
		
		
		if(message instanceof String){
			debug(message.toString());
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
	
	// TODO : Check behavior
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
	
	// TODO : Check utility
	public void sendCloseMessage(){
		sendMessage("close");
	}
	
	public void addServerStatusListener(IServerStatus listener)
	{
		subscribers.add(listener);
	}
	
	public void removeServerStatusListener(IServerStatus listener)
	{
		subscribers.remove(listener);
	}

}
