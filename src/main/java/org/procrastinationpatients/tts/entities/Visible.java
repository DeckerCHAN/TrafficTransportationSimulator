package org.procrastinationpatients.tts.entities;

import javafx.scene.canvas.GraphicsContext;



public interface Visible {
	public abstract void drawStaticGraphic(GraphicsContext gc);

	public abstract void drawDynamicGraphic(GraphicsContext gc);
}
