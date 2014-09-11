package app.models;

import com.sun.org.apache.xerces.internal.dom.AttributeMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;

/**
 * Created by samuelnilsson on 2014-09-10.
 */
public class WeatherForecast {

    private String currentTemperature;

    public WeatherForecast(String latitude, String longitude, String altitude) {

        //YrClient client = new YrClient();
        //Document xml = client.getLocationForecast(latitude, longitude, altitude);

        try {
            File xmlFile = new File("dummy.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document xml = dBuilder.parse(xmlFile);
            this.currentTemperature = parseCurrentTemperatureFromXml(xml);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public String getCurrentTemperature() {
        return this.currentTemperature;
    }

    private String parseCurrentTemperatureFromXml(Document xml) {
        Element documentElement = xml.getDocumentElement();
        Element productElement = (Element) documentElement.getElementsByTagName("product").item(0);

        NodeList nodeList = productElement.getElementsByTagName("time");

        Element firstElement = (Element) nodeList.item(0);
        Element locationElement = (Element) firstElement.getElementsByTagName("location").item(0);
        Element temperatureElement = (Element) locationElement.getElementsByTagName("temperature").item(0);

        return temperatureElement.getAttribute("value");
    }

}
