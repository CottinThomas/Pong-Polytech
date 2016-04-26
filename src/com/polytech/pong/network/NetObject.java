package com.polytech.pong.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.polytech.pong.Application;
import com.polytech.pong.GameStatus;

public abstract class NetObject {
	
	public static final int PORT = 8092;
	
	protected Socket connectionSocket;
	
	public Object getMessage(){
		try{
			ObjectInputStream in = new ObjectInputStream(connectionSocket.getInputStream());
			return in.readObject();
		} catch(UnknownHostException e){
			System.err.println("Unknown host. Aborted. Details:");
		} catch (IOException e) {
			System.err.println("IO exception. Aborted. Details:");
        	e.printStackTrace();
        } catch (ClassNotFoundException e) {
        	System.err.println("Class not founded. Aborted. Details:");
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean sendMessage(Object message){
		try{
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
		sendMessage(new Boolean(false));
	}

}
