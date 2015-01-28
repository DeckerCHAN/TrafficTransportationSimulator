package org.procrastinationpatients.tts.entities;

/**
 * Created by decker on 15-1-28.
 */
public abstract class Link extends IdentifiableObject implements Visible {

    protected Dot a;
    protected Dot b;
    protected Lane [] lanes;

    public Link(Integer id) {
        super(id);
    }
}
