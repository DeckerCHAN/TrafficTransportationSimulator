package org.procrastinationpatients.tts.entities;

/**
 * Created by decker on 15-1-28.
 */
public abstract class IdentifiableObject extends Object {
    private final String id;

    public IdentifiableObject(String id) {
        this.id = id;
    }

    public IdentifiableObject(Integer id) {
        this.id = String.valueOf(id);
    }

    public String getId() {
        return id;
    }

}
