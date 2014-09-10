import app.Place;

import java.util.List;


/**
 * Created by samuelnilsson on 2014-09-10.
 */
public class Main {

    public static void main(String[] args) {
        String placesXmlPath = args[0];
        List<Place> places = Place.createFromXml(placesXmlPath);

        for(Place p : places) {
            System.out.println("Name: " + p.getName());
            for(String s : p.getLocation()) {
                System.out.println(s);
            }
        }


    }

}
