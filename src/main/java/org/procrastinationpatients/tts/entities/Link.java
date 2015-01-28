package org.procrastinationpatients.tts.entities;

/**
 * Created by decker on 15-1-28.
 */
public abstract class Link extends IdentifiableObject implements Visible {

    private Dot a;
    private Dot b;
    private Lane [] lanes;

    public Link(Integer id) {
        super(id);
        this.lanes=new Lane[6];
        for (Lane l:lanes)
        {
            l=new Lane();
        }
    }

    public Lane[] getLanes() {
        return lanes;
    }

    public void setLanes(Lane[] lanes) {
        this.lanes = lanes;
    }

    protected Dot getA() {
        return a;
    }

    protected void setA(Dot a) {
        this.a = a;
        refreshLaneLength();
    }

    protected Dot getB() {
        return b;
    }

    protected void setB(Dot b) {
        this.b = b;
        refreshLaneLength();
    }

    private void refreshLaneLength()
    {
        //TODO：计算长度
        throw new UnsupportedOperationException("还未实现");
//        for (Lane l :this.lanes)
//        {
//            l.setLength(-1);
//        }
    }
}
