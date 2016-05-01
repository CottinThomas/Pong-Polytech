package com.polytech.pong;

import com.polytech.pong.network.IServerStatus.EServerStatus;

public interface IServerEvent {
	public void notifyServerStatus(EServerStatus status);
	public void notifyMessageReceived(Object message);
}
