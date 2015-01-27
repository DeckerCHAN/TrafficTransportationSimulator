package org.procrastinationpatients.tts.core;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.procrastinationpatients.tts.utils.DrawUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * @Author Decker & his father -- Jeffrey
 */

public class Margin implements Container, Dot, VisualEntity, Connectible {

    private final Integer id;
    private Point2D position;
    private Link connectionLink;

	private int lineLength ;

	//每条线路用一个数组表示
	private Vehicle[][] line ;
	//所有Margin内的车的集合
	private LinkedList<Vehicle> vehicles ;

    public Margin(Integer marginID, Point2D position) {
        this.position = position;
        this.id = marginID;
    }

	@Override
	public LinkedList<Vehicle> getVehicles() {
		return this.vehicles;
	}

	//将汽车加入到Margin对象中
	@Override
	public boolean addVehicle(Vehicle vehicle) {

			int cur_line = vehicle.getCur_line() ;
			for(int j = 0 ; j < lineLength ; j++){
				if(line[cur_line][j] == null){
					vehicle.setCur_Loc(j);
					this.vehicles.add(vehicle);
					this.line[cur_line][j] = vehicle ;
					return true ;
				}
			}
			return false ;
	}

	@Override
	public int getSafetyDistanceByID(int whichLine, int index) {
		return 0;
	}


    @Override
    public void drawStaticGraphic(GraphicsContext gc) {
        DrawUtils.drawBallAtCoordinate(gc, this.getPosition(), 30, Color.BLUE);

    }

	@Override
	public void drawDynamicGraphic(GraphicsContext gc) {

	}

	@Override
    public int getLineLength() {
        return this.lineLength;
    }

	@Override
	public void setLineLength(int length) { this.lineLength = length ; }

	@Override
    public int changeLine(Vehicle vehicle) {
        return 0;
    }

    @Override
    public boolean canChangeLine(Vehicle vehicle) {
        return false;
    }

	@Override
	public Container changeToNextContainer(Vehicle vehicle) {
		return null;
	}

	@Override
	public Vehicle getNextVehicle(Vehicle vehicle) {
		return null;
	}

	@Override
    public Point2D getPosition() {
        return this.position;
    }

    public Integer getId() {
        return id;
    }

    public Link getConnectionLink() {
        return connectionLink;
    }

    public void setConnectionLink(Link connectionLink) {
        this.connectionLink = connectionLink;
    }

    @Override
    public Collection<Connectible> getConnections() {
        ArrayList<Connectible> arrayList = new ArrayList<>();
        arrayList.add(this.connectionLink);
        return arrayList;
    }

    @Override
    public void addConnection(Connectible connection) {
        this.connectionLink = (Link) connection;
    }

	public void setLine(Vehicle[][] line) { this.line = line; }

	public Vehicle[][] getLine() { return this.line ; }
}
