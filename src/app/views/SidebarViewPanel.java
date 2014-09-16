package app.views;


import app.controllers.ApplicationController;
import app.models.Place;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;

/**
 * This is the sidebar view panel containing the navigation,
 * i.e. choosing between the different places.
 *
 * @author Samuel Nilsson
 */
public class SidebarViewPanel extends AbstractViewPanel {

    ApplicationController controller;
    Box navigationElements;

    public SidebarViewPanel(final ApplicationController controller) {
        this.controller = controller;
        this.controller.addView(this);

        JPanel pane = setupPane();

        addPlacesToNavigation(pane);
        addCacheControl(pane);
    }

    /**
     * Sets up the pane with correct colors, dimensions
     * and layout.
     *
     * @return
     */
    public JPanel setupPane() {
        FlowLayout layout = new FlowLayout();
        layout.setHgap(0);
        layout.setVgap(0);
        this.setLayout(layout);

        Dimension d = new Dimension(200, 400);
        this.setSize(d);
        this.setPreferredSize(d);
        this.setBackground(new Color(244, 244, 244));
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(204, 204, 204)));

        return this;
    }

    /**
     * Adds list of places for the navigation.
     *
     * @param pane
     */
    private void addPlacesToNavigation(JPanel pane) {
        navigationElements = Box.createVerticalBox();
        navigationElements.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(204, 204, 204)));


        JLabel placesTitle = createTitleLabel("PLACES");
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

        pane.add(navigationElements);
    }


    /**
     *
     */
    private void addCacheControl(JPanel pane) {
        JLabel cacheControlLabel = createTitleLabel("CACHE EXPIRY TIME");
        cacheControlLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        cacheControlLabel.setBorder(BorderFactory.createEmptyBorder(30, 5, 5, 0));
        cacheControlLabel.setPreferredSize(new Dimension(200, 50));
        pane.add(cacheControlLabel);

        String[] expiryTimes = { "1 minutes", "2 minutes", "3 minutes", "5 minutes", "8 minutes", "13 minutes", "21 minutes", "34 minutes", "55 minutes" };

        JComboBox timeSelectBox = new JComboBox(expiryTimes);
        timeSelectBox.setPreferredSize(new Dimension(200, 25));
        timeSelectBox.setSelectedIndex(0);

        timeSelectBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JComboBox comboBox = (JComboBox) event.getSource();

                String selected = (String) comboBox.getSelectedItem();
                int selectedNumber = Integer.parseInt(selected.replaceAll("[\\D]", ""));
                controller.changeCacheExpirationTime(selectedNumber);
            }
        });

        pane.add(timeSelectBox);

        JLabel infoText = new JLabel("<html>Select how many minutes old you allow the data to get before new data is fetched from yr.no.</html>");
        infoText.setFont(new Font("Helvetica Neue", Font.PLAIN, 11));
        infoText.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 0));
        infoText.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoText.setPreferredSize(new Dimension(200, 50));

        pane.add(infoText);
    }


    private JLabel createTitleLabel(String label) {
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 10));
        jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        jLabel.setBorder(BorderFactory.createEmptyBorder(20, 5, 5, 0));
        return jLabel;
    }



    /**
     * Handles property change events. This function is only concerned
     * about the cases when a model has changed its active status. In those
     * cases it will set the active style to the correct element in the list
     * and reset the style for all other elements.
     *
     * @param evt
     */
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

        if(evt.getPropertyName().equals("cacheExpirationTime")) {
            System.out.println("Changed cache expiration time to: " + evt.getNewValue());
        }

        revalidate();
        repaint();
    }


    /**
     * MouseAdapter for handling clicks and hover effects for
     * navigation elements.
     */
    class NavigationMouseListener extends MouseAdapter {
        private int placeId;
        private ApplicationController controller;

        public NavigationMouseListener(ApplicationController controller, int placeId) {
            this.controller = controller;
            this.placeId = placeId;
        }


        /**
         * Changes current place to the id stored in
         * the private variable placeId.
         *
         * @param mouseEvent
         */
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            controller.changeSelectedPlace(placeId);
        }


        /**
         * Add hover style when hovering over
         * navigation elements.
         *
         * @param mouseEvent
         */
        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            JLabel label = (JLabel) mouseEvent.getSource();
            if(!label.getBackground().equals(new Color(50, 150, 213))) {
                label.setBackground(new Color(219, 219, 219));
            }
        }


        /**
         * Remove hover style when leaving a navigation
         * element
         *
         * @param mouseEvent
         */
        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            JLabel label = (JLabel) mouseEvent.getSource();
            if(!label.getBackground().equals(new Color(50, 150, 213))) {
                label.setBackground(new Color(232, 232, 232));
            }
        }
    }
}