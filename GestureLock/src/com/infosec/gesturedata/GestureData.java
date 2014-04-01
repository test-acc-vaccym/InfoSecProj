package com.infosec.gesturedata;
import java.util.ArrayList;

public class GestureData {

	private ArrayList<AccelEvent> data;
	private Point finalPosition;

	public GestureData(ArrayList<AccelEvent> data) {
		this.data = data;
		normalizeData();
	}

	/* Returns a point that represents the final ending 
	 * position after all data is parsed
	 */
	public Point getPosition() {
		return this.finalPosition;
	}
	
	/* Normalizes the data arraylist by setting the timestamp
	 * of the first element to 0.  Then, the new timestamp for each
	 * following element will be the event's original timestamp less
	 * first elements original timestamp
	 */
	private void normalizeData() {
		long base = data.get(0).getTime();
		data.get(0).setTime(0);
		
		for (AccelEvent event : this.data) {
			event.setTime(event.getTime() - base);
		}
	}
	
	/* Returns an array list of points where each point is
	 * the average of one time interval
	 */
	private ArrayList<Point> getAverages() {
		ArrayList<Point> averages = new ArrayList<Point>();		
		
		// TODO
		// This method should call calcAverageOfInterval(start, end)

		return averages;
	}

	/* Returns a point that represents the averages
	 * @args start Starting time in nanoseconds
	 * @args end End of the interval in nanaseconds
	 */
	private Point calcAverageOfInterval(int start, int end) {
		Point p = new Point();
		int xSum = 0;
		int ySum = 0;
		int zSum = 0;
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
