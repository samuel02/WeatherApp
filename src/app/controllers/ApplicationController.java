package app.controllers;

import app.models.Place;

import java.beans.PropertyChangeEvent;
import java.util.Date;
import java.util.List;

/**
 * Main controller for WeatherApp. Will at instantiation load
 * and create all place models from the xml file "places.xml".
 *
 * @author Samuel Nilsson
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

        Object[] timeSeries = this.currentPlace.forecast.getTimeSeries();

        if(timeSeries.length > 0) {
            this.currentTime = (Date) timeSeries[0];
        } else {
            this.currentTime = null;
        }

    }


    /**
     * Function to get the currently selected place
     *
     * @return place that is currently selected
     */
    public Place getCurrentPlace() {
        return currentPlace;
    }


    /**
     * Get list of all places.
     *
     * @return list of all places
     */
    public List<Place> getPlaces() {
        return places;
    }


    /**
     * Get temperature for the currently selected time
     * from the forecast for the the currently selected place.
     *
     * @return string containing temperature in degrees
     */
    public String getTemperature() {
        return currentPlace.forecast.getTemperature(currentTime);
    }


    /**
     * Get currently selected time.
     *
     * @return date representing the selected time
     */
    public Date getCurrentTime() {
        return currentTime;
    }


    /**
     * Function to select a new place to view. Does so
     * by setting the currently selected place to inactive and
     * then setting the new place to active. This will trigger a
     * property change event that will eventually cause the view to
     * be re-rendered.
     *
     * @param id of the the place to change to
     */
    public void changeCurrentPlace(int id) {
        this.currentPlace.setActive(false);
        this.currentPlace = places.get(id);
        this.currentPlace.setActive(true);
    }


    /**
     * Function to change selected time in the view. Since
     * the selected time is not stored in a specific model a new
     * property change event is manually created and triggered.
     *
     * @param time to view temperature for
     */
    public void changeSelectedTime(Date time) {
        Date oldValue = this.currentTime;
        this.currentTime = time;
        propertyChange(new PropertyChangeEvent(this, "currentTime", time, oldValue));
    }
}