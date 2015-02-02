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
    private Lane [] lanes;
	private TrafficLight[] trafficlights;

    public Link(Integer id) {
        super(id);
        this.lanes=new Lane[6];
		for (int i = 0; i < 6; i++) {
			lanes[i] = new Lane(this,i);
		}
		this.trafficlights = new TrafficLight[2];
    }

	public TrafficLight[] getTrafficlights(){return this.trafficlights;}

//	public void setTrafficlights(){
//		if(inputs.size() == 0) {
//			trafficlights[0] = new TrafficLight(false);
//		}
//		else {
//			trafficlights[0] = new TrafficLight(true);
//			trafficlights[0].setLight(0);
//			trafficlights[0].setPosition(0);
//		}
//		if(outputs.size() == 0) {
//			trafficlights[1] = new TrafficLight(false);
//		}
//		else {
//			trafficlights[1] = new TrafficLight(true);
//			trafficlights[1].setLight(0);
//			trafficlights[0].setPosition(this.Length);
//		}
//	}

    public Lane[] getLanes() {
        return lanes;
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

	@Override
	public boolean canChangeLine(Vehicle vehicle) {
		int v_line = vehicle.getCur_line();
		int v_location = vehicle.getCur_Loc();
		int v_speed = vehicle.getCur_Spd();
		boolean flag = false;

		v_line = change_Line_NUMBER(v_line);
		for (int i = v_location ; i <= v_location + v_speed; i++) {
			if (lanes[v_line].getVehicles()[i] == null) {
				flag = true;
				break;
			}
		}
		return flag;
	}

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

		this.lanes[v_line].removeVehicle(vehicle) ;
		v_line = change_Line_NUMBER(v_line);
		vehicle.setCur_Loc(v_location);
		vehicle.setCur_line(v_line);
		vehicle.setOn_Link(this.lanes[v_line]);
		this.lanes[v_line].addChangeLineVehicle(vehicle);

		return v_line;
	}

	@Override
	public void changeToNextContainer(Vehicle vehicle) {
		this.removeVehicle(vehicle);
		List<Lane> outputLanes = lanes[vehicle.getCur_line()].getOutputs();
		Lane outputLane = outputLanes.get(RandomUtils.getStartLine()) ;
		vehicle.setCur_Loc(vehicle.getCur_Loc()+vehicle.getCur_Spd()-this.getLane_Length());
		outputLane.addVehicle(vehicle);
	}

	@Override
	public void toGoalLine(Vehicle vehicle) {
		int v_line = vehicle.getCur_line();
		int v_goal_line = vehicle.getGoal_line() ;
		int v_location = vehicle.getCur_Loc();

		if(!hasVehicle(v_goal_line,v_location)){
			lanes[v_line].removeVehicle(vehicle) ;
			vehicle.setCur_line(v_goal_line);
			vehicle.setOn_Link(lanes[v_goal_line]);
			lanes[v_goal_line].addChangeLineVehicle(vehicle) ;
		}
	}

	public void removeVehicle(Vehicle vehicle) {
		int v_line = vehicle.getCur_line();
		int v_location = vehicle.getCur_Loc();

		if(lanes[v_line].getVehicles()[v_location] != null){
			this.lanes[v_line].removeVehicle(vehicle) ;
		}
	}

	public boolean addVehicle(Vehicle vehicle) {
		int cur_line = vehicle.getCur_line();
		for (int j = 0; j < lane_Length; j++) {
			if (lanes[cur_line].getVehicles()[j] == null) {
				vehicle.setCur_Loc(j);
				this.lanes[cur_line].addVehicle(vehicle);
				return true;
			}
		}
		return false;
	}

	public boolean hasVehicle(int line , int loc){
		if(this.lanes[line].getVehicles()[loc] != null)
			return true ;
		else
			return false ;
	}

	@Override
	public int getLane_Length() {
		return this.lane_Length;
	}

	@Override
	public void drawDynamicGraphic(GraphicsContext gc) {
		//统计车的数量
		Integer pointCount = 0;
		for (Lane lane : this.getLanes())
		{
			for (int i = 0; i < lane.getLength(); i++)
			{
				if (lane.getVehicles()[i] != null) {
					if (lane.getVehiclePositions()[i] == null)
					{
						continue;
					}
					if (StaticConfig.DEBUG_MODE) {
						DrawUtils.drawText(gc, lane.getVehiclePositions()[i].getX(), lane.getVehiclePositions()[i].getY() - 11D, Color.RED, String.format("%s(%s,%s)", lane.getVehicles()[i].getId(), (int) lane.getVehiclePositions()[i].getX(), (int) lane.getVehiclePositions()[i].getY()), 10D);
					}
					DrawUtils.drawBallAtCoordinate(gc, lane.getVehiclePositions()[i], 4, Color.RED);
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
