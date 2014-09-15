package app.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Simple class for building string of params used
 * for the query part when building URLs. E.g:
 *
 * example.org?foo=bar&baz=foobar
 *
 * @author Samuel Nilsson
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
