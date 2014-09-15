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
 * The Place model is the main entity in WeatherApp it represents
 * a geographic location with a name and coordinates and has a
 * forecast.
 *
 * @author Samuel Nilsson
 */
public class Place extends AbstractModel {

    private String name;
    private String altitude;
    private String latitude;
    private String longitude;
    private Boolean active;


    public WeatherForecast forecast;

    public Place(AbstractController controller, String name, String altitude, String latitude, String longitude) {
        controller.addModel(this);
        this.name = name;
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;
        this.forecast = new WeatherForecast(latitude, longitude, altitude);
        this.active = false;
    }


    /**
     * Getter for name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }


    /**
     * Setter for active status, will trigger a property change event
     * and update the forecast for the selected place.
     *
     * @param active
     */
    public void setActive(Boolean active) {
        Boolean oldValue = this.active;
        this.active = active;
        forecast.updateForecastFromYr();
        firePropertyChange("active", oldValue, active);
    }


    /**
     * Getter for active status.
     * @return
     */
    public Boolean getActive() {
        return this.active;
    }


    /**
     * Class method to batch create Places from an xml file.
     *
     * @param controller Controller that the created models should be bound to.
     * @param xmlPath Path to xml file containing the places
     * @return List of places.
     */
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
