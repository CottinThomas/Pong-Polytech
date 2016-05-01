package com.polytech.pong.network.message;

import java.io.Serializable;

import com.polytech.pong.component.Paddle;

public class SlaveMessage implements Serializable{

	private static final long serialVersionUID = 8676968886262692434L;
	private Paddle slavePaddle;

	public SlaveMessage(Paddle slavePaddle) {
		super();
		this.slavePaddle = slavePaddle;
	}

	public Paddle getSlavePaddle() {
		return slavePaddle;
	}

	public void setSlavePaddle(Paddle slavePaddle) {
		this.slavePaddle = slavePaddle;
	}

	
}
