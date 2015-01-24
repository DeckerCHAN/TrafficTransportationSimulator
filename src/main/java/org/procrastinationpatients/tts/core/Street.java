package org.procrastinationpatients.tts.core;

/**
 * 东西走向的街
 * @Author Decker
 */
public class Street extends Link {

    public Street(Cross connectionW, Cross ConnectionE) {
        super(connectionW, ConnectionE);
    }

    public Container getConnectionW()
    {
        return  super.getContainerA();
    }

    public Container getConnectionE()
    {
        return super.getContainerB();
    }

}
