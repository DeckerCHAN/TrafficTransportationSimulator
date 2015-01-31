package org.procrastinationpatients.tts.source;

import javafx.geometry.Point2D;
import org.apache.commons.io.FileUtils;
import org.procrastinationpatients.tts.entities.*;
import org.procrastinationpatients.tts.utils.LaneUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @Author Decker
 */
public class EntityLoader {
    private Node root;
    private Document doc;
    private HashMap<Integer, Cross> crossCache;
    private HashMap<Integer, Margin> marginCache;
    private HashMap<Integer, Link> linkCache;

    public EntityLoader() {


    }

    public void LoadFromFile(File xmlFile) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setIgnoringComments(true);
        dbFactory.setIgnoringElementContentWhitespace(true);
        dbFactory.setValidating(false);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(FileUtils.openInputStream(xmlFile));
        doc.getDocumentElement().normalize();
        root = doc.getElementsByTagName("Data").item(0);
        //建立Cross的集合
        NodeList crosses = ((Element) root).getElementsByTagName("Cross");
        crossCache = new HashMap<>();
        for (int i = 0; i < crosses.getLength(); i++) {
            Node node = crosses.item(i);
            Element element = ((Element) node.getChildNodes());
            //获取cross的ID
            Integer crossID = Integer.valueOf(element.getElementsByTagName("Object_ID").item(0).getTextContent());
            //获取cross中x的坐标
            Double crossPositionX = Double.valueOf(element.getElementsByTagName("x").item(0).getTextContent());
            //获取cross中y的坐标
            Double crossPositionY = Double.valueOf(element.getElementsByTagName("y").item(0).getTextContent());
            //在左边和上边留下各60D的空间
            crossPositionX += 60D;
            crossPositionY += 60D;

            Cross cross = new Cross(crossID, new Point2D(crossPositionX, crossPositionY));
            this.crossCache.put(crossID, cross);
        }

        //建立Margin的集合
        NodeList margins = ((Element) root).getElementsByTagName("MarginalPoint");
        marginCache = new HashMap<>();
        for (int i = 0; i < margins.getLength(); i++) {
            Node node = margins.item(i);
            Element element = ((Element) node.getChildNodes());
            Integer marginID = Integer.valueOf(element.getElementsByTagName("Object_ID").item(0).getTextContent());

            //获取margin中x的坐标
            Double marginPositionX = Double.valueOf(element.getElementsByTagName("x").item(0).getTextContent());
            //获取margin中y的坐标
            Double marginPositionY = Double.valueOf(element.getElementsByTagName("y").item(0).getTextContent());

            //在左边和上边留下各60D的空间
            marginPositionX += 60D;
            marginPositionY += 60D;

            Margin margin = new Margin(marginID, new Point2D(marginPositionX, marginPositionY));
            this.marginCache.put(marginID, margin);

        }

        //建立link集合并连接
        NodeList links = ((Element) root).getElementsByTagName("Link");
        linkCache = new HashMap<>();
        for (int i = 0; i < links.getLength(); i++) {
            Node node = links.item(i);
            Element element = ((Element) node.getChildNodes());

            Integer linkID = Integer.valueOf(element.getElementsByTagName("Object_ID").item(0).getTextContent());

            String connectionATypeString = ((Element) element.getElementsByTagName("Link_Start").item(0)).getElementsByTagName("Object_Type").item(0).getTextContent();
            Integer connectionAID = Integer.valueOf(((Element) element.getElementsByTagName("Link_Start").item(0)).getElementsByTagName("Object_ID").item(0).getTextContent());

            String connectionBTypeString = ((Element) element.getElementsByTagName("Link_End").item(0)).getElementsByTagName("Object_Type").item(0).getTextContent();
            Integer connectionBID = Integer.valueOf(((Element) element.getElementsByTagName("Link_End").item(0)).getElementsByTagName("Object_ID").item(0).getTextContent());


            //先将两个Dot取出来(有可能是Margin也有可能是Cross)
            Dot dotA, dotB;
            if (connectionATypeString.toLowerCase().equals("m")) {
                dotB = this.marginCache.get(connectionAID);
            } else {
                dotB = this.crossCache.get(connectionAID);
            }

            if (connectionBTypeString.toLowerCase().equals("m")) {
                dotA = this.marginCache.get(connectionBID);
            } else {
                dotA = this.crossCache.get(connectionBID);
            }

            //去你麻痹的重
            Boolean isDup = false;
            for (Link link : this.linkCache.values()) {
                if (link.getA().getPosition().equals(dotA.getPosition()) && link.getB().getPosition().equals(dotB.getPosition())) {
                    isDup = true;
                    break;
                }
                if (link.getA().getPosition().equals(dotB.getPosition()) && link.getB().getPosition().equals(dotA.getPosition())) {
                    isDup = true;
                    break;
                }
            }
            if (isDup) {
                continue;
            }

            //计算两个Cross的X差绝对值
            Double differX = Math.abs(dotA.getPosition().getX() - dotB.getPosition().getX());
            //计算两个Cross的Y差绝对值
            Double differY = Math.abs(dotA.getPosition().getY() - dotB.getPosition().getY());


            if (differX < differY) { //若X的差比Y的差要小，说明这是一条连接南北的道路
                Road road = new Road(linkID);
                if (dotA.getPosition().getY() < dotB.getPosition().getY()) { //A点要更靠北的话
                    road.setNorthDot(dotA);
                    road.setSouthDot(dotB);

                    if (dotA instanceof Cross) {
                        ((Cross) dotA).setSouthRoad(road);
                    } else {
                        ((Margin) dotA).setConnectedLink(road);
                        ((Margin) dotA).setFirstInputLaneIndex(0);
                    }

                    if (dotB instanceof Cross) {
                        ((Cross) dotB).setNorthRoad(road);
                    } else {
                        ((Margin) dotB).setConnectedLink(road);
                        ((Margin) dotB).setFirstInputLaneIndex(3);
                    }
                } else {//否则B点更靠北
                    road.setNorthDot(dotB);
                    road.setSouthDot(dotA);

                    if (dotA instanceof Cross) {
                        ((Cross) dotA).setNorthRoad(road);

                    } else {
                        ((Margin) dotA).setConnectedLink(road);
                        ((Margin) dotA).setFirstInputLaneIndex(3);
                    }

                    if (dotB instanceof Cross) {
                        ((Cross) dotB).setSouthRoad(road);
                    } else {
                        ((Margin) dotB).setConnectedLink(road);
                        ((Margin) dotB).setFirstInputLaneIndex(0);
                    }
                }
                this.linkCache.put(linkID, road);
            } else { //若Y的差比X的差要小，说明这是一条连接东西的道路
                Street street = new Street(linkID);
                if (dotA.getPosition().getX() < dotB.getPosition().getX()) { //A点要更靠西的话
                    street.setWestDot(dotA);
                    street.setEastDot(dotB);

                    if (dotA instanceof Cross) {
                        ((Cross) dotA).setEastStreet(street);

                    } else {
                        ((Margin) dotA).setConnectedLink(street);
                        ((Margin) dotA).setFirstInputLaneIndex(0);
                    }

                    if (dotB instanceof Cross) {
                        ((Cross) dotB).setWestStreet(street);
                    } else {
                        ((Margin) dotB).setConnectedLink(street);
                        ((Margin) dotB).setFirstInputLaneIndex(3);
                    }

                } else {//否则B点更靠西
                    street.setWestDot(dotB);
                    street.setEastDot(dotA);

                    if (dotA instanceof Cross) {
                        ((Cross) dotA).setWestStreet(street);
                    } else {
                        ((Margin) dotA).setConnectedLink(street);
                        ((Margin) dotA).setFirstInputLaneIndex(3);
                    }

                    if (dotB instanceof Cross) {
                        ((Cross) dotB).setEastStreet(street);
                    } else {
                        ((Margin) dotB).setConnectedLink(street);
                        ((Margin) dotB).setFirstInputLaneIndex(0);
                    }
                }
                this.linkCache.put(linkID, street);
            }
        }

        for (Cross cross : this.crossCache.values()) {
//            for (int i = 0; i < 5; i++) {
//                if (i < 3) {
//                    LaneUtils.connectLane(cross.getNorthLanes()[i], cross.getNorthRoad().getLanes()[5 - i]);
//                    LaneUtils.connectLane(cross.getEastLanes()[i], cross.getEastStreet().getLanes()[5 - i]);
//
//                    LaneUtils.connectLane(cross.getWestLanes()[i], cross.getWestStreet().getLanes()[i]);
//                    LaneUtils.connectLane(cross.getSouthLanes()[i], cross.getSouthRoad().getLanes()[i]);
//
//                } else {
//                    LaneUtils.connectLane(cross.getNorthRoad().getLanes()[5 - i], cross.getNorthLanes()[i]);
//                    LaneUtils.connectLane(cross.getEastStreet().getLanes()[5 - i], cross.getEastLanes()[i]);
//
//                    LaneUtils.connectLane(cross.getWestStreet().getLanes()[i], cross.getWestLanes()[i]);
//                    LaneUtils.connectLane(cross.getSouthRoad().getLanes()[i], cross.getSouthLanes()[i]);
//                }
//            }
            //North departure
            LaneUtils.connectLane(cross.getNorthRoad().getLanes()[0],cross.getNorthLanes()[0],cross.getWestStreet().getLanes()[5]);
            LaneUtils.connectLane(cross.getNorthRoad().getLanes()[0],cross.getNorthLanes()[1],cross.getWestStreet().getLanes()[4]);
            LaneUtils.connectLane(cross.getNorthRoad().getLanes()[0],cross.getNorthLanes()[2],cross.getWestStreet().getLanes()[3]);

            LaneUtils.connectLane(cross.getNorthRoad().getLanes()[1],cross.getNorthLanes()[3],cross.getSouthRoad().getLanes()[1]);

            LaneUtils.connectLane(cross.getNorthRoad().getLanes()[2],cross.getNorthLanes()[4],cross.getEastStreet().getLanes()[0]);
            LaneUtils.connectLane(cross.getNorthRoad().getLanes()[2],cross.getNorthLanes()[5],cross.getEastStreet().getLanes()[1]);
            LaneUtils.connectLane(cross.getNorthRoad().getLanes()[2],cross.getNorthLanes()[6],cross.getEastStreet().getLanes()[2]);





            //East departure
            LaneUtils.connectLane(cross.getEastStreet().getLanes()[5],cross.getEastLanes()[0],cross.getNorthRoad().getLanes()[5]);
            LaneUtils.connectLane(cross.getEastStreet().getLanes()[5],cross.getEastLanes()[1],cross.getNorthRoad().getLanes()[4]);
            LaneUtils.connectLane(cross.getEastStreet().getLanes()[5],cross.getEastLanes()[2],cross.getNorthRoad().getLanes()[3]);

            LaneUtils.connectLane(cross.getEastStreet().getLanes()[4],cross.getEastLanes()[3],cross.getNorthRoad().getLanes()[4]);

            LaneUtils.connectLane(cross.getEastStreet().getLanes()[3],cross.getEastLanes()[4],cross.getNorthRoad().getLanes()[0]);
            LaneUtils.connectLane(cross.getEastStreet().getLanes()[3],cross.getEastLanes()[5],cross.getNorthRoad().getLanes()[1]);
            LaneUtils.connectLane(cross.getEastStreet().getLanes()[3],cross.getEastLanes()[6],cross.getNorthRoad().getLanes()[2]);



            //South departure
            LaneUtils.connectLane(cross.getSouthRoad().getLanes()[5],cross.getSouthLanes()[0],cross.getNorthRoad().getLanes()[0]);
            LaneUtils.connectLane(cross.getSouthRoad().getLanes()[5],cross.getSouthLanes()[1],cross.getNorthRoad().getLanes()[1]);
            LaneUtils.connectLane(cross.getSouthRoad().getLanes()[5],cross.getSouthLanes()[2],cross.getNorthRoad().getLanes()[2]);

            LaneUtils.connectLane(cross.getSouthRoad().getLanes()[4],cross.getSouthLanes()[3],cross.getNorthRoad().getLanes()[4]);

            LaneUtils.connectLane(cross.getSouthRoad().getLanes()[3],cross.getSouthLanes()[4],cross.getNorthRoad().getLanes()[5]);
            LaneUtils.connectLane(cross.getSouthRoad().getLanes()[3],cross.getSouthLanes()[5],cross.getNorthRoad().getLanes()[4]);
            LaneUtils.connectLane(cross.getSouthRoad().getLanes()[3],cross.getSouthLanes()[6],cross.getNorthRoad().getLanes()[3]);



            //West departure
            LaneUtils.connectLane(cross.getWestStreet().getLanes()[0],cross.getWestLanes()[0],cross.getNorthRoad().getLanes()[0]);
            LaneUtils.connectLane(cross.getWestStreet().getLanes()[0],cross.getWestLanes()[1],cross.getNorthRoad().getLanes()[1]);
            LaneUtils.connectLane(cross.getWestStreet().getLanes()[0],cross.getWestLanes()[2],cross.getNorthRoad().getLanes()[2]);

            LaneUtils.connectLane(cross.getWestStreet().getLanes()[1],cross.getWestLanes()[3],cross.getNorthRoad().getLanes()[1]);

            LaneUtils.connectLane(cross.getWestStreet().getLanes()[2],cross.getWestLanes()[4],cross.getNorthRoad().getLanes()[5]);
            LaneUtils.connectLane(cross.getWestStreet().getLanes()[2],cross.getWestLanes()[5],cross.getNorthRoad().getLanes()[4]);
            LaneUtils.connectLane(cross.getWestStreet().getLanes()[2],cross.getWestLanes()[6],cross.getNorthRoad().getLanes()[3]);

        }

    }


    public Cross[] getCrosses() {
        return crossCache.values().toArray(new Cross[]{});
    }

    public Margin[] getMargins() {
        return marginCache.values().toArray(new Margin[]{});
    }

    public Link[] getLinks() {
        return this.linkCache.values().toArray(new Link[]{});
    }
}
