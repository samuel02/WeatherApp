package app.controllers;

import app.models.AbstractModel;
import app.views.AbstractViewPanel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 * Created by samuelnilsson on 2014-09-10.
 */
public abstract class AbstractController implements PropertyChangeListener {

    private ArrayList<AbstractViewPanel> views;
    private ArrayList<AbstractModel> models;

    public AbstractController() {
        views = new ArrayList<AbstractViewPanel>();
        models = new ArrayList<AbstractModel>();
    }

    public void addModel(AbstractModel model) {
        models.add(model);
        model.addPropertyChangeListener(this);
    }

    public void addView(AbstractViewPanel view) {
        views.add(view);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        for (AbstractViewPanel view: views) {
            view.modelPropertyChange(evt);
        }
    }
}
