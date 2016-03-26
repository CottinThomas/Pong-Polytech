package com.polytech.pong.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.polytech.pong.Application;
import com.polytech.pong.GameStatus;

public abstract class NetObject {
	
	protected Socket connectionSocket;
	
	public String getMessage(){
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			return in.readLine();
		} catch(UnknownHostException e){
			System.out.println("Unknown host. Aborted. Details:");
		} catch (IOException e) {
			System.out.println("IO exception. Aborted. Details:");
        	e.printStackTrace();
        }
		return null;
	}
	
	public boolean sendMessage(String message){
		try{
			PrintWriter out = new PrintWriter(connectionSocket.getOutputStream());
			out.println(message);
			out.flush();
			return true;
		} catch (IOException e) {
			System.out.println("IO exception. Aborted. Details:");
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

}
