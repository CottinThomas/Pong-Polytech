package com.polytech.pong.network;

import java.io.ObjectInputStream;

public class MessageHandler implements Runnable {
	
	private NetObject listener;
	
	public MessageHandler(NetObject listener){
		this.listener = listener;
	}

	@Override
	public void run() {
		Object message;
		ObjectInputStream in;
		try {
			in = new ObjectInputStream(listener.connectionSocket.getInputStream());
			message = in.readObject();
			listener.getMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
