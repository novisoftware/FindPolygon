package com.github.novisoftware.findPolygon.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 三角形のピース
 *
 */
public class Triangle {
	static private final double R = Math.sqrt(2);

	int [] xPoints;
	int [] yPoints;

	Triangle(int a) {
		int x00 = (a / 4) / Model.ARRAY_HEIGHT;
		int y00 = (a / 4) % Model.ARRAY_HEIGHT;
		int x0 = (x00 + 0) * 2 + 1;
		int y0 = (y00 + 0) * 2 + 1;

		int th0 = (a % 4);
		double th = Math.PI * 0.5 * th0;
		double x1 = - R * Math.sin(th - 0.25 * Math.PI);
		double y1 = - R * Math.cos(th - 0.25 * Math.PI);
		double x2 = - R * Math.sin(th + 0.25 * Math.PI);
		double y2 = - R * Math.cos(th + 0.25 * Math.PI);

		int[] work_xPoints  = {
				x0,
				(int) Math.round((x0 + x1) ),
				(int) Math.round((x0 + x2) )
		};

		int[] work_yPoints  = {
				y0,
				(int) Math.round( (y0 + y1) ),
				(int) Math.round( (y0 + y2) )
		};

		xPoints = work_xPoints;
		yPoints = work_yPoints;
	}

	static HashMap<Integer, Triangle> stock = new HashMap<Integer, Triangle>();
	public static Triangle getTriangle(int a) {
		Triangle check1 = stock.get(a);
		if (check1 != null) {
			return check1;
		}

		Triangle newOne = new Triangle(a);
		stock.put(a, new Triangle(a));
		return newOne;
	}

	public ArrayList<Line> getEdges() {
		ArrayList<Line> result = new ArrayList<Line>();

		for (int i = 0; i < 3; i++) {
			Pos a = new Pos(xPoints[i % 3], yPoints[i % 3]);
			Pos b = new Pos(xPoints[(i + 1) % 3], yPoints[(i + 1) % 3]);

			result.add(new Line(a, b));
		}

		return result;
	}

	String debugPrint() {
		StringBuilder sb = new StringBuilder();

		for(int i = 0; i < 3; i++) {
			sb.append(String.format(" (%d, %d)", xPoints[i], yPoints[i]));
		}
		return sb.toString();
	}
}