package org.procrastinationpatients.tts.entities;

/**
 * Created by decker on 15-1-28.
 */
public abstract class Link extends IdentifiableObject implements Visible, FunctionalObject {

    private Dot a;
    private Dot b;
	private int lane_Length;
    private Lane [] lanes;

    public Link(Integer id) {
        super(id);
        this.lanes=new Lane[6];
		for (int i = 0; i < 6; i++) {
			lanes[i] = new Lane(this);
		}
    }

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
        refreshLaneLength();
    }

	public Dot getB() {
        return b;
    }

	public void setB(Dot b) {
        this.b = b;
        refreshLaneLength();
    }

    private void refreshLaneLength()
    {
        //TODO：计算长度
//        throw new UnsupportedOperationException("还未实现");
//        for (Lane l :this.lanes)
//        {
//            l.setLength(-1);
//        }
    }

	@Override
	public int getSafetyDistanceByID(int whichLine, int index) {
		if (index < 0 || index >= this.lanes[0].getLength())
			return -1;
		if (lanes[whichLine].getVehicles()[index] == null)
			return 0;

		for (int i = index + 1; i < this.lane_Length; i++) {
			if (lanes[whichLine].getVehicles()[index] != null) {
				return i - index ;
			}
		}

		return lane_Length - index ;
	}

	@Override
	public Vehicle getNextVehicle(Vehicle vehicle) {
		int v_line = vehicle.getCur_line();
		int v_location = vehicle.getCur_Loc();

		for (int i = v_location + 1; i < this.lane_Length; i++) {
			if (lanes[v_line].getVehicles()[i] != null)
				return lanes[v_line].getVehicles()[i];
		}
		return null;
	}

	@Override
	public boolean canChangeLine(Vehicle vehicle) {
		int v_line = vehicle.getCur_line();
		int v_location = vehicle.getCur_Loc();
		int v_speed = vehicle.getCur_Spd();
		boolean flag = true;

		v_line = change_Line_NUMBER(v_line);
		for (int i = v_location + 1; i <= v_location + v_speed; i++) {
			if (lanes[v_line].getVehicles()[i] != null) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	public int change_Line_NUMBER(int v_line) {
		switch (v_line) {
			case 1:
				v_line = 2;
				break;
			case 2:
				v_line = 3;
				break;
			case 3:
				v_line = 3;
				break;
			case 4:
				v_line = 4;
				break;
			case 5:
				v_line = 4;
				break;
			case 6:
				v_line = 5;
		}
		return v_line;
	}

	@Override
	public int changeLine(Vehicle vehicle) {
		int v_line = vehicle.getCur_line();
		int v_location = vehicle.getCur_Loc();
		int v_speed = vehicle.getCur_Spd();

		v_line = change_Line_NUMBER(v_line);
		vehicle.setCur_Loc(v_location + v_speed);
		vehicle.setCur_line(v_line);

		return v_line;
	}

	@Override
	public boolean changeToNextContainer(Vehicle vehicle) {
		return false;
	}

	@Override
	public void toGoalLine(Vehicle vehicle) {
		int v_line = vehicle.getGoal_line();
		int v_location = vehicle.getCur_Loc();

		if(!hasVehicle(v_line,v_location)){
			lanes[vehicle.getCur_line()].getVehicles()[v_location] = null ;
			lanes[v_line].getVehicles()[v_location] = vehicle ;
		}
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
}
