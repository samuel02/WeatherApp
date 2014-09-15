package app.controllers;

import app.models.AbstractModel;
import app.views.AbstractViewPanel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 * Superclass for controllers implemented according to
 * the MVC pattern. The abstract controller provides functionality
 * for binding models and views to the controller and control the
 * event flow between the entities.
 *
 * @author Samuel Nilsson
 */
public abstract class AbstractController implements PropertyChangeListener {

    private ArrayList<AbstractViewPanel> views;
    private ArrayList<AbstractModel> models;

    public AbstractController() {
        views = new ArrayList<AbstractViewPanel>();
        models = new ArrayList<AbstractModel>();
    }


    /**
     * Register a model in the controller and
     * setup a binding for listening to events,
     * i.e. changes made in the model.
     *
     * @param model the model to register
     */
    public void addModel(AbstractModel model) {
        models.add(model);
        model.addPropertyChangeListener(this);
    }


    /**
     * Register a view in the controller in order
     * to know what views should be notified in the case
     * of a property change in a registered model.
     *
     * @param view the view to register
     */
    public void addView(AbstractViewPanel view) {
        views.add(view);
    }


    /**
     * Function used for dispatching changes in models
     * to each registered view.
     *
     * @param evt this event is created in the model that changes and is passed on to all registered views
     */
    public void propertyChange(PropertyChangeEvent evt) {
        for (AbstractViewPanel view: views) {
            view.modelPropertyChange(evt);
        }
    }
}
