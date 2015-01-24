package org.procrastinationpatients.tts.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.procrastinationpatients.tts.core.Drawable;
import org.procrastinationpatients.tts.core.Engine;
import org.procrastinationpatients.tts.source.StaticConfig;


/**
 * @Author Decker
 */
public class MainWindow extends Application {


    private Group rootGroup;
    private StackPane stackPane;
    private ScrollPane scrollPane;
    private Canvas backgroundCanvas;
    private Canvas dynamicCanvas;

    private Thread tickThread;

    public MainWindow() {
        super();
        //Canvas 的大小最终应当取决于XML文件中的最高和最宽点
        //TODO:将大小修改为最高点和最宽点
        this.backgroundCanvas = new Canvas(4000, 4000);
        this.dynamicCanvas = new Canvas(4000, 4000);
        this.rootGroup = new Group();
        this.scrollPane = new ScrollPane();
        this.scrollPane.setPrefSize(StaticConfig.PANE_SIZE_WIDTH,StaticConfig.PANE_SIZE_HEIGHT);
        this.stackPane = new StackPane();
        //建立显示的层级嵌套结构
        this.rootGroup.getChildren().add(this.scrollPane);
        this.scrollPane.setContent(this.stackPane);
        this.stackPane.getChildren().add(this.backgroundCanvas);
        this.stackPane.getChildren().add(this.dynamicCanvas);


        this.tickThread = new Thread(new Runnable() {
            @Override
            public void run() {


                try {
                    while (true) {
                        tick();
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    System.out.println("Tick thread stopped!");
                }

            }
        });


    }

    @Override
    public void start(Stage stage) throws Exception {
        //设置固定窗口
        stage.setResizable(false);
        //设置stage宽度和高度
        stage.setWidth(StaticConfig.STAGE_SIZE_WIDTH);
        stage.setHeight(StaticConfig.STAGE_SIZE_HEIGHT);
        stage.setTitle("Traffic Transportation Simulator");
        //画出静态物体
        GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();
        for (Drawable drawable : Engine.getInstance().getStaticDrawables()) {
            drawable.draw(gc);
        }
        stage.setScene(new Scene(rootGroup));
        stage.show();
        this.tickThread.start();
    }

    private void tick() {
        GraphicsContext gc = dynamicCanvas.getGraphicsContext2D();
        for (Drawable drawable : Engine.getInstance().getDynamicDrawables()) {
            drawable.draw(gc);
        }
    }
}
