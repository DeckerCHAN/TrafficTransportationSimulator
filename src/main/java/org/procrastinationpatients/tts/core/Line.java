package org.procrastinationpatients.tts.core;

import javafx.scene.canvas.GraphicsContext;

import java.util.Collection;

/**
 * @Author Decker & his father -- Jeffrey
 */
public class Line extends VisualEntity implements Container {

	private Vehicle[][] line;
	private int line_Length;

	public Line(int num) {
		line = new Vehicle[4][num];
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
	public int getSafetyDistanceByID(int whichLine,int index) {
		if (index < 0 || index >= this.line_Length)
			return -1;
		if (line[whichLine][index] == null)
			return 0;

		int distance = index;
		for (index++; index < this.line_Length; index++) {
			if (line[whichLine][index] != null) {
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

	@Override
	public Vehicle[][] getAllLocation() {
		return line;
	}

	@Override
	public int getLineLength() {
		return line_Length;
	}

	public boolean changeToNextContainer(){
		return false ;
	}

	@Override
	public Vehicle getNextVehichle() {
		return null;
	}

	@Override
	public int changeLine(Vehicle vehicle) {
		int v_line = vehicle.getCur_line() ;
		int v_location = vehicle.getCur_Loc() ;
		int v_speed = vehicle.getCur_Spd() ;

		v_line = change_Line_NUMBER(v_line) ;
		vehicle.setCur_Loc(v_location + v_speed) ;
		vehicle.setCur_Line(v_line) ;

		return v_line ;
	}

	@Override
	public boolean canChangeLine(Vehicle vehicle) {
		int v_line = vehicle.getCur_line() ;
		int v_location = vehicle.getCur_Loc() ;
		int v_speed = vehicle.getCur_Spd() ;
		boolean flag = true ;

		v_line = change_Line_NUMBER(v_line) ;
		for(int i = v_location + 1 ; i <= v_location + v_speed ;i++ ){
			if(line[v_line][i] != null) {
				flag = false;
				break ;
			}
		}

		return flag ;
	}

	public int change_Line_NUMBER(int v_line){
		switch(v_line){
			case 1 :
				v_line = 2 ;
				break ;
			case 2 :
				v_line = 1 ;
				break ;
			case 3 :
				v_line = 4 ;
				break ;
			case 4 :
				v_line = 3 ;
				break ;
		}
		return v_line ;
	}
}
