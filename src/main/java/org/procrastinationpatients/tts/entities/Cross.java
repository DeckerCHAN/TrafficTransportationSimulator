package org.procrastinationpatients.tts.entities;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.procrastinationpatients.tts.source.StaticConfig;
import org.procrastinationpatients.tts.utils.DrawUtils;

public class Cross extends IdentifiableObject implements Visible, Dot, FunctionalObject {

    private static Double[] laneVisualLength;
    private static Double[] progressA, progressB;
    private Point2D position;
    private Lane[] northLanes;
    private Lane[] southLanes;
    private Lane[] eastLanes;
    private Lane[] westLanes;

    private Road northRoad;
    private Road southRoad;
    private Street eastStreet;
    private Street westStreet;

    private static TrafficLight[] northTrafficLights;
    private static TrafficLight[] southTrafficLights;
    private static TrafficLight[] eastTrafficLights;
    private static TrafficLight[] westTrafficLights;

    static {
        laneVisualLength = new Double[7];
        laneVisualLength[0] = Math.sqrt(Math.pow(35D, 2) + Math.pow(35D, 2));
        laneVisualLength[1] = Math.sqrt(Math.pow(35D, 2) + Math.pow(45D, 2));
        laneVisualLength[2] = Math.sqrt(Math.pow(35D, 2) + Math.pow(55D, 2));
        laneVisualLength[3] = 120D;
        laneVisualLength[4] = Math.sqrt(Math.pow(65D, 2) + Math.pow(65D, 2));
        laneVisualLength[5] = Math.sqrt(Math.pow(65D, 2) + Math.pow(75D, 2));
        laneVisualLength[6] = Math.sqrt(Math.pow(65D, 2) + Math.pow(85D, 2));

        progressA = new Double[7];
        progressB = new Double[7];

        progressA[0] = StaticConfig.LANE_POINT_SKIP_DISTANCE * (35D / laneVisualLength[0]);
        progressB[0] = StaticConfig.LANE_POINT_SKIP_DISTANCE * (35D / laneVisualLength[0]);

        progressA[1] = StaticConfig.LANE_POINT_SKIP_DISTANCE * (35D / laneVisualLength[1]);
        progressB[1] = StaticConfig.LANE_POINT_SKIP_DISTANCE * (45D / laneVisualLength[1]);

        progressA[2] = StaticConfig.LANE_POINT_SKIP_DISTANCE * (35D / laneVisualLength[2]);
        progressB[2] = StaticConfig.LANE_POINT_SKIP_DISTANCE * (55D / laneVisualLength[2]);

        progressA[3] = 0D;
        progressB[3] = StaticConfig.LANE_POINT_SKIP_DISTANCE;

        progressA[4] = StaticConfig.LANE_POINT_SKIP_DISTANCE * (65D / laneVisualLength[4]);
        progressB[4] = StaticConfig.LANE_POINT_SKIP_DISTANCE * (65D / laneVisualLength[4]);

        progressA[5] = StaticConfig.LANE_POINT_SKIP_DISTANCE * (65D / laneVisualLength[5]);
        progressB[5] = StaticConfig.LANE_POINT_SKIP_DISTANCE * (75D / laneVisualLength[5]);

        progressA[6] = StaticConfig.LANE_POINT_SKIP_DISTANCE * (65D / laneVisualLength[6]);
        progressB[6] = StaticConfig.LANE_POINT_SKIP_DISTANCE * (85D / laneVisualLength[6]);

		northTrafficLights = new TrafficLight[]{new TrafficLight(1,true), new TrafficLight(1,false), new TrafficLight(1,false), new TrafficLight(1,true), new TrafficLight(1,true), new TrafficLight(1,true)};
		westTrafficLights = new TrafficLight[]{new TrafficLight(1,true), new TrafficLight(0,false), new TrafficLight(0,false), new TrafficLight(1,true), new TrafficLight(1,true), new TrafficLight(1,true)};
		eastTrafficLights = new TrafficLight[6];
		southTrafficLights = new TrafficLight[6];
		for (int i = 0; i < 6; i++) {
			eastTrafficLights[i] = westTrafficLights[5 - i];
			southTrafficLights[i] = northTrafficLights[5 - i];
		}
    }

    public Cross(Integer id, Point2D position) {
        super(id);
        this.position = position;

        this.northLanes = new Lane[]{new Lane(this,0,northTrafficLights[0]), new Lane(this,1,northTrafficLights[0]), new Lane(this,2,northTrafficLights[0]), new Lane(this,3,northTrafficLights[1]), new Lane(this,4,northTrafficLights[2]), new Lane(this,5,northTrafficLights[2]), new Lane(this,6,northTrafficLights[2])};
        this.southLanes = new Lane[]{new Lane(this,0,southTrafficLights[5]), new Lane(this,1,southTrafficLights[5]), new Lane(this,2,southTrafficLights[5]), new Lane(this,3,southTrafficLights[4]), new Lane(this,4,southTrafficLights[3]), new Lane(this,5,southTrafficLights[3]), new Lane(this,6,southTrafficLights[3])};
        this.eastLanes = new Lane[]{new Lane(this,0,eastTrafficLights[5]), new Lane(this,1,eastTrafficLights[5]), new Lane(this,2,eastTrafficLights[5]), new Lane(this,3,eastTrafficLights[4]), new Lane(this,4,eastTrafficLights[3]), new Lane(this,5,eastTrafficLights[3]), new Lane(this,6,eastTrafficLights[3])};
        this.westLanes = new Lane[]{new Lane(this,0,westTrafficLights[0]), new Lane(this,1,westTrafficLights[0]), new Lane(this,2,westTrafficLights[1]), new Lane(this,3,westTrafficLights[1]), new Lane(this,4,westTrafficLights[2]), new Lane(this,5,westTrafficLights[2]), new Lane(this,6,westTrafficLights[2])};

        this.refreshLaneLength();

    }

	public static void changeTrafficLight(){
		for(int i = 0 ; i < northTrafficLights.length ; i++){
			northTrafficLights[i].changeLight();
			westTrafficLights[i].changeLight();
		}
	}

    @Override
    public Point2D getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(Point2D position) {
        this.position = position;
    }

    @Override
    public void drawStaticGraphic(GraphicsContext gc) {
        DrawUtils.drawBallAtCoordinate(gc, this.getPosition(), 6, Color.GREEN);
        Point2D a = new Point2D(this.getPosition().getX() - 30D, this.getPosition().getY() - 60D);
        Point2D b = new Point2D(this.getPosition().getX() + 30D, this.getPosition().getY() - 60D);

        Point2D c = new Point2D(this.getPosition().getX() + 60D, this.getPosition().getY() - 30D);
        Point2D d = new Point2D(this.getPosition().getX() + 60D, this.getPosition().getY() + 30D);

        Point2D e = new Point2D(this.getPosition().getX() + 30D, this.getPosition().getY() + 60D);
        Point2D f = new Point2D(this.getPosition().getX() - 30D, this.getPosition().getY() + 60D);

        Point2D g = new Point2D(this.getPosition().getX() - 60D, this.getPosition().getY() + 30D);
        Point2D h = new Point2D(this.getPosition().getX() - 60D, this.getPosition().getY() - 30D);

        DrawUtils.drawPolygon(gc, Color.AQUA, 3, a, b, c, d, e, f, g, h);

        if (StaticConfig.DEBUG_MODE) {
            DrawUtils.drawText(gc, this.getPosition().getX(), this.getPosition().getY() - 15D, Color.GREEN, "C:" + this.getId(), 15D);

        }
    }

    @Override
    public void drawDynamicGraphic(GraphicsContext gc) {
        int pointCount = 0;
        for (Lane[] lanes : this.getRowLanes()) {
            for (Lane lane : lanes) {
                for (int i = 0; i < lane.getLength(); i++) {
                    if (lane.getVehicles()[i] != null) {
                        if (lane.getVehiclePositions()[i] == null) {
                            continue;
                        }
                        if (StaticConfig.DEBUG_MODE) {
                            DrawUtils.drawText(gc, lane.getVehiclePositions()[i].getX(), lane.getVehiclePositions()[i].getY() - 11D, Color.RED, String.format("(%s,%s)", (int) lane.getVehiclePositions()[i].getX(), (int) lane.getVehiclePositions()[i].getY()), 10D);
                        }
                        DrawUtils.drawBallAtCoordinate(gc, lane.getVehiclePositions()[i].getX(), lane.getVehiclePositions()[i].getY(), 4, Color.RED);
                        pointCount++;
                    }
                }
            }
        }
        if (StaticConfig.DEBUG_MODE) {
            DrawUtils.drawText(gc, this.getPosition(), Color.RED, String.format("Drew:%s", pointCount), 11D);
        }

        for (int i = 0; i < 6; i++) {


            DrawUtils.drawLine(gc,
                    new Point2D(this.getPosition().getX() - 26D + 10D * i, this.getPosition().getY() - 60D),
                    new Point2D(this.getPosition().getX() - 26D + 10D * i + 2D, this.getPosition().getY() - 60D),
                    this.getNorthTrafficLights()[i].isRedLight() ? Color.RED : Color.GREEN,
                    8D);

            DrawUtils.drawLine(gc,
                    new Point2D(this.getPosition().getX() - 26D + 10D * i, this.getPosition().getY() + 60D),
                    new Point2D(this.getPosition().getX() - 26D + 10D * i + 2D, this.getPosition().getY() + 60D),
                    this.getSouthTrafficLights()[i].isRedLight() ? Color.RED : Color.GREEN,
                    8D);

            DrawUtils.drawLine(gc,
                    new Point2D(this.getPosition().getX() - 60D, this.getPosition().getY() + 26D - 10D * i),
                    new Point2D(this.getPosition().getX() - 60D, this.getPosition().getY() + 26D - 10D * i - 2D),
                    this.getWestTrafficLights()[i].isRedLight() ? Color.RED : Color.GREEN,
                    8D);

            DrawUtils.drawLine(gc,
                    new Point2D(this.getPosition().getX() + 60D, this.getPosition().getY() + 26D - 10D * i),
                    new Point2D(this.getPosition().getX() + 60D, this.getPosition().getY() + 26D - 10D * i - 2D),
                    this.getEastTrafficLights()[i].isRedLight() ? Color.RED : Color.GREEN,
                    8D);


        }
    }

    private void refreshLaneLength() {
        for (Lane[] lanes : this.getRowLanes()) {
            for (int i = 0; i < lanes.length; i++) {
                Integer itemLength = (int) Math.round(laneVisualLength[i] / StaticConfig.LANE_POINT_SKIP_DISTANCE);
                lanes[i].setLength(itemLength);
            }
        }
        calculateVehiclePositions();
    }

    private void calculateVehiclePositions() {

        //var
        Point2D m, n, o;

        //south dir
        m = new Point2D(this.getPosition().getX() + 25D, this.getPosition().getY() + 60D);

        //  this.getSouthLanes()[0].getVehiclePositions()[0] =  new Point2D(this.getPosition().getX() + 15D, this.getPosition().getY() + 60D);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < this.getSouthLanes()[i].getVehiclePositions().length; j++) {
                this.getSouthLanes()[i].getVehiclePositions()[j] = new Point2D(m.getX() + j * progressA[i], m.getY() - j * progressB[i]);
            }
        }
        n = new Point2D(this.getPosition().getX() + 15D, this.getPosition().getY() + 60D);
        for (int j = 0; j < this.getSouthLanes()[3].getVehiclePositions().length; j++) {
            this.getSouthLanes()[3].getVehiclePositions()[j] = new Point2D(n.getX() + j * progressA[3], n.getY() - j * progressB[3]);
        }
        o = new Point2D(this.getPosition().getX() + 5D, this.getPosition().getY() + 60D);
        for (int i = 4; i < 7; i++) {
            for (int j = 0; j < this.getSouthLanes()[i].getVehiclePositions().length; j++) {
                this.getSouthLanes()[i].getVehiclePositions()[j] = new Point2D(o.getX() - j * progressA[i], o.getY() - j * progressB[i]);
            }
        }

        //north dir
        m = new Point2D(this.getPosition().getX() - 25D, this.getPosition().getY() - 60D);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < this.getNorthLanes()[i].getVehiclePositions().length; j++) {
                this.getNorthLanes()[i].getVehiclePositions()[j] = new Point2D(m.getX() - j * progressA[i], m.getY() + j * progressB[i]);
            }
        }
        n = new Point2D(this.getPosition().getX() - 15D, this.getPosition().getY() - 60D);
        for (int j = 0; j < this.getNorthLanes()[3].getVehiclePositions().length; j++) {
            this.getNorthLanes()[3].getVehiclePositions()[j] = new Point2D(n.getX() + j * progressA[3], n.getY() + j * progressB[3]);
        }
        o = new Point2D(this.getPosition().getX() - 5D, this.getPosition().getY() - 60D);
        for (int i = 4; i < 7; i++) {
            for (int j = 0; j < this.getNorthLanes()[i].getVehiclePositions().length; j++) {
                this.getNorthLanes()[i].getVehiclePositions()[j] = new Point2D(o.getX() + j * progressA[i], o.getY() + j * progressB[i]);
            }
        }

        //west dir
        m = new Point2D(this.getPosition().getX() - 60D, this.getPosition().getY() + 25D);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < this.getWestLanes()[i].getVehiclePositions().length; j++) {
                this.getWestLanes()[i].getVehiclePositions()[j] = new Point2D(m.getX() + j * progressB[i], m.getY() + j * progressA[i]);
            }
        }
        n = new Point2D(this.getPosition().getX() - 60D, this.getPosition().getY() + 15D);
        for (int j = 0; j < this.getWestLanes()[3].getVehiclePositions().length; j++) {
            this.getWestLanes()[3].getVehiclePositions()[j] = new Point2D(n.getX() + j * progressB[3], n.getY() + j * progressA[3]);
        }
        o = new Point2D(this.getPosition().getX() - 60D, this.getPosition().getY() + 5D);
        for (int i = 4; i < 7; i++) {
            for (int j = 0; j < this.getWestLanes()[i].getVehiclePositions().length; j++) {
                this.getWestLanes()[i].getVehiclePositions()[j] = new Point2D(o.getX() + j * progressB[i], o.getY() - j * progressA[i]);
            }
        }

        //east dir
        m = new Point2D(this.getPosition().getX() + 60D, this.getPosition().getY() - 25D);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < this.getEastLanes()[i].getVehiclePositions().length; j++) {
                this.getEastLanes()[i].getVehiclePositions()[j] = new Point2D(m.getX() - j * progressB[i], m.getY() - j * progressA[i]);
            }
        }
        n = new Point2D(this.getPosition().getX() + 60D, this.getPosition().getY() - 15D);
        for (int j = 0; j < this.getEastLanes()[3].getVehiclePositions().length; j++) {
            this.getEastLanes()[3].getVehiclePositions()[j] = new Point2D(n.getX() - j * progressB[3], n.getY() + j * progressA[3]);
        }
        o = new Point2D(this.getPosition().getX() + 60D, this.getPosition().getY() - 5D);
        for (int i = 4; i < 7; i++) {
            for (int j = 0; j < this.getEastLanes()[i].getVehiclePositions().length; j++) {
                this.getEastLanes()[i].getVehiclePositions()[j] = new Point2D(o.getX() - j * progressB[i], o.getY() + j * progressA[i]);
            }
        }

    }

    public Road getNorthRoad() {
        return northRoad;
    }

    public void setNorthRoad(Road northRoad) {
        this.northRoad = northRoad;
    }

    public Road getSouthRoad() {
        return southRoad;
    }

    public void setSouthRoad(Road southRoad) {
        this.southRoad = southRoad;
    }

    public Street getEastStreet() {
        return eastStreet;
    }

    public void setEastStreet(Street eastStreet) {
        this.eastStreet = eastStreet;
    }

    public Street getWestStreet() {
        return westStreet;
    }

    public void setWestStreet(Street westStreet) {
        this.westStreet = westStreet;
    }

    public Lane[] getNorthLanes() {
        return northLanes;
    }

    public void setNorthLanes(Lane[] northLanes) {
        this.northLanes = northLanes;
    }

    public Lane[] getSouthLanes() {
        return southLanes;
    }

    public void setSouthLanes(Lane[] southLanes) {
        this.southLanes = southLanes;
    }

    public Lane[] getEastLanes() {
        return eastLanes;
    }

    public void setEastLanes(Lane[] eastLanes) {
        this.eastLanes = eastLanes;
    }

    public Lane[] getWestLanes() {
        return westLanes;
    }

    public void setWestLanes(Lane[] westLanes) {
        this.westLanes = westLanes;
    }

    public Lane[][] getRowLanes() {
        return new Lane[][]{this.getNorthLanes(), this.getEastLanes(), this.getSouthLanes(), this.getWestLanes()};
    }
    
    public Link [] getRowLink()
    {
        return new Link[]{this.getNorthRoad(),this.getEastStreet(),this.getSouthRoad(),this.getWestStreet()};
    }

	@Override
	public void toGoalLine(Vehicle vehicle) {}

	@Override
	public int changeLine(Vehicle vehicle) {
		return 0;
	}


    public TrafficLight[] getNorthTrafficLights() {
        return northTrafficLights;
    }

    public void setNorthTrafficLights(TrafficLight[] northTrafficLights) {
        this.northTrafficLights = northTrafficLights;
    }

    public TrafficLight[] getSouthTrafficLights() {
        return southTrafficLights;
    }

    public void setSouthTrafficLights(TrafficLight[] southTrafficLights) {
        this.southTrafficLights = southTrafficLights;
    }

    public TrafficLight[] getEastTrafficLights() {
        return eastTrafficLights;
    }

    public void setEastTrafficLights(TrafficLight[] eastTrafficLights) {
        this.eastTrafficLights = eastTrafficLights;
    }

    public TrafficLight[] getWestTrafficLights() {
        return westTrafficLights;
    }

    public void setWestTrafficLights(TrafficLight[] westTrafficLights) {
        this.westTrafficLights = westTrafficLights;
    }
}
