package app.controllers;

import app.models.Place;
import app.models.WeatherForecast;

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
    private Place selectedPlace;
    private Date selectedTime;

    public ApplicationController() {
        super();
        this.places = Place.createFromXml(this, "places.xml");
        this.selectedPlace = places.get(0);
        this.selectedPlace.setActive(true);

        Object[] timeSeries = this.selectedPlace.forecast.getTimeSeries();

        if(timeSeries.length > 0) {
            this.selectedTime = (Date) timeSeries[0];
        } else {
            this.selectedTime = null;
        }

    }


    /**
     * Function to get the currently selected place
     *
     * @return place that is currently selected
     */
    public Place getSelectedPlace() {
        return selectedPlace;
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
        return selectedPlace.forecast.getTemperature(selectedTime);
    }


    /**
     * Get currently selected time.
     *
     * @return date representing the selected time
     */
    public Date getSelectedTime() {
        return selectedTime;
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
    public void changeSelectedPlace(int id) {
        this.selectedPlace.setActive(false);
        this.selectedPlace = places.get(id);
        this.selectedPlace.setActive(true);
    }

    /**
     * Function to set cache expiration time
     *
     * @param minutes new value for cache expiration time
     */
    public void changeCacheExpirationTime(int minutes) {
        int oldValue = WeatherForecast.getCacheExpirationTime();
        WeatherForecast.setCacheExpirationTime(minutes);
        propertyChange(new PropertyChangeEvent(this, "cacheExpirationTime", oldValue, minutes));
    }


    /**
     * Function to change selected time in the view. Since
     * the selected time is not stored in a specific model a new
     * property change event is manually created and triggered.
     *
     * @param time to view temperature for
     */
    public void changeSelectedTime(Date time) {
        Date oldValue = this.selectedTime;
        this.selectedTime = time;
        propertyChange(new PropertyChangeEvent(this, "selectedTime", time, oldValue));
    }
}