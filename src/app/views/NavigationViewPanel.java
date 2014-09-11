package app.views;


import app.controllers.AbstractController;
import app.controllers.ApplicationController;
import app.models.Place;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;

public class NavigationViewPanel extends AbstractViewPanel {

    public NavigationViewPanel(final ApplicationController controller) {
        controller.addView(this);

        FlowLayout layout = new FlowLayout();
        layout.setHgap(0);
        layout.setVgap(0);
        this.setLayout(layout);

        Dimension d = new Dimension(200, 300);
        this.setSize(d);
        this.setPreferredSize(d);
        this.setBackground(Color.WHITE);

        Border appNamePaddingBorder = BorderFactory.createEmptyBorder(30,0,30,0);
        Font appNameFont = new Font("Helvetica Neue", Font.PLAIN, 26);

        JLabel appName = new JLabel("WeatherApp");

        appName.setBorder(appNamePaddingBorder);
        appName.setFont(appNameFont);

        this.add(appName);

        Dimension labelDimension = new Dimension(200, 40);
        Border paddingBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border labelBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
        Font labelFont = new Font("Helvetica Neue", Font.PLAIN, 16);

        for(Place p : controller.getPlaces()) {

            JLabel label = new JLabel(p.getName());

            label.setPreferredSize(labelDimension);
            label.setFont(labelFont);
            label.setBorder(BorderFactory.createCompoundBorder(labelBorder, paddingBorder));

            label.addMouseListener(new NavigationMouseListener(controller, p.getId()));

            this.add(label);
        }
    }

    class NavigationMouseListener extends MouseAdapter {
        private int placeId;
        private ApplicationController controller;

        public NavigationMouseListener(ApplicationController controller, int placeId) {
            this.controller = controller;
            this.placeId = placeId;
        }

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            controller.changeCurrentPlace(placeId);
            System.out.println("Clicked");
        }
    }

    public void modelPropertyChange(PropertyChangeEvent evt) {
        revalidate();
        repaint();
    }
}