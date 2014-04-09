package com.infosec.gesturedata;
import java.util.ArrayList;

public class GestureData {

	public ArrayList<direction> data;

	private boolean right;
	private boolean left;
	private boolean forward;
	private boolean backward;
	private boolean up;
	private boolean down;
	
	private enum direction {RIGHT, LEFT, FORWARD, BACKWARD, UP, DOWN}
	
	public GestureData() {
		this.data = new ArrayList<direction>();
		resetBool();
	}
	
	public void accelerometerParser(float[] input){
		if((input[0]*100 >= 34.0f) && !right){
			// Right
			resetBool();
			this.right = true;
			// insert
			insertDirection(direction.RIGHT);
			
		}else if((input[0]*100 <= -34.0f) && !left){
			// Left
			resetBool();
			this.left = true;
			// insert
			insertDirection(direction.LEFT);
			
		}else if((input[1]*100 >= 33.0f) && !forward){
			// Forward
			resetBool();
			this.forward = true;
			// insert
			insertDirection(direction.FORWARD);
			
		}else if((input[1]*100 <= -33.0f) && !backward){
			// Backward
			resetBool();
			this.backward = true;
			// insert
			insertDirection(direction.BACKWARD);
			
		}else if((input[2]*100 >= 35.0f) && !up){
			// Zenith (UP)
			resetBool();
			this.up = true;
			// insert
			insertDirection(direction.UP);
			
		}else if((input[2]*100 <= -35.0f) && !down){
			// Nadir (DOWN)
			resetBool();
			this.down = true;
			// insert
			insertDirection(direction.DOWN);
			
		}
	}
	
	private void insertDirection(direction dir){
		switch(dir){
		case RIGHT:
			this.data.add(dir);
			break;
		case LEFT:
			this.data.add(dir);
			break;
		case FORWARD:
			this.data.add(dir);
			break;
		case BACKWARD:
			this.data.add(dir);
			break;
		case UP:
			this.data.add(dir);
			break;
		case DOWN:
			this.data.add(dir);
			break;
		default:
			break;
		}
	}
	
	private void resetBool(){
		this.right = false;
		this.forward = false;
		this.up = false;
		this.left = false;
		this.backward = false;
		this.down = false;
	}
}
