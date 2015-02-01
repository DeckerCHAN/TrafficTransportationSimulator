package org.procrastinationpatients.tts.entities;

/**
 * Created by decker on 15-1-28.
 */
public abstract class IdentifiableObject extends Object {
    private final int id;

    public IdentifiableObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}
