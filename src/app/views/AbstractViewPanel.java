package app.views;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import javax.swing.JPanel;


/**
 * Superclass for all view panels in the application. The class
 * is just used as a "placeholder" for methods that all panels should
 * implement as well as inheriting from JPanel.
 *
 * @author Samuel Nilsson
 */
public abstract class AbstractViewPanel extends JPanel {

    /**
     * This function is triggered from the controller
     * if an event occurs in a model. The function is then
     * responsible for handling the different types of events
     * and adjust the view accordingly.
     *
     * @param evt
     */
    public abstract void modelPropertyChange(PropertyChangeEvent evt);


    /**
     * Simple method to setup the basic properties and layout for
     * the pane.
     *
     * @return a component, mostly a JPanel or a Box that will used
     *         when adding more content to the main panel.
     */
    public abstract Component setupPane();
}