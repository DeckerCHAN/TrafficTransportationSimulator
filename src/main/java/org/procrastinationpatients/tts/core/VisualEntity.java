package org.procrastinationpatients.tts.core;

import javafx.scene.canvas.GraphicsContext;

/**
 * @Author Decker
 */
public abstract class VisualEntity {
	public abstract void drawStaticGraphic(GraphicsContext gc);

	public abstract void drawDynamicGraphic(GraphicsContext gc);
}
