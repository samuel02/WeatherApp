package app.services;

import app.utils.SimpleHttpClient;
import app.utils.QueryString;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;

/**
 * Service class for interacting with yr.no. Uses the SimpleHttpClient to make HTTP GET
 * requests.
 *
 * @author Samuel Nilsson
 */
public class YrClient {

    private static String YR_API_VERSION = "1.9";
    private static String YR_API_URL = "http://api.yr.no/weatherapi/";


    /**
     * Will make a HTTP GET request to the YR.no API endpoint for getting location
     * forecasts and the parse the response as XML.
     *
     * @param latitude
     * @param longitude
     * @param altitude
     * @return XML document with response data
     * @see SimpleHttpClient
     */
    public Document getLocationForecast(String latitude, String longitude, String altitude) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        Document doc;
        String data = "";

        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Something has gone terribly wrong.");
        }

        try {
            data = SimpleHttpClient.get(buildURL(latitude, longitude, altitude));
            doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(data.getBytes("utf-8"))));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load data!");
        }

        return doc;
    }


    /**
     * Function to build the correct url with all params.
     * The function uses QueryString to build the query part
     * of the URL.
     *
     * @param latitude
     * @param longitude
     * @param altitude
     * @return string containing the full URL
     * @see QueryString
     */
    private String buildURL(String latitude, String longitude, String altitude) {
        QueryString query = new QueryString();
        query.append("lat", latitude);
        query.append("lon", longitude);
        query.append("msl", altitude);

        return YR_API_URL + "locationForecast/" + YR_API_VERSION + "/?" + query;
    }
}
