package com.infosec.gesturedata;

public class AccelEvent {
	
	private float xAccel;
	private float yAccel;
	private float zAccel;
	private long time;
	
	public AccelEvent(float x, float y, float z) {
		this.xAccel = x;
		this.yAccel = y;
		this.zAccel = z;
		this.time = System.nanoTime();
	}
	
	public float getX() {
		return this.xAccel;
	}
	
	public float getY() {
		return this.yAccel;
	}
	
	public float getZ() {
		return this.zAccel;
	}
	
	public long getTime() {
		return this.time;
	}
}
