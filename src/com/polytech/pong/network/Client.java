package com.polytech.pong.network;

import java.net.Socket;

public class Client extends NetObject implements Runnable{
	
	public Client(Socket socket){
		super();
		try{
			debug("Trying to connect to "+socket.getInetAddress().toString()+":"+socket.getPort());
			connectionSocket = socket;
			debug("Connected !");
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) throws Exception{
		Client client = new Client(new Socket("localhost", PORT));
		int i = 0;
		while(i<10){
			client.sendMessage(null);
			Object message = client.getMessage();
			if(message instanceof String)
				client.debug("Message recu : "+message.toString());
			else if(message instanceof Boolean){
				if(!(Boolean)message){
					client.closeConnection();
					client.debug("Connection stoped by server");
				}
			}
			i++;
		}
		client.sendCloseMessage();
		client.closeConnection();
	}
}
