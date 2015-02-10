package org.procrastinationpatients.tts.source;


public class StaticConfig {

    public static final Boolean DEBUG_MODE = false;

    //Main draw configs
    public static final Double STAGE_SIZE_WIDTH = 1024D;
    public static final Double STAGE_SIZE_HEIGHT = 768D;
    public static final Integer TICK_INTERVAL = 200;
    public static final Double LANE_POINT_SKIP_DISTANCE = 1D;
    public static final Boolean DRAW_PATH = false;
    public static final Boolean OUTPUT_DRAW_TIME = false;

    //Chart configs
    public static final Integer INSPECT_LINK=3;
    public static final Integer INSPECT_LANE_GROUP=0;

    public static final Integer INSPECT_TIME=45;

    public static final Double CHART_X_SKIP_MULTIPLE = 15D;
    public static final Double CHART_Y_SKIP_MULTIPLE = 0.2D;
    public static final Double DRAW_BIAS_X=45D;
    public static final Double DRAW_BIAS_Y=40D;
}
