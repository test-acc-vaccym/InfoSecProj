package com.infosec.gesturedata;
import java.util.ArrayList;

public class GestureData {

	private ArrayList<AccelEvent> data;
	private Point finalPosition;

	public GestureData(ArrayList<AccelEvent> data) {
		this.data = data;
	}
	

	/* Returns a point that represents the final ending 
	 * position after all data is parsed
	 */
	public Point getPosition() {
		return this.finalPosition;
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
	private Point calcAverageOfInterval(long start, long end) {
		Point p = new Point();

		// TODO

		return p;
	}



}
