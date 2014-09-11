package app.services;

import app.utils.HttpClient;
import app.utils.QueryString;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;

/**
 * Created by samuelnilsson on 2014-09-10.
 */
public class YrClient {
    private static String YR_API_VERSION = "1.9";
    private static String YR_API_URL = "http://api.yr.no/weatherapi/";

    public YrClient() {
    }

    public Document getLocationForecast(String latitude, String longitude, String altitude) {
        DocumentBuilderFactory dbFactory;
        DocumentBuilder dBuilder = null;
        Document doc;
        String data = "";

        try {
            dbFactory = DocumentBuilderFactory.newInstance();
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        doc = dBuilder.newDocument();

        try {
            data = HttpClient.get(buildURL(latitude, longitude, altitude));
            doc = dBuilder.parse(new InputSource(new ByteArrayInputStream(data.getBytes("utf-8"))));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load data!");
        }

        return doc;
    }

    private String buildURL(String latitude, String longitude, String altitude) {
        QueryString query = new QueryString();
        query.append("lat", latitude);
        query.append("lon", longitude);
        query.append("msl", altitude);

        String url = YR_API_URL + "locationForecast/" + YR_API_VERSION + "/?" + query;
        return url;
    }
}
