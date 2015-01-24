package org.procrastinationpatients.tts.core;

import javafx.scene.canvas.GraphicsContext;

import java.util.Collection;

/**
 * @Author Decker & his father -- Jeffrey
 */
public class Line extends VisualEntity implements Container {

	private Vehicle[] line;
	private int line_Length;

	public Line(int num) {
		line = new Vehicle[num];
		this.line_Length = num;
	}

	@Override
	public Collection<Vehicle> getVehicles() {
		return null;
	}

	@Override
	public void setVehicles() {

	}

	//通过索引位置,得到与下一辆车之间的安全距离
	@Override
	public int getSafetyDistanceByID(int index) {
		if (index < 0 || index >= this.line_Length)
			return -1;
		if (line[index] == null)
			return 0;

		int distance = index;
		for (index++; index < this.line_Length; index++) {
			if (line[index] != null) {
				break;
			}
		}
		if (index == this.line_Length) {
			index--;
		}
		return index - distance;
	}



    @Override
    public void drawStaticGraphic(GraphicsContext gc) {

    }

    @Override
    public void drawDynamicGraphic(GraphicsContext gc) {

	}
}
