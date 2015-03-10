package org.procrastinationpatients.tts.entities;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.procrastinationpatients.tts.source.StaticConfig;
import org.procrastinationpatients.tts.utils.DrawUtils;


public class Street extends Link {



    public Street(Integer id) {
        super(id);
    }

    @Override
    protected void refreshLane() {
        if(this.getA()==null||this.getB()==null)
        {
            return;
        }
        Point2D westPosition = this.getA().getPosition();
        Point2D eastPosition = this.getB().getPosition();
        Line centerLine = new Line(westPosition.getX() + 60D, westPosition.getY(), eastPosition.getX() - 60D, eastPosition.getY());
        Double d = Math.sqrt(Math.pow(Math.abs(centerLine.getStartX() - centerLine.getEndX()), 2) + Math.pow(Math.abs(centerLine.getStartY() - centerLine.getEndY()), 2)) / StaticConfig.LANE_POINT_SKIP_DISTANCE;
        Integer items = (int) Math.round(d);
        for (Lane lane : this.getLanes()) {
            lane.setLength(items);
        }
        Point2D westCenterPosition = new Point2D(westPosition.getX() + 60D, westPosition.getY());
        Point2D eastCenterPosition = new Point2D(eastPosition.getX() - 60D, eastPosition.getY());

        Double diffX = Math.abs(westCenterPosition.getX() - eastCenterPosition.getX());
        Double diffY = westCenterPosition.getY() - eastCenterPosition.getY();
        Double distance = Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));

        Double progressX = StaticConfig.LANE_POINT_SKIP_DISTANCE * (diffX / distance);
        Double progressY = StaticConfig.LANE_POINT_SKIP_DISTANCE * (diffY / distance);

        Point2D lane0StartPoint = new Point2D(westCenterPosition.getX(), westCenterPosition.getY() + 25D);
        Point2D lane3EndPoint = new Point2D(eastCenterPosition.getX(), eastCenterPosition.getY() - 5D);
        for (int i = 0; i < 3; i++) {
            Lane lane = this.getLanes()[i];
            for (int j = 0; j < lane.getLength(); j++) {
                lane.getVehiclePositions()[lane.getLength()- 1 - j] = new Point2D(lane0StartPoint.getX() + j * progressX, lane0StartPoint.getY() - j * progressY - i * 10D);
            }
        }
        for (int i = 3; i < 6; i++) {
            Lane lane = this.getLanes()[i];
            for (int j = 0; j < lane.getLength(); j++) {
                lane.getVehiclePositions()[lane.getLength()- 1 - j] = new Point2D(lane3EndPoint.getX() - j * progressX  , lane3EndPoint.getY() + j * progressY-(i - 3) * 10D);
            }
        }
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

        for (int i = 0; i < 7; i++) {
            DrawUtils.drawLine(gc, a[i], b[i], Color.GRAY, 2D);
        }

        DrawUtils.drawHorizontalText(gc, new Point2D(a[0].getX(), a[0].getY() + 100D), Color.BLUE, "A" + this.getId(), 20D);
        DrawUtils.drawHorizontalText(gc, new Point2D(b[0].getX(), b[0].getY() - 40D), Color.GREEN, "B" + this.getId(), 20D);
        return;
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
