package org.procrastinationpatients.tts.entities;


public class Barrier {

	int activityZoneLength ;
	int transitionZoneLength ;

	int workZoneStart ;
	int activityZoneStart ;

	public Barrier(int activityZoneLength, int transitionZoneLength, int workZoneStart , int activityZoneStart){
		this.activityZoneLength = activityZoneLength;
		this.transitionZoneLength = transitionZoneLength ;
		this.workZoneStart = workZoneStart ;
		this.activityZoneStart = activityZoneStart ;
	}

	public void setActivityZoneLength(int activityZoneLength){ this.activityZoneLength = activityZoneLength ;}
	public void setTransitionZoneLength(int transitionZoneLength){ this.transitionZoneLength = transitionZoneLength ;}

}
