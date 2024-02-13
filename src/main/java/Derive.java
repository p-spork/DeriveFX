import java.net.*;
import java.io.*;
import org.json.JSONObject;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * This class is used to derive a function and evaluate it at a point.
 * It uses the Newton Math API to derive the function and get a result as a string,
 * and the exp4j library to evaluate it at a point.
 * 
 * @author Shriniket, Evan, Peter
 */
public class Derive {

    public static String[] symbols = {"^", "+", "-", "/"}; // symbols that need to be replaced in the URL

    /**
     * Main method
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
    }

    /**
     * Evaluates the function at a point using exp4j library, which takes a string function and returns a y value for the inputted x value
     * @param x the x value at which the function is being evaluated
     * @param input the function being evaluated
     * @return the y value of the function at the inputted x value
     */
    public static double evaluate(double x, String input){
        Expression e = new ExpressionBuilder(input) // initialize expression builder
        .variables("x") // set variable being evaluated
        .build() // build expression
        .setVariable("x", x); // set variable to x value
        double result = e.evaluate(); // evaluate expression
        return result; // return result
    }

    /**
     * Derives the function using the Newton Math API, which takes a function as a string and returns a derivative as a string
     * @param function the function being derived
     * @return the derivative of the function
     * @throws IOException
     */
    public static String derive(String function) throws IOException {
        // Create dictionary with HTML URL encoding for each symbol
        Dictionary<String, String> dict = new Hashtable<>();
        dict.put("^", "%5E");
        dict.put("+", "%2B");
        dict.put("-", "%2D");
        dict.put("/", "%2F");

        // Loops through each character in function string
        for (int i=0;i<function.length();i++) {
            String cur = function.substring(i, i+1);
            
            // Loops through all symbols in dict to see if character matches a symbol
            for (int j=0;j<symbols.length;j++) {
                // If character matches symbol, change the symbol to the URL encoding
                if (symbols[j].equals(cur)) {
                    String rep = dict.get(cur);
                    String first = function.substring(0, i);
                    String last = function.substring(i+1);

                    function = first + rep + last;
                }
            }
        }

        // Delete all extra spaces in function string
        function = function.replaceAll("\\s", "");

        // Add function string to URL endpoint
        URL oracle = new URL("https://newton.vercel.app/api/v2/derive/" + function);
        URLConnection yc = oracle.openConnection(); // Open an oracle connection with URL
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream())); // Get data from endpoint using buffered-reader
        String inputLine = in.readLine(); // Read data from buffered-reader

        JSONObject jObject  = new JSONObject(inputLine); // Convert data to Json object
        String derivative = jObject.getString("result"); // Get the derivative from Json object
        in.close(); // Close the buffered-reader
        derivative = derivative.replaceAll("\\s", ""); // Remove extra spaces from derivative
        return derivative;
    }
}
