package org.procrastinationpatients.tts.gui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.procrastinationpatients.tts.core.Engine;
import org.procrastinationpatients.tts.core.VisualEntity;
import org.procrastinationpatients.tts.source.ContainersLoader;
import org.procrastinationpatients.tts.source.StaticConfig;
import org.procrastinationpatients.tts.utils.NetUtils;

import java.io.File;


/**
 * @Author Decker
 */
public class MainWindow extends Application {

    private Stage mainStage;
    private Group rootGroup;
    private StackPane stackPane;
    private ScrollPane scrollPane;
    private Canvas backgroundCanvas;
    private Canvas dynamicCanvas;

    private Button loadBtn;
    private Button startBtn;
    private Button pauseBtn;

    private Thread tickThread;
    private Boolean isTickPaused;

    public MainWindow() {
        super();
        //Canvas 的大小最终应当取决于XML文件中的最高和最宽点
        //TODO:将大小修改为最高点和最宽点
        this.backgroundCanvas = new Canvas(4000, 4000);
        this.dynamicCanvas = new Canvas(4000, 4000);

        //初始化载入按钮，并设置相关参数
        this.loadBtn = new Button("Load");
        this.loadBtn.setFont(new Font(20));
        this.loadBtn.setLayoutX(60);
        this.loadBtn.setLayoutY(50);
        this.loadBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    System.out.println("Load!");
                    //载入文件
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Open XML File");
                    File xml = fileChooser.showOpenDialog(mainStage);

                    //通过文件初始化Containers
                    Engine.getInstance().setContainers(new ContainersLoader(xml).getContainers());
                    //通过UTILS计算出画板的大小
                    Point2D maxSize = NetUtils.getMaxNetSize(Engine.getInstance().getContainers());
                    //重设画板大小
                    backgroundCanvas.setWidth(maxSize.getX());
                    backgroundCanvas.setHeight(maxSize.getY());
                    dynamicCanvas.setWidth(maxSize.getX());
                    dynamicCanvas.setHeight(maxSize.getY());
                    drawAllStatic();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //初始化开始按钮，并设置相关参数
        this.startBtn = new Button("Start");
        this.startBtn.setFont(new Font(20));
        this.startBtn.setLayoutX(this.loadBtn.getLayoutX() + this.loadBtn.getWidth() + 100);
        this.startBtn.setLayoutY(50);
        this.startBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Start!");
                //画出静态物体
//                drawAllStatic();
//                tickThread.start();
            }
        });
        //初始化暂停按钮，并设置相关参数
        this.pauseBtn = new Button("Pause");
        this.pauseBtn.setFont(new Font(20));
        this.pauseBtn.setLayoutX(this.startBtn.getLayoutX() + this.startBtn.getWidth() + 100);
        this.pauseBtn.setLayoutY(50);

        this.rootGroup = new Group();
        this.scrollPane = new ScrollPane();
        this.scrollPane.setPrefSize(StaticConfig.PANE_SIZE_WIDTH, StaticConfig.PANE_SIZE_HEIGHT);
        this.stackPane = new StackPane();
        //建立显示的层级嵌套结构
        this.rootGroup.getChildren().add(this.scrollPane);
        this.rootGroup.getChildren().add(this.loadBtn);
        this.rootGroup.getChildren().add(this.startBtn);
        this.rootGroup.getChildren().add(this.pauseBtn);

        this.scrollPane.setContent(this.stackPane);
        this.stackPane.getChildren().add(this.backgroundCanvas);
        this.stackPane.getChildren().add(this.dynamicCanvas);

        this.isTickPaused = false;
        this.tickThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (!isTickPaused) {
                            drawAllDynamic();
                            Thread.sleep(StaticConfig.TICK_INTERVAL);
                        }

                    }
                } catch (InterruptedException e) {
                    System.out.println("Tick thread stopped!");
                }

            }
        });


    }

    @Override
    public void start(Stage stage) throws Exception {
        this.mainStage = stage;
        //设置固定窗口
        stage.setResizable(false);
        //设置stage宽度和高度
        stage.setWidth(StaticConfig.STAGE_SIZE_WIDTH);
        stage.setHeight(StaticConfig.STAGE_SIZE_HEIGHT);
        stage.setTitle("Traffic Transportation Simulator");

        stage.setScene(new Scene(rootGroup));
        stage.show();
    }


    private void drawAllStatic() {
        GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();
        for (VisualEntity visualEntity : Engine.getInstance().getVisualEntities()) {
            visualEntity.drawStaticGraphic(gc);
        }
    }

    private void drawAllDynamic() {
        GraphicsContext gc = dynamicCanvas.getGraphicsContext2D();
        //TODO:将大小修改为最高点和最宽点
        gc.clearRect(0, 0, 4000, 4000);
        for (VisualEntity visualEntity : Engine.getInstance().getVisualEntities()) {
            visualEntity.drawDynamicGraphic(gc);
        }
    }
}
