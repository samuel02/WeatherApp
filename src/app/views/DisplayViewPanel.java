package app.views;


import app.controllers.ApplicationController;
import com.apple.eawt.Application;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

public class DisplayViewPanel extends AbstractViewPanel {
    JLabel temperatureLabel;
    JLabel placeLabel;
    ApplicationController controller;

    public DisplayViewPanel(ApplicationController controller) {
        this.controller = controller;
        this.controller.addView(this);

        Dimension d = new Dimension(500, 300);
        this.setMinimumSize(d);
        this.setPreferredSize(d);
        this.setBackground(Color.LIGHT_GRAY);

        this.temperatureLabel = new JLabel(controller.getCurrentPlace().getCurrentTemperature());
        temperatureLabel.setFont(new Font("Helvetica", 1, 62));

        this.placeLabel = new JLabel(controller.getCurrentPlace().getName());
        placeLabel.setFont(new Font("Helvetica", 1, 42));

        this.add(temperatureLabel);
        this.add(placeLabel);
    }

    public void modelPropertyChange(PropertyChangeEvent evt) {

        System.out.println("modelPropertyChange!");
        System.out.println(evt.getPropertyName());

        if(evt.getPropertyName().equals("active")) {
            temperatureLabel.setText(controller.getCurrentPlace().getCurrentTemperature());
            placeLabel.setText(controller.getCurrentPlace().getName());
        }

        revalidate();
        repaint();
    }
}