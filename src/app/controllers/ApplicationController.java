package app.controllers;

import app.models.Place;

import java.util.List;

/**
 * Created by samuelnilsson on 2014-09-10.
 */
public class ApplicationController extends AbstractController{

    private List<Place> places;
    private Place currentPlace;

    public ApplicationController() {
        super();
        this.places = Place.createFromXml(this, "places.xml");
        this.currentPlace = places.get(0);
        this.currentPlace.setActive(true);
    }

    public Place getCurrentPlace() {
        return currentPlace;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void changeCurrentPlace(int id) {
        this.currentPlace.setActive(false);
        this.currentPlace = places.get(id);
        this.currentPlace.setActive(true);
        System.out.println("changeCurrentPlace");
    }
}