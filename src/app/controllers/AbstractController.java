package app.controllers;

import app.models.AbstractModel;
import app.views.AbstractViewPanel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by samuelnilsson on 2014-09-10.
 */
public abstract class AbstractController implements PropertyChangeListener {

    private ArrayList<AbstractViewPanel> registeredViews;
    private ArrayList<AbstractModel> registeredModels;

    public AbstractController() {
        registeredViews = new ArrayList<AbstractViewPanel>();
        registeredModels = new ArrayList<AbstractModel>();
    }

    public void addModel(AbstractModel model) {
        registeredModels.add(model);
        model.addPropertyChangeListener(this);
    }

    public void removeModel(AbstractModel model) {
        registeredModels.remove(model);
        model.removePropertyChangeListener(this);
    }

    public void addView(AbstractViewPanel view) {
        registeredViews.add(view);
    }

    public void removeView(AbstractViewPanel view) {
        registeredViews.remove(view);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        for (AbstractViewPanel view: registeredViews) {
            view.modelPropertyChange(evt);
        }
    }

    protected void setModelProperty(String propertyName, Object newValue) {

        for(AbstractModel model: registeredModels) {
            try {

                Method method = model.getClass().
                        getMethod("set"+propertyName, new Class[] {
                                                            newValue.getClass()
                                                       }
                                  );
                method.invoke(model, newValue);
            } catch (Exception e) {
                // Do nothing.
            }
        }
    }
}
