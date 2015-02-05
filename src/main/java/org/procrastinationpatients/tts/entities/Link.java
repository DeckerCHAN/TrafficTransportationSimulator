package org.procrastinationpatients.tts.entities;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.procrastinationpatients.tts.source.StaticConfig;
import org.procrastinationpatients.tts.utils.DrawUtils;
import org.procrastinationpatients.tts.utils.RandomUtils;

import java.util.List;


public abstract class Link extends IdentifiableObject implements Visible, FunctionalObject {

	private Dot a;
	private Dot b;
	private int lane_Length;
	private Lane[] lanes;

	public Link(Integer id) {
		super(id);
		this.lanes = new Lane[6];
		for (int i = 0; i < 6; i++) {
			lanes[i] = new Lane(this,i);
		}
	}

	public Lane[] getLanes() {
		return lanes;
	}

	public int getLane_Length(){
		return this.lane_Length;
	}

	public void setLanes(Lane[] lanes) {
		this.lanes = lanes;
		if(lanes.length > 0)
			this.lane_Length = lanes[0].getLength() ;
		else
			this.lane_Length = 0 ;
	}

	public Dot getA() {
		return a;
	}

	public void setA(Dot a) {
		this.a = a;
		refreshLane();
	}

	public Dot getB() {
		return b;
	}

	public void setB(Dot b) {
		this.b = b;
		refreshLane();
	}

	protected abstract void refreshLane();

	public int change_Line_NUMBER(int v_line) {
		switch (v_line) {
			case 0:
				v_line = 1;
				break;
			case 1:
				v_line = 2;
				break;
			case 2:
				v_line = 2;
				break;
			case 3:
				v_line = 3;
				break;
			case 4:
				v_line = 3;
				break;
			case 5:
				v_line = 4;
		}
		return v_line;
	}

	@Override
	public int changeLine(Vehicle vehicle) {
		int v_line = vehicle.getCur_line();
		int v_location = vehicle.getCur_Loc();
		int new_line = change_Line_NUMBER(v_line);
		if(this.lanes[new_line].getVehicles()[v_location] == null){
			this.lanes[v_line].removeVehicle(vehicle) ;
			vehicle.setCur_Loc(v_location);
			vehicle.setCur_line(new_line);
			vehicle.setOn_Link(this.lanes[new_line]);
			this.lanes[new_line].addChangeLineVehicle(vehicle);
			return new_line;
		}
		return 0;
	}

	@Override
	public void toGoalLine(Vehicle vehicle) {
		int v_line = vehicle.getCur_line();
		int v_goal_line = vehicle.getGoal_line() ;
		int v_location = vehicle.getCur_Loc();

		if (!hasVehicle(v_goal_line, v_location)) {
			lanes[v_line].removeVehicle(vehicle) ;
			vehicle.setCur_line(v_goal_line);
			vehicle.setOn_Link(lanes[v_goal_line]);
			lanes[v_goal_line].addChangeLineVehicle(vehicle) ;
		}
	}

	public boolean hasVehicle(int line , int loc){
		if(this.lanes[line].getVehicles()[loc] != null)
			return true ;
		else
			return false ;
	}

	@Override
	public void drawDynamicGraphic(GraphicsContext gc) {
		//统计车的数量
		Integer pointCount = 0;
		for (Lane lane : this.getLanes())
		{
			for (int i = 0; i < lane.getLength(); i++)
			{
				for (Barrier barrier : lane.getBarriers()) {
					if (i > barrier.getStart() && i < barrier.getEnd()) {
						DrawUtils.drawBarrier(gc, lane.getVehiclePositions()[i], 8, Color.BLACK);
					}

				}

				if (lane.getVehicles()[i] != null) {
					if (lane.getVehiclePositions()[i] == null)
					{
						continue;
					}
					if (StaticConfig.DEBUG_MODE) {
						DrawUtils.drawText(gc, lane.getVehiclePositions()[i].getX(), lane.getVehiclePositions()[i].getY() - 11D, Color.RED, String.format("%s(%s,%s)", lane.getVehicles()[i].getId(), (int) lane.getVehiclePositions()[i].getX(), (int) lane.getVehiclePositions()[i].getY()), 10D);
					}
					DrawUtils.drawBallAtCoordinate(gc, lane.getVehiclePositions()[i], 4, Color.RED);
					System.out.println("Drawing:::+++++++" + lane.getVehicles()[i].getId() + "-->"+ lane.getVehicles()[i].getCur_Loc());
					pointCount++;
				}
			}
		}

		//在Debug模式下输出车的数量
		if (StaticConfig.DEBUG_MODE) {
			DrawUtils.drawText(gc, new Point2D(
							(this.getA().getPosition().getX() + this.getB().getPosition().getX()) / 2,
							(this.getA().getPosition().getY() + this.getB().getPosition().getY()) / 2
					), Color.GREEN, String.format("Drew:%s", pointCount), 11D
			);
		}
	}
}
