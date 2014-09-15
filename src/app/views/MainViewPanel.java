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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * The main view which shows temperature for selected place
 * and time. It also contains the list of times for the user
 * to select a different time.
 *
 * @author Samuel Nilsson
 */
public class MainViewPanel extends AbstractViewPanel {

    JLabel temperatureLabel;
    ApplicationController controller;
    Place activePlace;

    public MainViewPanel(ApplicationController controller) {
        this.controller = controller;
        this.controller.addView(this);
        this.activePlace = this.controller.getCurrentPlace();

        Box box = setupPane();

        addTemperatureLabel(box);
        addTimeSeriesPane(box);

        this.add(box);
    }


    /**
     * Sets up the pane and sets the layout to
     * a vertical BoxLayout.
     *
     * @return box to add content to
     */
    public Box setupPane() {
        Dimension d = new Dimension(500, 400);

        this.setMinimumSize(d);
        this.setPreferredSize(d);
        this.setBackground(Color.WHITE);

        Box box = Box.createVerticalBox();
        box.setMaximumSize(d);

        return box;
    }


    /**
     * Adds a pane containing all times displayed on a horizontal line
     * and scrollable. Hovering over a time will change the selected time
     * to the time currently hovered.
     *
     * @param box
     */
    private void addTimeSeriesPane(Box box) {
        Object[] timeSeries = activePlace.forecast.getTimeSeries();

        JPanel timeSeriesPanel = new JPanel();
        timeSeriesPanel.setBackground(Color.WHITE);
        timeSeriesPanel.setLayout(new BoxLayout(timeSeriesPanel, BoxLayout.PAGE_AXIS));

        Box datesBox = Box.createHorizontalBox();
        Box timeSeriesBox = Box.createHorizontalBox();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String firstDateString = null;

        for(Object d : timeSeries) {
            Date time = (Date) d;

            JLabel timeLabel = new JLabel(timeFormat.format(time));
            timeLabel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
            timeLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
            timeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            timeLabel.setForeground(new Color(51, 51, 51));
            timeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            timeLabel.setPreferredSize(new Dimension(45, 20));
            timeLabel.setMinimumSize(new Dimension(45, 20));
            timeLabel.setMaximumSize(new Dimension(45, 20));

            if(time == controller.getCurrentTime()) {
                timeLabel.setForeground(new Color(50, 150, 213));
            }

            timeLabel.addMouseListener(new TimeSelectMouseListener(controller, time));
            timeSeriesBox.add(timeLabel);
            JLabel dateLabel;
            if((firstDateString == null) || !(firstDateString.equals(dateFormat.format(time)))) {
                firstDateString = dateFormat.format(time);
                dateLabel = new JLabel(firstDateString);

            } else {
                dateLabel = new JLabel(" ");
            }

            dateLabel.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
            dateLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 10));
            dateLabel.setForeground(new Color(51, 51, 51));
            dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            dateLabel.setPreferredSize(new Dimension(45, 10));
            dateLabel.setMinimumSize(new Dimension(45, 10));
            dateLabel.setMaximumSize(new Dimension(45, 10));
            datesBox.add(dateLabel);
        }

        timeSeriesPanel.add(datesBox);
        timeSeriesPanel.add(timeSeriesBox);

        JScrollPane timeScrollPane = new JScrollPane(timeSeriesPanel);
        timeScrollPane.setPreferredSize(new Dimension(500, 30));
        timeScrollPane.setBounds(0, 0, 500, 40);
        timeScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        timeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        timeScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0,0));
        timeScrollPane.setBorder(BorderFactory.createEmptyBorder());

        box.add(timeScrollPane);
    }


    /**
     * Add the temperature label.
     *
     * @param box
     */
    private void addTemperatureLabel(Box box) {
        this.temperatureLabel = new JLabel(controller.getTemperature() + "\u00b0C");
        temperatureLabel.setBorder(BorderFactory.createEmptyBorder(120, 0, 130, 0));
        temperatureLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 92));
        temperatureLabel.setForeground(new Color(50, 150, 213));
        temperatureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        box.add(temperatureLabel);
    }


    /**
     * Specific MouseAdapter for the selecting of time,
     * takes care of the visual hover effect as well as actually
     * calling the changeSelectedTime function on the controller.
     */
    class TimeSelectMouseListener extends MouseAdapter {
        private Date time;
        private ApplicationController controller;

        public TimeSelectMouseListener(ApplicationController controller, Date time) {
            this.controller = controller;
            this.time = time;
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            JLabel label = (JLabel) mouseEvent.getSource();
            Box parent = (Box) label.getParent();

            for(Component c : parent.getComponents()) {
                c.setForeground(new Color(51, 51, 51));
            }

            label.setForeground(new Color(50, 150, 213));
            controller.changeSelectedTime(time);
        }

    }


    /**
     * Function to handle property changes in models. This function only cares
     * about events that change the active status of a place or changes the current
     * time. If one of those events occurs it will update the temperature label with
     * correct temperature from the controller.
     *
     * @param evt
     */
    public void modelPropertyChange(PropertyChangeEvent evt) {

        if(evt.getPropertyName().equals("active") || evt.getPropertyName().equals("currentTime")) {
            temperatureLabel.setText(controller.getTemperature() + "\u00b0C");
        }

        revalidate();
        repaint();
    }
}