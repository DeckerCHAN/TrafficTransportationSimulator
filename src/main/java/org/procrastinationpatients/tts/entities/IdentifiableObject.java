package org.procrastinationpatients.tts.entities;

/**
 * Created by decker on 15-1-28.
 */
public abstract class IdentifiableObject extends Object {
    private final Integer id;

    public IdentifiableObject(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
