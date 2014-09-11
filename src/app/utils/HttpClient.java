package app.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by samuelnilsson on 2014-09-10.
 */
public class HttpClient {

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