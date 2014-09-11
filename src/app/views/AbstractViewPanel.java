package app.views;

import java.beans.PropertyChangeEvent;
import javax.swing.JPanel;


public abstract class AbstractViewPanel extends JPanel {
    public abstract void modelPropertyChange(PropertyChangeEvent evt);
}