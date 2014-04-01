package com.infosec.gesturedata;
import java.util.ArrayList;

public class GestureData {

	private ArrayList<AccelEvent> data;
	private Point finalPosition;

	static final float ALPHA = 0.20f;

	public GestureData(ArrayList<AccelEvent> data) {
		this.data = data;
		normalizeData();
		this.data = lowPassFilter(data);
	}


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
	private ArrayList<Point> getAverages()	{	
		ArrayList<Point> averages = new ArrayList<Point>();
		long startTime = this.data.get(0).getTime();
		long width = 100000000;
		int start = 0;
		int end = 0;
		// TODO
		// This method should call calcAverageOfInterval(start, end)
		for(AccelEvent event:data) {
			if ((event.getTime() >= startTime) && (event.getTime() < startTime + width)) {
				end ++;
			} else {
				averages.add(calcAverageOfInterval(start, end));
				start = end + 1; 
			}
			if (start == end + 1) {
				averages.add(new Point(event.getX(), event.getY(), event.getZ()));
			}
		}
		return averages;
	}

	/* Returns a point that represents the averages
	 * @args start Starting time in nanoseconds
	 * @args end End of the interval in nanoseconds
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
