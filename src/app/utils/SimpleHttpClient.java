package app.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Simple class for making HTTP requests to given URLs. Currently
 * it only supports get requests.
 *
 * @author Samuel Nilsson
 *
 */
public class SimpleHttpClient {

    /**
     * Public method to make HTTP GET requests to given URL. Will return the full
     * response as a string.
     *
     * @param urlString Full URL to make the request to
     * @return String containing the full response.
     * @throws IOException
     */
    public static String get(String urlString) throws IOException{

        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        if(connection.getResponseCode() != 200) {
            throw new IOException(connection.getResponseMessage());
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        reader.close();

        connection.disconnect();

        return stringBuilder.toString();
    }
}