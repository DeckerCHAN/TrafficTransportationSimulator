package org.procrastinationpatients.tts.source;

import javafx.geometry.Point2D;
import org.apache.commons.io.FileUtils;
import org.procrastinationpatients.tts.core.Connectible;
import org.procrastinationpatients.tts.core.Cross;
import org.procrastinationpatients.tts.core.Link;
import org.procrastinationpatients.tts.core.Margin;
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
public class ContainersLoader {
    private Node root;
    private Document doc;
    private HashMap<Integer, Cross> crossCache;
    private HashMap<Integer, Margin> marginCache;
    private HashMap<Integer, Link> linkCache;

    public ContainersLoader() {


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


            //先将两个Connectible取出来(有可能是Margin也有可能是Cross)
            Connectible connectibleA, connectibleB;
            if (connectionATypeString.toLowerCase().equals("m")) {
                connectibleA = this.marginCache.get(connectionAID);
            } else {
                connectibleA = this.crossCache.get(connectionAID);
            }

            if (connectionBTypeString.toLowerCase().equals("m")) {
                connectibleB = this.marginCache.get(connectionBID);
            } else {
                connectibleB = this.crossCache.get(connectionBID);
            }

            Link link = new Link(linkID, new Connectible[]{connectibleA, connectibleB});

            connectibleA.addConnection(link);
            connectibleB.addConnection(link);

//            //计算两个Cross的X差绝对值
//            Double differX = Math.abs(dotA.getPosition().getX() - dotB.getPosition().getX());
//            //计算两个Cross的Y差绝对值
//            Double differY = Math.abs(dotA.getPosition().getY() - dotB.getPosition().getY());
//
//            Link link;
//
//            if (differX < differY) { //若X的差比Y的差要小，说明这是一条连接南北的道路
//
//                if (dotA.getPosition().getY() > dotB.getPosition().getY()) { //A点要更靠北的话
//                    link = new Link(linkID,dotA, dotB);
//                    if(dotA instanceof Cross)
//                    {
//                        ((Cross)dotA).setConnectionS(link);
//                    }else
//                    {
//                        ()dotA
//                    }
//
//                    crossB.setConnectionN(link);
//                } else {//否则B点更靠北
//                    link = new Link(crossB, crossA);
//                    crossB.setConnectionS(link);
//                    dotA.setConnectionN(link);
//                }
//            } else { //若Y的差比X的差要小，说明这是一条连接东西的道路
//                if (dotA.getPosition().getX() > crossB.getPosition().getX()) { //A点要更靠西的话
//                    link = new Link(crossA, crossB);
//                    dotA.setConnectionW(link);
//                    crossB.setConnectionE(link);
//                } else {//否则B点更靠西
//                    link = new Link(crossB, dotA);
//                    crossB.setConnectionW(link);
//                    dotA.setConnectionE(link);
//                }
//            }


            //将link加入cache
            this.linkCache.put(linkID, link);
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
