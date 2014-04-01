package com.infosec.gesturedata;
import java.util.ArrayList;

public class GestureData {

	private ArrayList<AccelEvent> data;
	private Point finalPosition;
	private Long lastEventTime;
	private Long currentTime;
	
	static final float ALPHA = 0.20f;
	
	public GestureData(ArrayList<AccelEvent> data) {
		this.data = lowPassFilter(data);
	}


	private ArrayList<AccelEvent> lowPassFilter(ArrayList<AccelEvent>input) {

		ArrayList<AccelEvent> output = new ArrayList<AccelEvent>();
		
		for (int i = 1; i < input.size();i++) {
			AccelEvent current = input.get(i);
			AccelEvent previous = input.get(i-1);
			output.add(new AccelEvent(
					current.getX()+ ALPHA * (current.getX() - previous.getX()),
					current.getY()+ ALPHA * (current.getY() - previous.getY()),
					current.getZ()+ ALPHA * (current.getZ() - previous.getZ())
					));
		}

		return output;
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
		Long width = new Long(100000000);
		// TODO
		// This method should call calcAverageOfInterval(start, end)
		for(AccelEvent event:data) {

		}
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
