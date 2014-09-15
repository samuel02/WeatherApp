import app.controllers.ApplicationController;
import app.views.ApplicationView;
import app.views.MainViewPanel;
import app.views.SidebarViewPanel;


public class Main {

    /**
     * Main function, sets up some system properties
     * related to font rendering. This function also
     * creates a controller and initializes the views.
     *
     * @params args
     */
    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext", "true");

        ApplicationController controller = new ApplicationController();
        SidebarViewPanel navigationPanel = new SidebarViewPanel(controller);
        MainViewPanel panel = new MainViewPanel(controller);
        ApplicationView view = new ApplicationView(panel, navigationPanel);
    }

}
