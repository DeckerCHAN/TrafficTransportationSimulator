package org.procrastinationpatients.tts.entities;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.procrastinationpatients.tts.source.StaticConfig;
import org.procrastinationpatients.tts.utils.DrawUtils;

/**
 * 南北走向
 * Created by decker on 15-1-28.
 */
public class Road extends Link {


    public Road(Integer id) {
        super(id);
    }

    @Override
    protected void refreshLane() {
        if(this.getA()==null||this.getB()==null)
        {
            return;
        }
        Point2D northPosition = this.getA().getPosition();
        Point2D southPosition = this.getB().getPosition();
        Point2D northCenterPosition = new Point2D(northPosition.getX(), northPosition.getY() + 60D);
        Point2D southCenterPosition = new Point2D(southPosition.getX(), southPosition.getY() - 60D);
        Line centerLine = new Line(northPosition.getX(), northPosition.getY() + 60D, southPosition.getX(), southPosition.getY() - 60D);
        Double d = Math.sqrt(Math.pow(Math.abs(centerLine.getStartX() - centerLine.getEndX()), 2) + Math.pow(Math.abs(centerLine.getStartY() - centerLine.getEndY()), 2)) / StaticConfig.LANE_POINT_SKIP_DISTANCE;
        Integer items = (int) Math.round(d);
        for (Lane lane : this.getLanes()) {
            lane.setLength(items);
        }
        Double diffX = northCenterPosition.getX() - southCenterPosition.getX();
        Double diffY = Math.abs(northCenterPosition.getY() - southCenterPosition.getY());
        Double distance = Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));

        Double progressX = StaticConfig.LANE_POINT_SKIP_DISTANCE * (diffX / distance);
        Double progressY = StaticConfig.LANE_POINT_SKIP_DISTANCE * (diffY / distance);

        Point2D lane0StartPoint = new Point2D(northPosition.getX() - 25D, northPosition.getY() + 60D);
        Point2D lane3EndPoint = new Point2D(southPosition.getX() + 5D, southPosition.getY() - 60D);
        for (int i = 0; i < 3; i++) {
            Lane lane = this.getLanes()[i];
            for (int j = 0; j < lane.getLength(); j++) {
                lane.getVehiclePositions()[j] = new Point2D(lane0StartPoint.getX() - j * progressX + i * 10D, lane0StartPoint.getY() + j * progressY);
            }
        }
        for (int i = 3; i < 6; i++) {
            Lane lane = this.getLanes()[i];
            for (int j = 0; j < lane.getLength(); j++) {
                         lane.getVehiclePositions()[j] = new Point2D(lane3EndPoint.getX() + j * progressX + (i-3) * 10D, lane3EndPoint.getY() - j * progressY);
            }
        }

    }


    @Override
    public void drawStaticGraphic(GraphicsContext gc) {
        Point2D northDotPosition = this.getNorthDot().getPosition();
        Point2D southDotPosition = this.getSouthDot().getPosition();

        Point2D[] a = new Point2D[7];
        Point2D[] b = new Point2D[7];

        a[0] = new Point2D(northDotPosition.getX() - 30D, northDotPosition.getY() + 60D);
        a[1] = new Point2D(northDotPosition.getX() - 20D, northDotPosition.getY() + 60D);
        a[2] = new Point2D(northDotPosition.getX() - 10D, northDotPosition.getY() + 60D);
        a[3] = new Point2D(northDotPosition.getX() - 0D, northDotPosition.getY() + 60D);
        a[4] = new Point2D(northDotPosition.getX() + 10D, northDotPosition.getY() + 60D);
        a[5] = new Point2D(northDotPosition.getX() + 20D, northDotPosition.getY() + 60D);
        a[6] = new Point2D(northDotPosition.getX() + 30D, northDotPosition.getY() + 60D);

        b[0] = new Point2D(southDotPosition.getX() - 30D, southDotPosition.getY() - 60D);
        b[1] = new Point2D(southDotPosition.getX() - 20D, southDotPosition.getY() - 60D);
        b[2] = new Point2D(southDotPosition.getX() - 10D, southDotPosition.getY() - 60D);
        b[3] = new Point2D(southDotPosition.getX() - 0D, southDotPosition.getY() - 60D);
        b[4] = new Point2D(southDotPosition.getX() + 10D, southDotPosition.getY() - 60D);
        b[5] = new Point2D(southDotPosition.getX() + 20D, southDotPosition.getY() - 60D);
        b[6] = new Point2D(southDotPosition.getX() + 30D, southDotPosition.getY() - 60D);

        DrawUtils.drawLine(gc, a[0], a[6], Color.BROWN, 5);
        DrawUtils.drawLine(gc, b[0], b[6], Color.BROWN, 5);

        for (int i = 0; i < 7; i++) {
            DrawUtils.drawLine(gc, a[i], b[i], Color.BLACK, 2);
        }

        DrawUtils.drawText(gc, new Point2D(a[0].getX() - 40D, a[0].getY()), Color.BLUE, "A" + this.getId(),20D);
        DrawUtils.drawText(gc, new Point2D(b[0].getX() + 100D, b[0].getY()), Color.GREEN, "B" + this.getId(),20D);
        return;
    }



    public Dot getNorthDot() {
        return getA();
    }

    public void setNorthDot(Dot northDot) {
        this.setA(northDot);
    }

    public Dot getSouthDot() {
        return getB();
    }

    public void setSouthDot(Dot southDot) {
        this.setB(southDot);
    }

}
