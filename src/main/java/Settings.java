
// Imports
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;

/**
 * A class representing a settings page.
 * Implements the Initializable for initializing the settings page.
 */
public class Settings implements Initializable {

    // Variables for the settings checkboxes
    @FXML
    CheckBox graph2ndSetting, graphSetting, darkSetting;

    /**
     * Initializes the settings page.
     * 
     * @param location  The URL location of the FXML file.
     * @param resources The ResourceBundle for the FXML file.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set the initial values for the checkboxes based on the application's current
        // settings
        graph2ndSetting.setSelected(App.showSecDer);
        graphSetting.setSelected(App.showFunction);
        darkSetting.setSelected(App.darkMode);
    }

    // Temporary variables to store the initial settings
    boolean tempShowFunction = App.showFunction;
    boolean tempShowSecDer = App.showSecDer;
    boolean tempDarkChecked = App.darkMode;

    /**
     * Handles the event when the show function button is checked/unchecked.
     * 
     * @throws IOException If an I/O error occurs while navigating to the main
     *                     screen.
     */
    @FXML
    public void showFunction() {
        // Change temporary showfunction to opposite of current value
        tempShowFunction = !tempShowFunction;
    }

    /**
     * Handles the event when the show 2nd derivative button is checked/unchecked.
     * 
     * @throws IOException If an I/O error occurs while navigating to the main
     *                     screen.
     */
    @FXML
    public void showSecDer() {
        // Change temporary showsecder to opposite of current value
        tempShowSecDer = !tempShowSecDer;
    }

    /**
     * Handles the event when the dark button is checked/unchecked.
     * 
     * @throws IOException If an I/O error occurs while navigating to the main
     *                     screen.
     */
    @FXML
    public void darkChecked() throws IOException {
        // Change temporary darkChecked to opposite of current value
        tempDarkChecked = !tempDarkChecked;
    }

    /**
     * Handles the event when the back button is clicked.
     * 
     * @throws IOException If an I/O error occurs while navigating to the main
     *                     screen.
     */
    @FXML
    public void backClicked() throws IOException {
        // Set the root view to the "mainscreen" page
        App.setRoot("mainscreen");
    }

    /**
     * Handles the event when the save button is clicked.
     * 
     * @throws IOException If an I/O error occurs while saving settings to a file.
     */
    @FXML
    public void saveClicked() throws IOException {
        // Update the show function and showsecder setting
        App.showFunction = tempShowFunction;
        App.showSecDer = tempShowSecDer;

        // Check if the dark mode setting has changed
        if (tempDarkChecked != App.darkMode) {
            // Toggle the CSS for dark mode
            App.toggleCSS();
        }

        // Update the dark mode setting
        App.darkMode = tempDarkChecked;

        // Save the settings to a file
        try (PrintWriter out = new PrintWriter("./src/main/resources/settings.txt")) {
            String settings = App.showFunction + "." + App.showSecDer + "." + App.darkMode;
            out.println(settings);
        }
    }

}
