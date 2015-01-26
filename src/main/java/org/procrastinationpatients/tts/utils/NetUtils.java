package org.procrastinationpatients.tts.utils;

import javafx.geometry.Point2D;
import org.procrastinationpatients.tts.core.Container;
import org.procrastinationpatients.tts.core.Dot;
import org.procrastinationpatients.tts.core.Margin;

/**
 * @Author Decker
 */
public class NetUtils {
    /**
     * 计算网络中最大X与最大Y
     *
     * @param containers 网络点集
     * @return 最大X与最大Y的2DPoint
     */
    public static Point2D getMaxNetSize(Container[] containers) {
        Double maxX = 0D;
        Double maxY = 0D;
        for (Container container : containers) {
            if (container instanceof Dot) {
                Dot dot = ((Dot) container);
                maxX = dot.getPosition().getX() > maxX ? dot.getPosition().getX() : maxX;
                maxY = dot.getPosition().getY() > maxY ? dot.getPosition().getY() : maxY;
            }
        }
        return new Point2D(maxX, maxY);
    }
}
