package app.views;

import javax.swing.*;
import java.awt.*;

/**
 * Main view for WeatherApp. It sets up the main window as well
 * as creating the panes and laying them out in the window.
 *
 * @author Samuel Nilsson
 */
public class ApplicationView extends JFrame {

    public ApplicationView(MainViewPanel mainViewPanel, SidebarViewPanel navigationPanel) {
        setTitle("WeatherApp");
        getContentPane().add(mainViewPanel, BorderLayout.CENTER);
        getContentPane().add(navigationPanel, BorderLayout.WEST);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        setResizable(false);
    }

}
