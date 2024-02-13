import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

    /**
     * The History class is responsible for managing the history screen, including displaying
     * the function and derivative history.
     */

    public class History implements Initializable {
    //Global Variable
    ArrayList<String> functionList = new ArrayList<String>();
    ArrayList<String> functionHistory = new ArrayList<String>();
    ArrayList<String> derivativeHistory = new ArrayList<String>();
    ArrayList<String> derivativeList = new ArrayList<String>();

    static boolean historyPressed;


    @FXML
    private Button clearButton, backButton;
    @FXML
    TextArea fun1, fun2, fun3, fun4, der1, der2 ,der3, der4;
    TextArea[] functionButtons = {fun1, fun2, fun3, fun4};
    TextArea[] derivativeButtons = {der1, der2, der3, der4};

    /**
     * Brings user back to mainscreen fxml when pressed
     */
    @FXML
    public void backClicked() throws IOException {
        App.setRoot("mainscreen");
    }

    /**
     * Initializes the history screen.
     * Sets the initial state of variables and lists.
     * Assigns the history from the history files to the appropriate lists.
     * Displays the function and derivative history on the screen.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    public void initialize(URL location, ResourceBundle resources) {
        // Set the initial state of variables
        historyPressed = false;

        try {
            // Assign the history from the history files to the appropriate lists
            assignHistory();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Define arrays for function buttons and derivative buttons
        TextArea[] functionButtons = { fun1, fun2, fun3, fun4 };
        TextArea[] derivativeButtons = { der1, der2, der3, der4 };

        String function;
        String derivative;

        if ((derivativeList.size() > 3) && (functionList.size() > 3)) {
            // If both function and derivative history have more than 3 entries,
            // display the last 4 entries on the screen
            for (int i = 0; i <= 3; i++) {
                function = functionHistory.get(i);
                derivative = derivativeHistory.get(i);

                functionButtons[i].setText(function);
                derivativeButtons[i].setText(derivative);
            }
        } else if (functionList.size() < 4) {
            // If function history has less than 4 entries,
            // display all the current entries on the screen
            for (int i = 0; i < functionList.size(); i++) {
                function = functionHistory.get(i);
                derivative = derivativeHistory.get(i);
                functionButtons[i].setText(function);
                derivativeButtons[i].setText(derivative);
            }
        }
    }



    /**
     * Assigns the history from the history files to the appropriate lists.
     * Reads the history files, populates the functionList and derivativeList,
     * and selects the last 4 entries for the functionHistory and derivativeHistory lists.
     *
     * @throws FileNotFoundException If the history files are not found.
     */
    public void assignHistory() throws FileNotFoundException {
        // Creating scanners for the two history files
        File file2 = new File("./src/main/resources/lastderivative.txt");
        File file = new File("./src/main/resources/history.txt");

        Scanner saveScanner = new Scanner(file);
        Scanner saveScanner2  = new Scanner (file2);

        // Adding every single line to a list
        while (saveScanner.hasNextLine()) {
            functionList.add(saveScanner.nextLine());
        }
        while (saveScanner2.hasNextLine()) {
            derivativeList.add(saveScanner2.nextLine());
        }

        // Close scanners
        saveScanner.close();
        saveScanner2.close();

        // For function last 4 entries
        for (int i = functionList.size() - 1; i >= Math.max(functionList.size() - 5, 0); i--) {
            functionHistory.add(functionList.get(i));
        }

        // For derivative last 4 entries
        for (int i = derivativeList.size() - 1; i >= Math.max(derivativeList.size() - 5, 0); i--) {
            derivativeHistory.add(derivativeList.get(i));
        }
    }

    /**
     * Clears the contents of the specified file.
     *
     * @param file The path of the file to be cleared.
     * @throws IOException If an error occurs while clearing the file.
     */
    private void clearFile(String file) throws IOException {
        PrintWriter writer = new PrintWriter(file);
        writer.print("");
        writer.close();
    }

        
    /**
     * Handles the action when the clear button is clicked.
     * Clears the contents of the history files, sets the root to the "history" FXML file.
     *
     * @throws IOException If an error occurs while clearing the files or setting the root.
     */
    @FXML
    private void clearClicked() throws IOException {
        clearFile("./src/main/resources/history.txt");
        clearFile("./src/main/resources/lastderivative.txt");
        App.setRoot("history");
    }

        
    /**
     * Handles the action when a function button is clicked.
     * Retrieves the clicked button's text, validates it, and sets it as the function in the App class.
     * Sets the historyPressed flag to true and sets the root to the "mainscreen" FXML file.
     *
     * @param event The mouse event representing the button click.
     * @throws IOException If an error occurs while setting the root.
     */
    @FXML
    private void functionClicked(MouseEvent event) throws IOException {
        TextArea area = (TextArea) event.getSource();
        String buttonText = area.getText();

        if (buttonText.matches("")) {
            // Display an alert for invalid input
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Please click a function.");
            alert.showAndWait();
        } else {
            // Set the clicked button's text as the function in the App class
            App.function = buttonText;
            App.historyPressed = true;
            App.setRoot("mainscreen");
        }
    }


    /**
     * Checks the status of the historyPressed flag.
     *
     * @return {@code true} if historyPressed is true, {@code false} otherwise.
     */
    public static boolean historyStatus() {
        if (historyPressed) {
            return true;
        } else {
            return false;
        }
    }

}