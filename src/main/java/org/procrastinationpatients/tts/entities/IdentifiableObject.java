package org.procrastinationpatients.tts.entities;


public abstract class IdentifiableObject extends Object {
    private final int id;

    public IdentifiableObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
