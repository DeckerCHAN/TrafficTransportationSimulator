package org.procrastinationpatients.tts.entities;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.procrastinationpatients.tts.utils.DrawUtils;

/**
 * 东西走向
 * Created by decker on 15-1-28.
 */
public class Street extends Link {



    public Street(Integer id) {
        super(id);
    }

    @Override
    protected void refreshLaneLength() {

    }

    @Override
    public void drawStaticGraphic(GraphicsContext gc) {
        Point2D westDotPosition = this.getWestDot().getPosition();
        Point2D eastDotPosition = this.getEastDot().getPosition();

        Point2D[] a = new Point2D[7];
        Point2D[] b = new Point2D[7];

        a[0] = new Point2D(westDotPosition.getX() + 60D, westDotPosition.getY() - 30D);
        a[1] = new Point2D(westDotPosition.getX() + 60D, westDotPosition.getY() - 20D);
        a[2] = new Point2D(westDotPosition.getX() + 60D, westDotPosition.getY() - 10D);
        a[3] = new Point2D(westDotPosition.getX() + 60D, westDotPosition.getY() - 0D);
        a[4] = new Point2D(westDotPosition.getX() + 60D, westDotPosition.getY() + 10D);
        a[5] = new Point2D(westDotPosition.getX() + 60D, westDotPosition.getY() + 20D);
        a[6] = new Point2D(westDotPosition.getX() + 60D, westDotPosition.getY() + 30D);

        b[0] = new Point2D(eastDotPosition.getX() -60D, eastDotPosition.getY() - 30D);
        b[1] = new Point2D(eastDotPosition.getX() - 60D, eastDotPosition.getY() - 20D);
        b[2] = new Point2D(eastDotPosition.getX() - 60D, eastDotPosition.getY() - 10D);
        b[3] = new Point2D(eastDotPosition.getX() - 60D, eastDotPosition.getY() - 0D);
        b[4] = new Point2D(eastDotPosition.getX() - 60D, eastDotPosition.getY() + 10D);
        b[5] = new Point2D(eastDotPosition.getX() - 60D, eastDotPosition.getY() + 20D);
        b[6] = new Point2D(eastDotPosition.getX() - 60D, eastDotPosition.getY() + 30D);

        DrawUtils.drawLine(gc, a[0], a[6], Color.BROWN, 5);
        DrawUtils.drawLine(gc, b[0], b[6], Color.BROWN, 5);

        for (int i = 0; i < 7; i++) {
            DrawUtils.drawLine(gc, a[i], b[i], Color.BLACK, 2);
        }

        DrawUtils.drawText(gc, new Point2D(a[0].getX(), a[0].getY() - 40D), Color.BLUE, "A" + this.getId());
        DrawUtils.drawText(gc, new Point2D(b[0].getX(), b[0].getY() - 40D), Color.GREEN, "B" + this.getId());
        return;
    }

    @Override
    public void drawDynamicGraphic(GraphicsContext gc) {

    }

    public Dot getEastDot() {
        return this.getB();
    }

    public void setEastDot(Dot eastDot) {
        this.setB(eastDot);
    }

    public Dot getWestDot() {
        return this.getA();
    }

    public void setWestDot(Dot westDot) {
        this.setA(westDot);
    }
}
