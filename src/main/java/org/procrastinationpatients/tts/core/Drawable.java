package org.procrastinationpatients.tts.core;

import com.sun.istack.internal.NotNull;
import javafx.scene.canvas.GraphicsContext;

/**
 * @Author Decker
 */
public interface Drawable {
    public void draw(@NotNull GraphicsContext gc);
}
