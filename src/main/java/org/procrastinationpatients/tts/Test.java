package org.procrastinationpatients.tts;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.apache.commons.io.FileUtils;
import org.procrastinationpatients.tts.core.Engine;
import org.procrastinationpatients.tts.core.VisualEntity;
import org.procrastinationpatients.tts.source.ContainersLoader;

import java.util.LinkedList;

/**
 * @Author Decker
 */
public class Test {
    public static void main(String[] args) {
        try {
            ContainersLoader loader=new ContainersLoader(FileUtils.getFile("Sample(20100422 Network-same control strategy).xml"));
            loader.getContainers();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
