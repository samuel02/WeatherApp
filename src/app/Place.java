package app;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by samuelnilsson on 2014-09-10.
 */
public class Place {

    private String name;
    private String altitude;
    private String latitude;
    private String longitude;


    public Place(String name, String altitude, String latitude, String longitude) {
        this.name = name;
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() { return this.name; }
    public String[] getLocation() {
        String[] location = {this.altitude, this.latitude, this.longitude};
        return location;
    }


    public static List<Place> createFromXml(String xmlPath) {
        List<Place> places = new ArrayList<Place>();

        try {
            File xmlFile = new File(xmlPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            NodeList nodeList = doc.getElementsByTagName("locality");

            for(int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if(node.getNodeType() == Node.ELEMENT_NODE) {

                    Element placeElement = (Element) node;
                    Element locationElement = (Element) placeElement.getElementsByTagName("location").item(0);

                    String name = placeElement.getAttribute("name");
                    String altitude = locationElement.getAttribute("altitude");
                    String latitude = locationElement.getAttribute("latitude");
                    String longitude = locationElement.getAttribute("longitude");


                    places.add(new Place(name, altitude, latitude, longitude));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return places;
    }
}
