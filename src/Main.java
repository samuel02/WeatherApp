import app.controllers.ApplicationController;
import app.views.ApplicationView;
import app.views.DisplayViewPanel;
import app.views.NavigationViewPanel;


/**
 * Created by samuelnilsson on 2014-09-10.
 */
public class Main {

    public static void main(String[] args) {

        ApplicationController controller = new ApplicationController();
        NavigationViewPanel navigationPanel = new NavigationViewPanel(controller);
        DisplayViewPanel panel = new DisplayViewPanel(controller);
        ApplicationView view = new ApplicationView(panel, navigationPanel);
    }

}
