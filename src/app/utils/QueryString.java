package app.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by samuelnilsson on 2014-09-10.
 */
public class QueryString {
    private StringBuilder query = new StringBuilder();

    public QueryString() {
    }

    public void append(String name, String value) {
        try {
            query.append("&");
            query.append(URLEncoder.encode(name, "UTF-8"));
            query.append("=");
            query.append(URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("VM does not support UTF-8.");
        }
    }

    public String getQuery() {
        return query.toString();
    }

    public String toString() {
        return getQuery();
    }
}
