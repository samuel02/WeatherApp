package app.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Superclass for all models. Implements event handling with
 * a propertyChangeSupport object. Makes it possible to add listeners
 * to models as well as firing events from them. It also gives support for
 * automatically incremented ids which can be used for keeping track of and
 * identifying model instances during runtime.
 *
 * @author Samuel Nilsson
 */
public abstract class AbstractModel {

    private static int instanceCounter = -1;
    private int id;

    protected PropertyChangeSupport propertyChangeSupport;

    public AbstractModel() {
        instanceCounter++;
        this.id = instanceCounter;
        propertyChangeSupport = new PropertyChangeSupport(this);
    }


    /**
     * Getter for the instance id
     *
     * @return integer representing the instance
     */
    public int getId() {
        return this.id;
    }


    /**
     * Register a listener to the the model, this will be a controller
     * that wants to listen to changes done to the model.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }


    /**
     * Function to trigger a property change event from the model.
     * This function must be manually called whenever the model
     * wishes to notify others of a change.
     *
     * @param propertyName a string to describe the name of the property name that was changed,
     *                     should in most cases be equal to the name of the variable containing
     *                     the property.
     * @param oldValue the value of the property before it was changed
     * @param newValue the value that property was changed to
     */
    public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
}
