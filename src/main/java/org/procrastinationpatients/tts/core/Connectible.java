package org.procrastinationpatients.tts.core;

import java.util.Collection;

/**
 * @Author Decker
 */
public interface Connectible {
    public Collection<Connectible> getConnections();

    public void addConnection(Connectible connection);
}