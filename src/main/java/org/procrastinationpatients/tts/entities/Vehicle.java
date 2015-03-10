package org.procrastinationpatients.tts.entities;

import org.procrastinationpatients.tts.source.StaticConfig;
import org.procrastinationpatients.tts.utils.RandomUtils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class Vehicle {

	private int id ; 		  // ID
	private int Cur_Spd;      //当前速度
	private int Cur_Loc;      //当前位置
	private int Cur_line;     //当前线路
	private int goal_line;   //目标线路
	private int MAX_Speed;    //最大速度
	private Lane on_Link;    //当前所在的Lane

	private int inputNum;
	private int outputNum;

	private double start_TIME;
	private double end_TIME;

	private boolean isStop = false; 	//障碍物

	//寻路
	private int pathIndex = 0;
	private List<Integer> path;
	private List<FunctionalObject> visited;
	private FunctionalObject finalCross;

	public Vehicle(Lane lane){
		this.on_Link = lane ;
		this.visited = new LinkedList<>();
		this.path = new LinkedList<>();
	}

	public void setSpeed(int Cur_Sped , int MAX_Speed){
		this.Cur_Spd = Cur_Sped ;
		this.MAX_Speed = MAX_Speed ;
	}

	//速度变化规则
	public int Speed_From_VDR() {
		if (this.Cur_Spd < this.MAX_Speed) {
			this.Cur_Spd++;
		}

		int safety_distance = on_Link.getSafetyDistanceByID(this.Cur_Loc);
		this.Cur_Spd = (safety_distance < this.Cur_Spd) ? (safety_distance) : (this.Cur_Spd);
		if(this.Cur_Spd < 0){
			this.Cur_Spd = 0 ;
		}
		return this.Cur_Spd;
	}

	//终于开始能动了啦
	public void move_Next_Location() {

		FunctionalObject fo = on_Link.getParent() ;
		if(fo instanceof Link){

			//换路
			if (this.Cur_Spd + this.Cur_Loc >= on_Link.getLength()) {
				this.checkRoad();
				on_Link.changeToNextContainer(this);
				this.updateGoalLine();
				return ;
			}

			//安全距离内检查
			if(this.Cur_Spd + this.Cur_Loc + StaticConfig.CHECK_POSITI0N >= on_Link.getLength()){
				if(this.goal_line != -1)
					this.checkRoad();
			}else{
				Vehicle nextVehicle = on_Link.getNextVehicle(this);
				if(nextVehicle != null) {
					if (this.Cur_line != 0 && this.Cur_line != 5) {
						if (this.Cur_Spd > nextVehicle.getCur_Spd() && on_Link.getSafetyDistanceByID(this.Cur_Loc) < StaticConfig.SAFETY_LENGTH) {
							fo.changeLine(this);
						}
					}
				}
				if(this.Cur_Spd == 0){
					fo.changeLine(this) ;
				}
			}

			//直行
			on_Link.updateVehicle(this);
		}else if(fo instanceof Cross){
			if (this.Cur_Spd + this.Cur_Loc >= on_Link.getLength()) {
				this.checkRoad();
				on_Link.changeToNextContainer(this);
				this.updateGoalLine();
			}
			on_Link.updateVehicle(this);
			return;
		}
	}

	public void updateGoalLine(){
		int line = this.getNextGoalLane();
		if(this.getCur_line() > 2)
			line = line + 3;

		this.setGoal_line(line);
	}

	public boolean isOnRoad(){

		if(this.Cur_line == this.goal_line)
			return true ;
		else
			return false ;
	}

	public void checkRoad(){
		if(!isOnRoad())
			if(on_Link != null)
				on_Link.getParent().toGoalLine(this);
	}

	public int getInputNum() {
		return inputNum;
	}

	public int getOutputNum() {
		return outputNum;
	}

	public int getId() { return id; }

	public void setId(int id) { this.id = id; }

	public int getCur_Spd() { return Cur_Spd; }

	public void setCur_Spd(int cur_Spd) { Cur_Spd = cur_Spd; }

	public int getCur_Loc() { return Cur_Loc; }

	public void setCur_Loc(int cur_Loc) { Cur_Loc = cur_Loc; }

	public int getCur_line() { return Cur_line; }

	public void setCur_line(int cur_line) { Cur_line = cur_line; }

	public int getGoal_line() { return goal_line; }

	public void setGoal_line(int goal_line) { this.goal_line = goal_line; }

	public void setOn_Link(Lane lane){
		this.on_Link = lane ;
	}

	public Lane getOn_Link(){
		return this.on_Link ;
	}

	public boolean isStop() {
		return isStop;
	}

	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}

	public double getStart_TIME() {
		return start_TIME;
	}

	public int getMAX_Speed() { return MAX_Speed; }

	public void setStart_TIME(double start_TIME) {
		this.start_TIME = start_TIME;
	}

	public double getEnd_TIME() {
		return end_TIME;
	}

	public void setEnd_TIME(double end_TIME) {
		this.end_TIME = end_TIME;
	}

	//没事就没看这段了
	public void findPath(Margin[] margins,int inputNum , int outputNum){
		int length = margins.length;
		Margin input, output;

		if(outputNum == -1){

			int index = inputNum % length ;
			int mapIndex = length-index-1;

			if(mapIndex == index){
				mapIndex++;
			}
			input = margins[index] ;
			output = margins[mapIndex];
		}else{
			input = margins[inputNum];
			output = margins[outputNum];
		}

		if(output.getFirstInputLaneIndex() == 0)
			finalCross = output.getConnectedLink().getLanes()[0].getOutputs().get(0).getParent();
		else
			finalCross = output.getConnectedLink().getLanes()[3].getOutputs().get(0).getParent();

		if(input.getFirstInputLaneIndex() == 0 )
			DFS(transferToLaneArrays(input.getConnectedLink().getLanes()[0]));
		else
			DFS(transferToLaneArrays(input.getConnectedLink().getLanes()[3]));

		Collections.reverse(path);

		int firstLine = path.get(pathIndex);

		if(pathIndex + 1 < path.size())
			pathIndex++;

		Lane lane;
		if(input.getFirstInputLaneIndex() == 0){
			lane = input.getConnectedLink().getLanes()[firstLine];
		}else{
			lane = input.getConnectedLink().getLanes()[firstLine + 3];
			firstLine = 5 - firstLine ;
		}
		this.Cur_line = firstLine;
		this.goal_line = firstLine;
		this.on_Link = lane;
		this.inputNum = input.getId();
		this.outputNum = output.getId();
		lane.addVehicle(this);
	}

	//深度优先搜索
	public int DFS(Lane[] outputs){

		if(outputs[0].getOutputs().size() ==0){
			return 0;
		}

		if(outputs[0].getOutputs().get(0).getParent() == finalCross){
			if(outputs[0].getOutputs().get(0).getOutputs().get(0).getOutputs().size() == 0){
				path.add(0);
				return 1;
			}
			if(outputs[1].getOutputs().get(0).getOutputs().get(0).getOutputs().size() == 0){
				path.add(1);
				return 1;
			}
			if(outputs[2].getOutputs().get(0).getOutputs().get(0).getOutputs().size() == 0){
				path.add(2);
				return 1;
			}
			return 0;
		}

		for(int i = 0 ; i < this.visited.size() ;i++){
			if(outputs[0].getOutputs().get(0).getParent() == visited.get(i)){
				return 0;
			}
		}
		visited.add(outputs[0].getOutputs().get(0).getParent());

		if(DFS(transferToLaneArrays(outputs[0].getOutputs().get(0).getOutputs().get(0))) == 1){
			path.add(0);
			return 1;
		}
		if(DFS(transferToLaneArrays(outputs[1].getOutputs().get(0).getOutputs().get(0))) == 1){
			path.add(1);
			return 1;
		}
		if(DFS(transferToLaneArrays(outputs[2].getOutputs().get(0).getOutputs().get(0))) == 1){
			path.add(2);
			return 1;
		}
		return 0;
	}

	public Lane[] transferToLaneArrays(Lane lane){
		if(lane.getParent() instanceof  Link){
			Link link = (Link) lane.getParent();
			Lane[] lanes = link.getLanes();
			if(lane.getLine() - 3 < 0)
			{
				return new Lane[]{lanes[0] , lanes[1], lanes[2]} ;
			}else{
				return new Lane[]{lanes[3] , lanes[4], lanes[5]} ;
			}
		}
		return null;
	}

	public Integer getNextGoalLane(){
		if(pathIndex < path.size()){
			if(pathIndex + 1 < path.size()){
				pathIndex++;
			}
			return path.get(pathIndex);
		}else
			return -1;
	}

}
