package com.infosec.gesturedata;
import java.util.ArrayList;

public class GestureData {

	private ArrayList<AccelEvent> data;
	
	
	private Point finalPosition;
	private long width = 100000000;


	static final float ALPHA = 0.20f;

	public GestureData(ArrayList<AccelEvent> events) {
		this.data = lowPassFilter(events);
		normalizeData();
		calculatePosition();
	}

	/* Returns a point that represents the final ending 
	 * position after all data is parsed
	 */
	public Point getPosition() {
		return this.finalPosition;
	}

	/*
	 * 
	 */
	private ArrayList<AccelEvent> lowPassFilter(ArrayList<AccelEvent>input) {

		if (input == null){
			return new ArrayList<AccelEvent>();
		}

		ArrayList<AccelEvent> output = new ArrayList<AccelEvent>();
		output.add(input.get(0));

		for (int i = 1; i < input.size();i++) {
			AccelEvent current = input.get(i);
			AccelEvent previous = output.get(i-1);
			AccelEvent filtered = new AccelEvent();

			filtered.setX(current.getX()+ ALPHA * (current.getX() - previous.getX()));
			filtered.setY(current.getY()+ ALPHA * (current.getY() - previous.getY()));
			filtered.setZ(current.getZ()+ ALPHA * (current.getZ() - previous.getZ()));
			filtered.setTime(current.getTime());

			output.add(filtered);
		}

		return output;
	}

	/* Normalizes the data arraylist by setting the timestamp
	 * of the first element to 0.  Then, the new timestamp for each
	 * following element will be the event's original timestamp less
	 * first elements original timestamp
	 */
	private void normalizeData() {
		long base = data.get(0).getTime();

		for (AccelEvent event : this.data) {
			event.setTime(event.getTime() - base);
		}
	}

	/* Using the averages of the intervals, calculates the final
	 * ending point and stores it in this.finalPosition
	 */
	private void calculatePosition() {
		ArrayList<Point> averages = getAverages();
		float widthInSec = this.width/1000000000.0f;
		float vx, vy, vz;
		float dx, dy, dz;
		vx = vy = vz = dx = dy = dz = 0;
		
		for (int i =1; i < averages.size(); i++) {
			vx += averages.get(i-1).x + averages.get(i).x * widthInSec;
			vy += averages.get(i-1).y + averages.get(i).y * widthInSec;
			vz += averages.get(i-1).z + averages.get(i).z * widthInSec;

			dx += vx * widthInSec;
			dy += vy * widthInSec;
			dz += vz * widthInSec;
		}
			this.finalPosition = new Point(dx, dy, dz);
		
	}

	/* Returns an array list of points where each point is
	 * the average of one time interval
	 */
	private ArrayList<Point> getAverages()	{	
		ArrayList<Point> averages = new ArrayList<Point>();
		long startTime = 0;
		int start = 0;
		int end = 1;
		
		// This method should call calcAverageOfInterval(start, end)
		for(AccelEvent event:data) {
			if ((event.getTime() >= startTime) && (event.getTime() < startTime + this.width)) {
				end ++;
			} else {
				averages.add(calcAverageOfInterval(start, end));
				startTime +=this.width;
				start = end;
				end++;
			}
		}

		if (start == end ) {
			averages.add(new Point(
					data.get(data.size()-1).getX(), 
					data.get(data.size()-1).getY(), 
					data.get(data.size()-1).getZ()));
		}
		return averages;
	}

	/* Returns a point that represents the averages
	 * @args start Starting time in nanoseconds
	 * @args end End of the interval in nanoseconds
	 */
	private Point calcAverageOfInterval(int start, int end) {
		Point p = new Point();
		float xSum = 0;
		float ySum = 0;
		float zSum = 0;
		int numOfPoints = 0;

		for (int i = start; i < end; i++) {
			AccelEvent event = data.get(i);
			xSum += event.getX();
			ySum += event.getY();
			zSum += event.getZ();
			numOfPoints++;	
		}
		p.set(xSum/numOfPoints, ySum/numOfPoints, zSum/numOfPoints);
		return p;
	}
}
