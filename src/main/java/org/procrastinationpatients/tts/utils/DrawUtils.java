package org.procrastinationpatients.tts.utils;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @Author Decker
 */
public class DrawUtils {
    public static void drawBallAtCoordinate(GraphicsContext gc, Point2D point, Integer size, Color color) {
        gc.setFill(color);
        gc.fillOval(point.getX() - (size / 2), point.getY() - (size / 2), size, size);
    }

    public static void drawLineThrowTwoPoint(GraphicsContext gc, Point2D start, Point2D end, Color color,Integer width) {
        gc.setStroke(color);
        gc.setLineWidth(width);
        gc.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());

    }
}
