package app.models;

import app.controllers.AbstractController;
import app.controllers.ApplicationController;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Created by samuelnilsson on 2014-09-10.
 */
public class Place extends AbstractModel {
    private static int instanceCounter = -1;

    private int id;
    private String name;
    private String altitude;
    private String latitude;
    private String longitude;
    private Boolean active;


    public WeatherForecast forecast;

    public Place(AbstractController controller, String name, String altitude, String latitude, String longitude) {
        controller.addModel(this);
        instanceCounter++;
        this.id = instanceCounter;
        this.name = name;
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;
        this.forecast = new WeatherForecast(latitude, longitude, altitude);
        this.active = false;
    }

    public String getName() {
        return name;
    }

    public void setActive(Boolean active) {
        Boolean oldValue = this.active;
        this.active = active;
        firePropertyChange("active", oldValue, active);
    }

    public Boolean getActive() {
        return this.active;
    }

    public int getId() {
        return this.id;
    }

    public static List<Place> createFromXml(AbstractController controller, String xmlPath) {
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

                    Place newPlace = new Place(controller, name, altitude, latitude, longitude);
                    places.add(newPlace);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return places;
    }
}