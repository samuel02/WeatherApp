package app.controllers;

import app.models.Place;

import java.beans.PropertyChangeEvent;
import java.util.Date;
import java.util.List;

/**
 * Created by samuelnilsson on 2014-09-10.
 */
public class ApplicationController extends AbstractController{

    private List<Place> places;
    private Place currentPlace;
    private Date currentTime;

    public ApplicationController() {
        super();
        this.places = Place.createFromXml(this, "places.xml");
        this.currentPlace = places.get(0);
        this.currentPlace.setActive(true);
        this.currentTime = (Date) this.currentPlace.forecast.getTimeSeries()[0];
    }

    public Place getCurrentPlace() {
        return currentPlace;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public String getTemperature() {
        return currentPlace.forecast.getTemperature(currentTime);
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void changeCurrentPlace(int id) {
        this.currentPlace.setActive(false);
        this.currentPlace = places.get(id);
        this.currentPlace.setActive(true);
    }

    public void changeSelectedTime(Date time) {
        Date oldValue = this.currentTime;
        this.currentTime = time;
        propertyChange(new PropertyChangeEvent(this, "currentTime", time, oldValue));
    }
}