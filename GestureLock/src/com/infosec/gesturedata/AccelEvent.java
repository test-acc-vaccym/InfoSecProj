package com.infosec.gesturedata;

public class AccelEvent {

	
	static final float ALPHA = 0.20f;
	private float xAccel;
	private float yAccel;
	private float zAccel;
	private long time;
	
	public AccelEvent() {
	}
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

	public void setX(float xAccel) {
		this.xAccel = xAccel;
	}
	
	public void setY(float yAccel) {
		this.yAccel = yAccel;
	}
	
	public void setZ(float zAccel) {
		this.zAccel = zAccel;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
}
