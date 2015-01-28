package org.procrastinationpatients.tts.utils;

import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;

/**
 * @Author Decker
 */
public class DrawUtils {
    public static void drawBallAtCoordinate(GraphicsContext gc, Point2D point, Integer size, Color color) {
        gc.setFill(color);
        gc.fillOval(point.getX() - (size / 2), point.getY() - (size / 2), size, size);
    }

    public static void drawLine(GraphicsContext gc, Point2D start, Point2D end, Color color, Integer width) {
        gc.setStroke(color);
        gc.setLineWidth(width);
        gc.strokeLine(start.getX(), start.getY(), end.getX(), end.getY());

    }

    public static void drawLine(GraphicsContext gc, Line line, Color color, Integer width) {

        drawLine(gc, new Point2D(line.getStartX(), line.getStartY()), new Point2D(line.getEndX(), line.getEndY()), color, width);

    }

    public static void drawPolygon(GraphicsContext gc, Color color, Integer width, Point2D... points) {
        gc.setStroke(color);
        gc.setLineWidth(width);
        double[] xs = new double[points.length];
        double[] ys = new double[points.length];
        for (int i = 0; i < points.length; i++) {
            xs[i] = points[i].getX();
            ys[i] = points[i].getY();
        }

        gc.strokePolygon(xs, ys, points.length);

    }

    public static void drawText(GraphicsContext gc, Point2D point, Color color,String text) {
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setStroke(color);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(
                text,
                point.getX(), point.getY()
        );
    }

}
