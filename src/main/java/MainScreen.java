import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

/**
 * This class is the controller for the main screen. It contains all the methods that control the graph, the text input and output, and the buttons
 * to other screens.
 * 
 * @author Shriniket, Evan, Peter
 */
public class MainScreen implements Initializable {
    
    boolean helpButtonState;
    @FXML
    private Pane root;
    @FXML
    private Button helpButton, settingsButton, historyButton, submitButton, loadButton, clearButton;
    @FXML
    public TextField textInput;
    @FXML
    private Label textOutput, coordinateLabel;
    @FXML
    private LineChart<Double, Double> lineGraph;
    @FXML
    private Circle coordinateCircle;

    /*
     * The following variables are used to calculate the coordinates of the mouse on the graph. They are used in the checkCoords method.
     * centerX and centerY are the coordinates of the center of the graph with respect to the scene.
     * rightX and leftX are the coordinates of the right and left edges of the graph with respect to the scene.
     * topY and bottomY are the coordinates of the top and bottom edges of the graph with respect to the scene.
     * incrementX and incrementY are the increments between each unit on the x and y axes respectively.
     * The increment was calculated by doing incrementX = (rightX - leftX) / 20 and incrementY = (bottomY - topY) / 20
     */
    double centerX = 302;
    double centerY = 607;
    double rightX = 521;
    double leftX = 83;
    double topY = 457;
    double bottomY = 757;
    double incrementX = 21.9;
    double incrementY = 15;

    // Create a new instance of MyGraph
    private MyGraph mathsGraph;

    /**
     * This method is called when the program is first run. It sets up the graph and the text input and output.
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mathsGraph = new MyGraph(lineGraph, 10);// Create a new instance of MyGraph with the lineGraph and a scale of 10
        
        lineGraph.setCreateSymbols(false); // Disable symbol creation on the line graph
        lineGraph.setOnMouseClicked(this::handle); // Set the mouse click event handler for the line graph
      
        String fileContent = "";

        // Creating object of FileReader and BufferedReader
        FileReader fr = null;
        try {
            fr = new FileReader("./src/main/resources/settings.txt"); // Open the settings file for reading
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fr);

        try {
            fileContent = br.readLine(); // Read the first line of the settings file
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> list = Arrays.asList(fileContent.split("\\.")); // Parse and set showFunction flag based on the first value in the settings file
        
        if (Boolean.parseBoolean(list.get(0))) {
            App.showFunction = true;  // Set showFunction to true if the value is "true"
        } else {
            App.showFunction = false; // Set showFunction to false if the value is "false"
        }
        if (Boolean.parseBoolean(list.get(1))) {
            App.showSecDer = true;  // Set showSecDer to true if the value is "true"
        } else {
            App.showSecDer = false; // Set showSecDer to false if the value is "false"
        }
        if (Boolean.parseBoolean(list.get(2))) {
            App.darkMode = true;  // Set darkMode to true if the value is "true"
        } else {
            App.darkMode = false; // Set darkMode to false if the value is "false"
        }

        // Check if function was pressed in history screen
        if (App.historyPressed) {
            try {
                historyPressed();
            } catch (IOException e) {
                e.printStackTrace();
            }
            App.historyPressed = false; // Reset the historyPressed flag
        }
    }

    /**
     * This method is called when the mouse is clicked on the graph. It displays the coordinates of the mouse on the graph.
     * @param event The mouse event that triggers the method.
     */
    public void handle(MouseEvent event) {
        coordinateLabel.setVisible(true); // Make the coordinateLabel visible (it starts off hidden on the screen so there isn't a blank label)
        coordinateLabel.setText(checkCoords(event.getSceneX(), event.getSceneY())); // Set the text of the coordinateLabel to the calculated coordinates of the mouse click on the graph
        coordinateLabel.setLayoutX(event.getSceneX()-8.0); // Set the x and y coordinates of the coordinateLabel to slightly near mouse click coordinates
        coordinateLabel.setLayoutY(event.getSceneY()-40.0);
        coordinateLabel.toFront(); // Bring the coordinateLabel to the front of the screen so it is visible
        coordinateCircle.setVisible(true); // Make the coordinateCircle visible (it starts off hidden on the screen so there isn't a random dot)
        coordinateCircle.setLayoutX(event.getSceneX()); // Set the x and y coordinates of the coordinateCircle to the exact mouse click coordinates
        coordinateCircle.setLayoutY(event.getSceneY());
        coordinateCircle.toFront(); // Bring the coordinateCircle to the front of the screen so it is visible
    }

    /**
     * This method is called when the mouse is moved on the graph. It calculates the coordinates of the mouse on the graph and returns the output that is printed on the screen.
     * @param sceneX The x coordinate of the mouse on the screen when clicked.
     * @param sceneY The y coordinate of the mouse on the screen when clicked.
     * @return The coordinates of the mouse on the graph in string format.
     */
    private String checkCoords(double sceneX, double sceneY) {
        // Calculate the coordinates of the mouse on the graph

        double x = (Math.abs(sceneX - centerX)) / incrementX; // use absolute value to get distance from center of graph
        double y = (Math.abs(sceneY - centerY)) / incrementY; 

        boolean positiveX = (sceneX > centerX && sceneX!=centerX);
        x = Math.round(x*100.0)/100.0;
        y = Math.round(y*100.0)/100.0;
        boolean positiveY = (sceneY < centerY && sceneY!=centerY); // y increases further down the page so sign flipped

        if(positiveX && x!=0.0){ // x is positive
            if(positiveY && y!=0.0){ // both coordinates are positive
                return ("(" + x + ", " + y + ")");
            } else if (!positiveY && y != 0.0) { // x is positive, y is negative
                return ("(" + x + ", -" + y + ")");
            } else { // x is positive, y is 0
                return ("(" + x + ", 0)");
            }
        } else if (!positiveX && x!=0.0){ // x is negative
            if(positiveY && y!=0.0){ // x is negative, y is positive
                return ("(-" + x + ", " + y + ")");
            } else if (!positiveY && y != 0.0) { // both coordinates are negative
                return ("(-" + x + ", -" + y + ")");
            } else { // x is negative, y is 0
                return ("(-" + x + ", 0)");
            }
        } else {
            if(positiveY && y!=0.0){ // x is 0, y is positive
                return ("(0, " + y + ")");
            } else if (!positiveY && y!=0.0){ // x is 0, y is negative
                return ("(0, -" + y + ")");
            }
        }
        return ("(0, 0)"); // x and y are 0
    }

    /**
     * This method is called when the clear button is pressed. It clears the graph and the input and output text.
     * @param function The function that is to be plotted on the graph.
     * @param colour The colour of the line that is to be plotted on the graph.
     */
    private void plotLine(String function, String colour) {
        // Plot the function with the specified string and color
		mathsGraph.plotLine(function, colour);
	}

    public void historyPressed() throws IOException{
        String function = App.getString();

        // Set the text input field with the user input function
        textInput.setText(function);

        // Clear the math graph
        mathsGraph.clear();

        // Save the user input function to a history file
        saveFile(function, "./src/main/resources/history.txt");

        // Derive the function and set the text output field with the derivative
        String derivative = Derive.derive(function);
        textOutput.setText(derivative);

        // Save the derivative to a file
        saveFile(derivative, "./src/main/resources/lastDerivative.txt");

        // Plot the user input function if App.showFunction is true
        if (App.showFunction)
            plotLine(function, "#ff8c00");

        // Plot the second derivative if App.showSecDer is true
        if (App.showSecDer)
            plotLine(Derive.derive(derivative), "#bf34ff");

        // Plot the derivative
        plotLine(derivative, "#00c91b");
    }

    /**
     * Executes the submitPressed function when a submit button is pressed.
     * 
     * @throws IOException If an I/O error occurs while saving files.
     */
    @FXML
    public void submitPressed() throws IOException {
        // Clear the graph
        mathsGraph.clear();

        // Retrieve the function from the text input field
        String function = textInput.getText();

        // Create an information alert
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);

        // Check if the function is empty
        if (function.matches("")) {
            alert.setContentText("Please input a function into the box.");
            alert.showAndWait();
        } else {
            boolean error = false;

            // Validate the function by evaluating it with a dummy value
            try {
                Derive.evaluate(1, function);
            } catch (Exception e) {
                alert.setContentText("Please input a valid function.");
                alert.showAndWait();
                error = true;
            }

            // Proceed if no error occurred during validation
            if (!error) {
                // Clear the math graph
                mathsGraph.clear();

                // Save the function to a history file
                saveFile(function, "./src/main/resources/history.txt");

                // Derive the function and set the text output field with the derivative
                String derivative = Derive.derive(function);
                textOutput.setText(derivative);

                // Save the derivative to a file
                saveFile(derivative, "./src/main/resources/lastDerivative.txt");

                // Plot the user input function if App.showFunction is true
                if (App.showFunction)
                    plotLine(function, "#ff8c00");

                // Plot the second derivative if App.showSecDer is true
                if (App.showSecDer)
                    plotLine(Derive.derive(derivative), "#bf34ff");

                // Plot the derivative
                plotLine(derivative, "#00c91b");
            }
        }
    }

    /**
     * Saves the provided function to the specified file.
     * 
     * @param function The function to be saved.
     * 
     * @param file     The file path where the function will be saved.
     * 
     * @throws IOException If an I/O error occurs while reading or writing the file.
     */
    private void saveFile(String function, String file) throws IOException {
        int maxInputs = 9999999;

        // Read existing content from the file
        StringBuilder existingContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                existingContent.append(line).append("\n");
            }
        }

        // Append the new function if the maximum number of inputs has not been reached
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            if (existingContent.toString().lines().count() < maxInputs) {
                // Append new input
                out.append(existingContent);
                out.append(function);
            }
        }
    }

    /**
     * Handles the event when the help button is clicked.
     * 
     * @throws IOException If an I/O error occurs while navigating to the help page.
     */
    @FXML
    private void helpClicked() throws IOException {
        // Set the root view to the "help" page
        App.setRoot("help");
    }

    /**
     * Handles the event when the settings button is clicked.
     * 
     * @throws IOException If an I/O error occurs while navigating to the help page.
     */
    @FXML
    private void settingsClicked() throws IOException {
        // Set the root view to the "settings" page
        App.setRoot("settings");
    }

    /**
     * Handles the event when the settings button is clicked.
     * 
     * @throws IOException If an I/O error occurs while navigating to the help page.
     */
    @FXML
    private void historyClicked() throws IOException {
        // Set the root view to the "history" page
        App.setRoot("history");
    }
}
