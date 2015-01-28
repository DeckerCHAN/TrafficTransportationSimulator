package org.procrastinationpatients.tts.utils;

import javafx.geometry.Point2D;
import org.procrastinationpatients.tts.entities.Dot;

/**
 * @Author Decker
 */
public class NetUtils {
    /**
     * 计算网络中最大X与最大Y
     *
     * @param dots 网络点集
     * @return 最大X与最大Y的2DPoint
     */
    public static Point2D getMaxNetSize(Dot[] dots) {
        Double maxX = 0D;
        Double maxY = 0D;
        for (Dot dot : dots) {


            if((dot.getPosition()==null))
                {
                    System.out.println("对象坐标空，无法将操作应用到实例！");
                }
                maxX = dot.getPosition().getX() > maxX ? dot.getPosition().getX() : maxX;
                maxY = dot.getPosition().getY() > maxY ? dot.getPosition().getY() : maxY;
            dot.setPosition(new Point2D(dot.getPosition().getX() + 60D, dot.getPosition().getY() + 60D));
        }
        //留出10%的空间
        return new Point2D(maxX * 1.05, maxY * 1.05);
    }
}
