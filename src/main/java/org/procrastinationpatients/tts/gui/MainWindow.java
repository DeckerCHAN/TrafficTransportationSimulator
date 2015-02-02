package org.procrastinationpatients.tts.gui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.procrastinationpatients.tts.core.Engine;
import org.procrastinationpatients.tts.core.Processor;
import org.procrastinationpatients.tts.entities.Dot;
import org.procrastinationpatients.tts.entities.Visible;
import org.procrastinationpatients.tts.source.EntityLoader;
import org.procrastinationpatients.tts.source.StaticConfig;
import org.procrastinationpatients.tts.utils.NetUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;



public class MainWindow extends Application {

    private Point2D canvasMaxSizePoint;

    private Stage mainStage;
    private BorderPane root;
    private StackPane scrollInnerPane;
    private ScrollPane scrollPane;
    private Canvas backgroundCanvas;
    private Canvas dynamicCanvas;

    private HBox buttonsContainer;
    private Button loadBtn;
    private Button startBtn;
    private Button pauseBtn;
    private Button resumeBtn;


    private Timeline timeline;
    private Boolean isTickPaused;

    public MainWindow() {
        super();
        //Canvas 的大小最终应当取决于XML文件中的最高和最宽点
        //TODO:将大小修改为最高点和最宽点
        this.backgroundCanvas = new Canvas(4000, 4000);
        this.dynamicCanvas = new Canvas(4000, 4000);

        //初始化载入按钮，并设置相关参数
        this.loadBtn = new Button("Load");
        this.loadBtn.setPrefSize(100, 20);
        this.loadBtn.setLayoutX(60);
        this.loadBtn.setLayoutY(50);
        this.loadBtn.setOnMouseClicked(this.getLoadButtonEventHandler());
        //初始化开始按钮，并设置相关参数
        this.startBtn = new Button("Start");
        this.startBtn.setPrefSize(100, 20);
        this.startBtn.setLayoutX(this.loadBtn.getLayoutX() + this.loadBtn.getWidth() + 100);
        this.startBtn.setLayoutY(50);
        this.startBtn.setOnMouseClicked(this.getStartButtonEventHandler());
        //初始化暂停按钮，并设置相关参数
        this.pauseBtn = new Button("Pause");
        this.pauseBtn.setPrefSize(100, 20);
        this.pauseBtn.setLayoutX(this.startBtn.getLayoutX() + this.startBtn.getWidth() + 100);
        this.pauseBtn.setLayoutY(50);
        this.pauseBtn.setOnMouseClicked(this.getPauseButtonEventHandler());
        //初始化恢复按钮，并设置相关参数
        this.resumeBtn = new Button("Resume");
        this.resumeBtn.setPrefSize(100, 20);
        this.resumeBtn.setLayoutX(this.pauseBtn.getLayoutX() + this.pauseBtn.getWidth() + 100);
        this.resumeBtn.setLayoutY(50);
        this.resumeBtn.setOnMouseClicked(this.getResumeButtonEventHandler());

        this.buttonsContainer = new HBox();
        buttonsContainer.setPadding(new Insets(15, 12, 15, 12));
        buttonsContainer.setSpacing(10);
        this.scrollPane = new ScrollPane();
        this.scrollInnerPane = new StackPane();
        this.root = new BorderPane();


        //建立显示的层级嵌套结构
        this.root.setCenter(this.scrollPane);
        this.root.setTop(this.buttonsContainer);
        this.buttonsContainer.getChildren().addAll(this.loadBtn, this.startBtn, this.pauseBtn,this.resumeBtn);

        this.scrollPane.setContent(this.scrollInnerPane);
        this.scrollInnerPane.getChildren().addAll(this.backgroundCanvas, this.dynamicCanvas);

        this.isTickPaused = false;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(StaticConfig.TICK_INTERVAL), this.getTickHandler()));
        timeline.setCycleCount(Animation.INDEFINITE);


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
        stage.setScene(new Scene(this.root));

        stage.show();
    }

    /**
     * 绘制全部的静态对象
     */
    private void drawAllStatic() {
        GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();
        for (Visible visualEntity : Engine.getInstance().getVisualEntities()) {
            visualEntity.drawStaticGraphic(gc);
        }
    }

    private void drawAllDynamic() {
        GraphicsContext gc = dynamicCanvas.getGraphicsContext2D();
        if (!StaticConfig.DRAW_PATH) {
            gc.clearRect(0, 0, dynamicCanvas.getWidth(), dynamicCanvas.getHeight());
        }
        for (Visible visualEntity : Engine.getInstance().getVisualEntities()) {
            visualEntity.drawDynamicGraphic(gc);
        }
    }

    /**
     * Load 键按下后的响应事件
     *
     * @return 响应事件
     */
    private EventHandler<MouseEvent> getLoadButtonEventHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    System.out.println("Load!");
                    //载入文件
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Open XML File");
                    File xml = fileChooser.showOpenDialog(mainStage);
                    if(xml==null){return;}
                    //通过文件初始化Containers
                    EntityLoader containersLoader = new EntityLoader();
                    containersLoader.LoadFromFile(xml);
                    Engine.getInstance().setCrosses(containersLoader.getCrosses());
                    Engine.getInstance().setLinks(containersLoader.getLinks());
                    Engine.getInstance().setMargins(containersLoader.getMargins());
                    //通过UTILS计算出画板的大小
                    ArrayList<Dot> dots = new ArrayList<>();
                    dots.addAll(Arrays.asList(containersLoader.getCrosses()));
                    dots.addAll(Arrays.asList(containersLoader.getMargins()));

                    canvasMaxSizePoint = NetUtils.getMaxNetSize(dots.toArray(new Dot[]{}));
                    //重设画板大小
                    backgroundCanvas.setWidth(canvasMaxSizePoint.getX());
                    backgroundCanvas.setHeight(canvasMaxSizePoint.getY());
                    dynamicCanvas.setWidth(canvasMaxSizePoint.getX());
                    dynamicCanvas.setHeight(canvasMaxSizePoint.getY());
                    drawAllStatic();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * Start 键按下后的响应事件
     *
     * @return 响应事件
     */
    private EventHandler<MouseEvent> getStartButtonEventHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("Start!");
                //启动动态绘制线程
                Engine.getInstance().setProcessor(new Processor());
                Engine.getInstance().getProcessor().start();
				timeline.play();
            }
        };
    }

    /**
     * Pause 键按下后的响应事件
     *
     * @return 响应事件
     */
    private EventHandler<MouseEvent> getPauseButtonEventHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Engine.getInstance().pause();
				timeline.stop();
                System.out.println("Pause!");
            }
        };
    }

    /**
     * Resume键按下后的响应事件
     *
     * @return 响应事件
     */
    private EventHandler<MouseEvent> getResumeButtonEventHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Engine.getInstance().resume();
				timeline.play();
            }
        };
    }

    private EventHandler<ActionEvent> getTickHandler() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
//                Long start = System.currentTimeMillis();
                drawAllDynamic();
//                Long end = System.currentTimeMillis();
//                System.out.println(String.format("Draw cost %s ms.", end - start));

            }
        };
    }

}
