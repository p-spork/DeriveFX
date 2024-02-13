import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * This class is used to display the instructions for the user to use the program.
 */
public class Help  implements Initializable {
    @FXML
    private TextArea instructions;

    /**
     * Initializes the help screen.
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instructions.setEditable(false); // make the text area uneditable
    }

    /**
     * Brings user back to mainscreen fxml when pressed
     * @throws IOException
     */
    @FXML
    public void backClicked() throws IOException {
        App.setRoot("mainscreen"); // set the scene to mainscreen
    }

}