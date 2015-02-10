package org.procrastinationpatients.tts.entities;


public class Barrier extends IdentifiableObject {

	private int start ;
	private int length ;

    public Barrier(int id, int start, int length) {
        super(id);
        this.start = start;
        this.length = length ;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return start + length;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
