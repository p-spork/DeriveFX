
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

    /**
     * The main class of the application that extends the JavaFX Application class.
     * It manages the initialization and startup of the JavaFX application.
     */

public class App extends Application {

    private static Scene scene;

    public static boolean historyPressed = false;

    static boolean showFunction, showSecDer, darkMode;

    static String function;


        
    /**
     * This method is called when the application is starting.
     * It sets up the main scene and displays it on the stage.
     *
     * @param stage The primary stage for the JavaFX application.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        
        stage.setResizable(false);
        scene = new Scene(loadFXML("mainscreen"), 600, 800);

        // Add CSS stylesheet based on the value of "darkMode"
        if (darkMode) {
            scene.getStylesheets().add("darkstyle.css");
        } else {
            scene.getStylesheets().add("style.css");
        }

        // Set the main scene on the stage and display it
        stage.setScene(scene);
        stage.show();
    }



    /**
     * Sets the root of the scene to the specified FXML file.
     *
     * @param fxml The name of the FXML file to load as the new root.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * changes the CSS stylesheet between light mode and dark mode.
     *
     * @throws IOException If an error occurs while loading the CSS files.
     */
    static void toggleCSS() throws IOException {
        if (!darkMode) {
            // Remove the light mode stylesheet and add the dark mode stylesheet
            scene.getStylesheets().remove("style.css");
            scene.getStylesheets().add("darkstyle.css");
        } else {
            // Remove the dark mode stylesheet and add the light mode stylesheet
            scene.getStylesheets().remove("darkstyle.css");
            scene.getStylesheets().add("style.css");
        }
    }

    /**
     * Loads the specified FXML file and returns its root element as a Parent object.
     *
     * @param fxml The name of the FXML file to load.
     * @return The root element of the loaded FXML file.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        // Create an FXMLLoader instance using the specified FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));

        // Load the FXML file 
        return fxmlLoader.load();
    }

    /**
     * The main entry point for the Java application.
     *
     * @param args The command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        // Launch the JavaFX application
        launch();
    }

    /**
     * Toggles the state of the showFunction variable.
     */
    static void toggleShowFunction() {
        if (showFunction) {
            // If showFunction is currently true, set it to false
            showFunction = false;
        } else {
            // If showFunction is currently false, set it to true
            showFunction = true;
        }
    }


    /**
     * Toggles the state of the showSecDer variable.
     */
    static void toggleShowSecDer() {
        if (showSecDer) {
            // If showSecDer is currently true, set it to false
            showSecDer = false;
        } else {
            // If showSecDer is currently false, set it to true
            showSecDer = true;
        }
    }


    /**
     * Toggles the state of the darkMode variable.
     */
    static void toggleDarkChecked() {
        if (darkMode) {
            // If darkMode is currently true, set it to false
            darkMode = false;
        } else {
            // If darkMode is currently false, set it to true
            darkMode = true;
        }
    }
    /**
     * Retrieves the value of the function string.
     *
     * @return The current value of the function string.
     */
    static String getString() {
        return function;
    }

    }
