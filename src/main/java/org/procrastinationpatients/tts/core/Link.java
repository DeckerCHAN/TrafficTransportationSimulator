package org.procrastinationpatients.tts.core;

import javafx.scene.canvas.GraphicsContext;

import java.util.Collection;

/**
 * @Author Decker & his father -- Jeffrey
 */
public class Link extends VisualEntity implements Container {

	private Vehicle[][] line;
	private int line_Length;

	private final Cross containerA;
	private final Cross containerB;

	protected Link(Cross A,Cross B) {
		this.containerA=A;
		this.containerB=B;
		//TODO:通过两个Connection计算数组拥有的AvailablePoint数量
		int num=new Integer("");
		line = new Vehicle[4][num];
		this.line_Length = num;
	}

	@Override
	public Collection<Vehicle> getVehicles() {
		return null;
	}

	@Override
	public void addVehicles(Vehicle vehicle) {

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
	public int getLineLength() {
		return line_Length;
	}

	public boolean changeToNextContainer(){
		return false ;
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

	@Override
	public Container changeToNextContainer(Vehicle vehicle) {
		return null;
	}

	@Override
	public Vehicle getNextVehicle(Vehicle vehicle) {
		return null;
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

	public Cross getContainerA() {
		return containerA;
	}

	public Cross getContainerB() {
		return containerB;
	}
}
