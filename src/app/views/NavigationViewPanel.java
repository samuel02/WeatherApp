package app.views;


import app.controllers.ApplicationController;
import app.models.Place;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;

public class NavigationViewPanel extends AbstractViewPanel {
    ApplicationController controller;
    Box navigationElements;

    public NavigationViewPanel(final ApplicationController controller) {
        this.controller = controller;
        this.controller.addView(this);
        setupPane();
    }

    public void setupPane() {
        FlowLayout layout = new FlowLayout();
        layout.setHgap(0);
        layout.setVgap(0);
        this.setLayout(layout);

        Dimension d = new Dimension(200, 400);
        this.setSize(d);
        this.setPreferredSize(d);
        this.setBackground(new Color(244, 244, 244));
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(204, 204, 204)));

        addPlacesToNavigation();
    }

    private void addLogo() throws IOException {
        BufferedImage logo = ImageIO.read(new File("logo.png"));
        JLabel logoLabel = new JLabel(new ImageIcon(logo));
        this.add(logoLabel);
    }

    private void addPlacesToNavigation() {
        navigationElements = Box.createVerticalBox();
        navigationElements.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(204, 204, 204)));


        JLabel placesTitle = new JLabel("PLACES");
        placesTitle.setFont(new Font("Helvetica Neue", Font.BOLD, 10));
        placesTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        placesTitle.setBorder(BorderFactory.createEmptyBorder(20, 5, 5, 0));
        navigationElements.add(placesTitle);

        Dimension labelDimension = new Dimension(200, 30);
        Border paddingBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border labelBorder = BorderFactory.createMatteBorder(1, 0, 0, 1, new Color(204, 204, 204));
        Font labelFont = new Font("Helvetica Neue", Font.PLAIN, 12);
        Color labelBackgroundColor = new Color(232, 232, 232);
        Color labelColor = new Color(51, 51, 51);

        for(Place p : controller.getPlaces()) {

            JLabel label = new JLabel(p.getName());

            label.setPreferredSize(labelDimension);
            label.setMaximumSize(labelDimension);
            label.setFont(labelFont);
            label.setBorder(BorderFactory.createCompoundBorder(labelBorder, paddingBorder));
            label.setCursor(new Cursor(Cursor.HAND_CURSOR));

            if (p.getActive()) {
                label.setBackground(new Color(50, 150, 213));
                label.setForeground(Color.WHITE);
            } else {
                label.setBackground(labelBackgroundColor);
                label.setForeground(labelColor);
            }


            label.setOpaque(true);
            label.addMouseListener(new NavigationMouseListener(controller, p.getId()));

            navigationElements.add(label);
        }

        this.add(navigationElements);
    }

    public void modelPropertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("active")) {
            Boolean activated = (Boolean) evt.getNewValue();
            if(activated) {
                Place activePlace = (Place) evt.getSource();
                for(Component c: navigationElements.getComponents()) {
                    JLabel label = (JLabel) c;

                    if (label.getText().equals(activePlace.getName())) {
                        label.setBackground(new Color(50, 150, 213));
                        label.setForeground(Color.WHITE);
                    } else {
                        label.setBackground(new Color(232, 232, 232));
                        label.setForeground(new Color(51, 51, 51));
                    }
                }

            }
        }

        revalidate();
        repaint();
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
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            JLabel label = (JLabel) mouseEvent.getSource();
            if(!label.getBackground().equals(new Color(50, 150, 213))) {
                label.setBackground(new Color(219, 219, 219));
            }
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            JLabel label = (JLabel) mouseEvent.getSource();
            if(!label.getBackground().equals(new Color(50, 150, 213))) {
                label.setBackground(new Color(232, 232, 232));
            }
        }
    }
}