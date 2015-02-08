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


public class EntityLoader {
    private Node root;
    private Document doc;
    private File xmlFile;
    private HashMap<Integer, Cross> crossCache;
    private HashMap<Integer, Margin> marginCache;
    private HashMap<Integer, Link> linkCache;
    private HashMap<Integer, Barrier> barrierCache;

    public EntityLoader(File xmlFile) throws IOException {
        this.xmlFile = xmlFile;
        if (!xmlFile.exists()) {
            throw new IOException("File not exists");
        }
        this.linkCache = new HashMap<>();
        this.marginCache = new HashMap<>();
        this.crossCache = new HashMap<>();
        this.barrierCache = new HashMap<>();

    }

    private void resolveLink() {
        NodeList links = ((Element) root).getElementsByTagName("Link");
        for (int i = 0; i < links.getLength(); i++) {
            Element element = ((Element) links.item(i).getChildNodes());
            Integer linkID = Integer.valueOf(element.getElementsByTagName("ObjectID").item(0).getTextContent());
            Character type = Character.valueOf(element.getElementsByTagName("Type").item(0).getTextContent().charAt(0));
            switch (type) {
                case 'R':
                    this.linkCache.put(linkID, new Road(linkID));
                    break;
                case 'S':
                    this.linkCache.put(linkID, new Street(linkID));
                    break;
                default:
                    throw new RuntimeException("Type out of expect!");
            }
        }
    }

    private void resolveMargin() {
        NodeList margins = ((Element) root).getElementsByTagName("MarginalPoint");
        for (int i = 0; i < margins.getLength(); i++) {
            Element element = ((Element) margins.item(i).getChildNodes());
            Integer marginID = Integer.valueOf(element.getElementsByTagName("ObjectID").item(0).getTextContent());
            Character type = Character.valueOf(element.getElementsByTagName("Type").item(0).getTextContent().charAt(0));
            Double positionX = Double.valueOf(element.getElementsByTagName("x").item(0).getTextContent());
            Double positionY = Double.valueOf(element.getElementsByTagName("y").item(0).getTextContent());
            Margin margin = new Margin(marginID, new Point2D(positionX, positionY));
            switch (type) {
                case 'A': {
                    margin.setFirstInputLaneIndex(0);
                    break;
                }
                case 'B': {
                    margin.setFirstInputLaneIndex(3);
                    break;
                }
                default:
                    throw new RuntimeException("Type out of expect!");
            }
            this.marginCache.put(marginID, margin);
        }
    }

    private void resolveCross() {
        NodeList cross = ((Element) root).getElementsByTagName("Cross");
        for (int i = 0; i < cross.getLength(); i++) {
            Element element = ((Element) cross.item(i).getChildNodes());
            Integer crossID = Integer.valueOf(element.getElementsByTagName("ObjectID").item(0).getTextContent());
            Double positionX = Double.valueOf(element.getElementsByTagName("x").item(0).getTextContent());
            Double positionY = Double.valueOf(element.getElementsByTagName("y").item(0).getTextContent());
            this.crossCache.put(crossID, new Cross(crossID, new Point2D(positionX, positionY)));
        }
    }

    private void connectObjs() {
        NodeList links = ((Element) root).getElementsByTagName("Link");
        for (int i = 0; i < links.getLength(); i++) {
            Element element = ((Element) links.item(i).getChildNodes());
            Integer linkID = Integer.valueOf(element.getElementsByTagName("ObjectID").item(0).getTextContent());
            Character type = Character.valueOf(element.getElementsByTagName("Type").item(0).getTextContent().charAt(0));
            Dot dotA, dotB;
            Character tagA = ((Element) element.getElementsByTagName("ConnectA").item(0)).getElementsByTagName("Type").item(0).getTextContent().toUpperCase().charAt(0);
            Integer idA = Integer.valueOf(((Element) element.getElementsByTagName("ConnectA").item(0)).getElementsByTagName("ObjectID").item(0).getTextContent());
            Character tagB = ((Element) element.getElementsByTagName("ConnectB").item(0)).getElementsByTagName("Type").item(0).getTextContent().toUpperCase().charAt(0);
            Integer idB = Integer.valueOf(((Element) element.getElementsByTagName("ConnectB").item(0)).getElementsByTagName("ObjectID").item(0).getTextContent());
            dotA = tagA == 'M' ? this.marginCache.get(idA) : this.crossCache.get(idA);
            dotB = tagB == 'M' ? this.marginCache.get(idB) : this.crossCache.get(idB);

            this.linkCache.get(linkID).setA(dotA);
            this.linkCache.get(linkID).setB(dotB);

            if (dotA instanceof Margin) {
                ((Margin) dotA).setConnectedLink(linkCache.get(linkID));
            } else if (dotA instanceof Cross) {
                if (linkCache.get(linkID) instanceof Road) {
                    ((Cross) dotA).setSouthRoad((Road) linkCache.get(linkID));
                } else {
                    ((Cross) dotA).setEastStreet((Street) linkCache.get(linkID));
                }
            }

            if (dotB instanceof Margin) {
                ((Margin) dotB).setConnectedLink(this.linkCache.get(linkID));
            } else if (dotB instanceof Cross) {

                if (linkCache.get(linkID) instanceof Road) {
                    ((Cross) dotB).setNorthRoad((Road) linkCache.get(linkID));
                } else {
                    ((Cross) dotB).setWestStreet((Street) linkCache.get(linkID));
                }
            }
        }
    }

    private  void  insertBarrier()
    {
        NodeList barriers = ((Element) root).getElementsByTagName("Barrier");
        for (int i = 0; i < barriers.getLength(); i++) {
            Element element = ((Element) barriers.item(i).getChildNodes());
            Integer barrierID = Integer.valueOf(element.getElementsByTagName("ObjectID").item(0).getTextContent());
            Integer linkID = Integer.valueOf(element.getElementsByTagName("LinkID").item(0).getTextContent());
            Integer laneID = Integer.valueOf(element.getElementsByTagName("LaneIndex").item(0).getTextContent());
            Integer start = Integer.valueOf(element.getElementsByTagName("Start").item(0).getTextContent());
            Integer end = Integer.valueOf(element.getElementsByTagName("End").item(0).getTextContent());
            Link recptorLink = this.linkCache.get(linkID);
            recptorLink.getLanes()[laneID].addBarrier(new Barrier(barrierID,start,end));
        }
    }

    private void connectLanes() {
        for (Cross cross : this.crossCache.values()) {
            //North input
            LaneUtils.connectLane(cross.getNorthRoad().getLanes()[0], cross.getNorthLanes()[0], cross.getWestStreet().getLanes()[5]);
            LaneUtils.connectLane(cross.getNorthRoad().getLanes()[0], cross.getNorthLanes()[1], cross.getWestStreet().getLanes()[4]);
            LaneUtils.connectLane(cross.getNorthRoad().getLanes()[0], cross.getNorthLanes()[2], cross.getWestStreet().getLanes()[3]);

            LaneUtils.connectLane(cross.getNorthRoad().getLanes()[1], cross.getNorthLanes()[3], cross.getSouthRoad().getLanes()[1]);

            LaneUtils.connectLane(cross.getNorthRoad().getLanes()[2], cross.getNorthLanes()[4], cross.getEastStreet().getLanes()[2]);
            LaneUtils.connectLane(cross.getNorthRoad().getLanes()[2], cross.getNorthLanes()[5], cross.getEastStreet().getLanes()[1]);
            LaneUtils.connectLane(cross.getNorthRoad().getLanes()[2], cross.getNorthLanes()[6], cross.getEastStreet().getLanes()[0]);


            //East input
            LaneUtils.connectLane(cross.getEastStreet().getLanes()[5], cross.getEastLanes()[0], cross.getNorthRoad().getLanes()[5]);
            LaneUtils.connectLane(cross.getEastStreet().getLanes()[5], cross.getEastLanes()[1], cross.getNorthRoad().getLanes()[4]);
            LaneUtils.connectLane(cross.getEastStreet().getLanes()[5], cross.getEastLanes()[2], cross.getNorthRoad().getLanes()[3]);

            LaneUtils.connectLane(cross.getEastStreet().getLanes()[4], cross.getEastLanes()[3], cross.getWestStreet().getLanes()[4]);

            LaneUtils.connectLane(cross.getEastStreet().getLanes()[3], cross.getEastLanes()[4], cross.getSouthRoad().getLanes()[2]);
            LaneUtils.connectLane(cross.getEastStreet().getLanes()[3], cross.getEastLanes()[5], cross.getSouthRoad().getLanes()[1]);
            LaneUtils.connectLane(cross.getEastStreet().getLanes()[3], cross.getEastLanes()[6], cross.getSouthRoad().getLanes()[0]);


            //South input
            LaneUtils.connectLane(cross.getSouthRoad().getLanes()[5], cross.getSouthLanes()[0], cross.getEastStreet().getLanes()[0]);
            LaneUtils.connectLane(cross.getSouthRoad().getLanes()[5], cross.getSouthLanes()[1], cross.getEastStreet().getLanes()[1]);
            LaneUtils.connectLane(cross.getSouthRoad().getLanes()[5], cross.getSouthLanes()[2], cross.getEastStreet().getLanes()[2]);

            LaneUtils.connectLane(cross.getSouthRoad().getLanes()[4], cross.getSouthLanes()[3], cross.getNorthRoad().getLanes()[4]);

            LaneUtils.connectLane(cross.getSouthRoad().getLanes()[3], cross.getSouthLanes()[4], cross.getWestStreet().getLanes()[3]);
            LaneUtils.connectLane(cross.getSouthRoad().getLanes()[3], cross.getSouthLanes()[5], cross.getWestStreet().getLanes()[4]);
            LaneUtils.connectLane(cross.getSouthRoad().getLanes()[3], cross.getSouthLanes()[6], cross.getWestStreet().getLanes()[5]);


            //West input
            LaneUtils.connectLane(cross.getWestStreet().getLanes()[0], cross.getWestLanes()[0], cross.getSouthRoad().getLanes()[0]);
            LaneUtils.connectLane(cross.getWestStreet().getLanes()[0], cross.getWestLanes()[1], cross.getSouthRoad().getLanes()[1]);
            LaneUtils.connectLane(cross.getWestStreet().getLanes()[0], cross.getWestLanes()[2], cross.getSouthRoad().getLanes()[2]);

            LaneUtils.connectLane(cross.getWestStreet().getLanes()[1], cross.getWestLanes()[3], cross.getEastStreet().getLanes()[1]);

            LaneUtils.connectLane(cross.getWestStreet().getLanes()[2], cross.getWestLanes()[4], cross.getNorthRoad().getLanes()[3]);
            LaneUtils.connectLane(cross.getWestStreet().getLanes()[2], cross.getWestLanes()[5], cross.getNorthRoad().getLanes()[4]);
            LaneUtils.connectLane(cross.getWestStreet().getLanes()[2], cross.getWestLanes()[6], cross.getNorthRoad().getLanes()[5]);
        }
    }

    public void LoadFromFile() throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setIgnoringComments(true);
        dbFactory.setIgnoringElementContentWhitespace(true);
        dbFactory.setValidating(false);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(FileUtils.openInputStream(this.xmlFile));
        doc.getDocumentElement().normalize();
        root = doc.getElementsByTagName("Data").item(0);

        this.resolveLink();
        this.resolveMargin();
        this.resolveCross();
        this.connectObjs();
        this.connectLanes();
        this.insertBarrier();
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

    public Barrier[] getBarriers() {
        return this.barrierCache.values().toArray(new Barrier[]{});
    }
}
