package com.polytech.pong.network.message;

import java.io.Serializable;

import com.polytech.pong.component.Ball;
import com.polytech.pong.component.Paddle;

public class MasterMessage implements Serializable{

	private static final long serialVersionUID = -7315365207476721113L;
	
	private int masterPoint;
	private int slavePoint;
	private Paddle masterPaddle;
	private Ball ball;
	
	public MasterMessage(int masterPoint, int slavePoint, Paddle masterPaddle,
			Ball ball) {
		super();
		this.masterPoint = masterPoint;
		this.slavePoint = slavePoint;
		this.masterPaddle = masterPaddle;
		this.ball = ball;
	}

	public int getMasterPoint() {
		return masterPoint;
	}

	public void setMasterPoint(int masterPoint) {
		this.masterPoint = masterPoint;
	}

	public int getSlavePoint() {
		return slavePoint;
	}

	public void setSlavePoint(int slavePoint) {
		this.slavePoint = slavePoint;
	}

	public Paddle getMasterPaddle() {
		return masterPaddle;
	}

	public void setMasterPaddle(Paddle masterPaddle) {
		this.masterPaddle = masterPaddle;
	}

	public Ball getBall() {
		return ball;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
	}
	
	
	
	
}
