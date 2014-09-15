package app.models;

import app.services.YrClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Model for weather forecasts. The forecast is fetched in
 * the constructor from yr.no and parsed from retrieved XML
 * into a HashMap, mapping dates (timestamps) to strings containing
 * the temperature.
 *
 * @author Samuel Nilsson
 */
public class WeatherForecast {

    private HashMap<Date, String> forecast = new HashMap<Date, String>();

    public WeatherForecast(String latitude, String longitude, String altitude) {
        YrClient client = new YrClient();
        Document xml = client.getLocationForecast(latitude, longitude, altitude);
        parseTemperaturesFromXml(xml);
    }


    /**
     * Get temperature for given date/time.
     *
     * @param timestamp to get temperature for
     * @return string containing temperature
     */
    public String getTemperature(Date timestamp) {
        return forecast.get(timestamp);
    }


    /**
     * Get list of timestamps currently in the set,
     * sorted in correct order.
     *
     * @return List of dates
     */
    public Object[] getTimeSeries() {
        Object[] series = forecast.keySet().toArray();
        Arrays.sort(series);
        return series;
    }


    /**
     * Function to parse temperatures from an XML document
     * and add entries to the forecast HashMap. Will only add
     * forecasts for the next 24 h.
     *
     * @param xml containing weather data from yr.no
     */
    private void parseTemperaturesFromXml(Document xml) {
        Date startDate = null;

        Element documentElement = xml.getDocumentElement();
        Element productElement = (Element) documentElement.getElementsByTagName("product").item(0);

        NodeList nodeList = productElement.getElementsByTagName("time");


        for(int i = 0; i < nodeList.getLength(); i++) {
            String temperature;
            Date time;
            Node node = nodeList.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Element locationElement = (Element) element.getElementsByTagName("location").item(0);
                Element temperatureElement = (Element) locationElement.getElementsByTagName("temperature").item(0);
                if(temperatureElement != null) {

                    try {
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        time = dateFormat.parse(element.getAttribute("from"));
                    } catch(ParseException e) {
                        throw new RuntimeException("Unable to parse date from XML.");
                    }

                    if (startDate == null) {
                        startDate = time;
                    }

                    temperature = temperatureElement.getAttribute("value");

                    if((time.getTime()-startDate.getTime())/(60 * 60 * 1000) <= 24) {
                        this.forecast.put(time, temperature);
                    }
                }
            }
        }
    }
}
