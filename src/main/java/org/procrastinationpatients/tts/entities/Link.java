package org.procrastinationpatients.tts.entities;

/**
 * Created by decker on 15-1-28.
 */
public abstract class Link extends IdentifiableObject implements Visible {

    private Lane [] lanes;

    public Link(Integer id) {
        super(id);
    }
}
