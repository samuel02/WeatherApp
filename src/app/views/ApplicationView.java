package app.views;

import javax.swing.*;
import java.awt.*;

/**
 * Created by samuelnilsson on 2014-09-10.
 */
public class ApplicationView extends JFrame {

    public ApplicationView(DisplayViewPanel displayViewPanel, NavigationViewPanel navigationPanel) {
        setTitle("WeatherApp");
        getContentPane().add(displayViewPanel, BorderLayout.CENTER);
        getContentPane().add(navigationPanel, BorderLayout.WEST);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setResizable(false);
    }

}
