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

    private YrClient client = new YrClient();
    private String latitude;
    private String longitude;
    private String altitude;

    private HashMap<Date, String> forecast = new HashMap<Date, String>();

    public WeatherForecast(String latitude, String longitude, String altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
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
     * Update the forecast with data from YR.no.
     *
     * @param forceUpdate Force a new fetch of data regardless of cache settings
     */
    public void updateForecastFromYr(Boolean forceUpdate) {
        if(!forceUpdate && !forecast.isEmpty()) {
            return;
        }

        Document xml = this.client.getLocationForecast(this.latitude, longitude, altitude);
        this.forecast = parseTemperaturesFromXml(xml);
    }


    /**
     * Update the forecast with data from YR.no.
     */
    public void updateForecastFromYr() {
        updateForecastFromYr(false);
    }


    /**
     * Function to parse temperatures from an XML document. Will load
     * predicted temperatures for the next 24 hours and return the data
     * as a HashMap, mapping dates to temperatures.
     *
     * @param xml containing weather data from yr.no
     */
    private HashMap<Date, String> parseTemperaturesFromXml(Document xml) {
        Date startDate = null;
        HashMap<Date, String> newForecast = new HashMap<Date, String>();

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
                        newForecast.put(time, temperature);
                    }
                }
            }
        }

        return newForecast;
    }
}
