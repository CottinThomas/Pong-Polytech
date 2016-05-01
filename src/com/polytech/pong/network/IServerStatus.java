package com.polytech.pong.network;

public interface IServerStatus {

	public enum EServerStatus {CONNECTED, DISCONNECTED};
	
	public void notifyServerStatus(EServerStatus status);
	public void notifyMessageReceived(Object message);
}
