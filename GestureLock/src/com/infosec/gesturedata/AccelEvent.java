package com.infosec.gesturedata;

import java.io.FileInputStream;

import android.content.Context;

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
}
