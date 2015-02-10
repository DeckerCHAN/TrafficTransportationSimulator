package org.procrastinationpatients.tts.gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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


public class MainStage extends TickStage {

    private Point2D canvasMaxSizePoint;

    private Stage mainStage;
    private ChartStage chartStage;
    private BorderPane root;
    private StackPane scrollInnerPane;
    private ScrollPane scrollPane;
    private Canvas backgroundCanvas;
    private Canvas dynamicCanvas;

    private HBox menuContainer;
    private Button loadBtn;
    private Button startBtn;
    private Button pauseBtn;
    private Button resumeBtn;
    private Button chartBtn;
    private Label runingTime;


    public MainStage() {
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
        //初始化图标按钮，并设置相关参数
        this.chartBtn = new Button("Charts");
        this.chartBtn.setPrefSize(100, 20);
        this.chartBtn.setLayoutX(this.resumeBtn.getLayoutX() + this.chartBtn.getWidth() + 100);
        this.chartBtn.setLayoutY(50);
        this.chartBtn.setOnMouseClicked(this.getChartButtonEventHandler());
        //初始化运行时间，并设置相关参数
        this.runingTime = new Label("");
        this.runingTime.setLayoutX(this.chartBtn.getLayoutX() + this.runingTime.getWidth() + 100);
        this.runingTime.setLayoutY(50);


        this.menuContainer = new HBox();
        this.menuContainer.setPadding(new Insets(15, 12, 15, 12));
        this.menuContainer.setSpacing(10);
        this.scrollPane = new ScrollPane();
        this.scrollInnerPane = new StackPane();
        this.root = new BorderPane();


        //建立显示的层级嵌套结构
        this.root.setCenter(this.scrollPane);
        this.root.setTop(this.menuContainer);
        this.menuContainer.getChildren().addAll(this.loadBtn, this.startBtn, this.pauseBtn, this.resumeBtn, this.chartBtn,this.runingTime);

        this.scrollPane.setContent(this.scrollInnerPane);
        this.scrollInnerPane.getChildren().addAll(this.backgroundCanvas, this.dynamicCanvas);


        //设置固定窗口
        this.setResizable(true);
        //设置stage宽度和高度
        this.setWidth(StaticConfig.STAGE_SIZE_WIDTH);
        this.setHeight(StaticConfig.STAGE_SIZE_HEIGHT);

        this.setTitle("Traffic Transportation Simulator");
        this.setScene(new Scene(this.root));
    }

    /**
     * 绘制全部的静态对象
     */
    @Override
    protected void drawAllStatic() {
        GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();
        for (Visible visualEntity : Engine.getInstance().getVisualEntities()) {
            visualEntity.drawStaticGraphic(gc);
        }
    }

    @Override
    protected void drawAllDynamic() {
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
                    if (xml == null) {
                        return;
                    }
                    //通过文件初始化Containers
                    EntityLoader containersLoader = new EntityLoader(xml);
                    containersLoader.LoadFromFile();
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
                start();
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
                pause();
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
                resume();
            }
        };
    }

    /**
     * Chart键按下后的响应事件
     *
     * @return 响应事件
     */
    private EventHandler<MouseEvent> getChartButtonEventHandler() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                chartStage = new ChartStage();
                chartStage.show();

            }
        };
    }

    @Override
    protected void tick() {
        super.tick();
        this.runingTime.setText(String.format("Running: %s(s)", this.getTickCounts() * StaticConfig.TICK_INTERVAL / 1000));
    }

    @Override
    public void start() {
        super.start();
        //启动动运算线程
        Engine.getInstance().setProcessor(new Processor());
        Engine.getInstance().getProcessor().start();
        System.out.println("Start!");
    }

    @Override
    public void pause() {
        super.pause();
        Engine.getInstance().pause();
        if (chartStage != null) {
            chartStage.pause();
        }
        System.out.println("Pause!");
    }

    @Override
    public void resume() {
        super.resume();
        Engine.getInstance().resume();
        if (chartStage != null) {
            chartStage.resume();
        }
        System.out.println("Resume!");
    }
}
