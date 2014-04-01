package com.infosec.gesturedata;
import java.util.ArrayList;

public class GestureData {

	private ArrayList<AccelEvent> data;
	private Point finalPosition;

	public GestureData(ArrayList<AccelEvent> data) {
		this.data = data;
	}
	
}
