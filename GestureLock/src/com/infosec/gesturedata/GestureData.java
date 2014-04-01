package com.infosec.gesturedata;
import java.util.ArrayList;

public class GestureData {

	private ArrayList<AccelEvent> data;
	private Point finalPosition;

	static final float ALPHA = 0.20f;

	public GestureData(ArrayList<AccelEvent> data) {
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
	private Point calcAverageOfInterval(Integer start, Integer end) {
		Point p = new Point();

		// TODO

		return p;
	}



}
