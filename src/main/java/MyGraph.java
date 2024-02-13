import javafx.scene.chart.XYChart;

/**
 * This class is used to plot the graph of a function.
 * @author Shriniket, Evan, Peter
 */
public class MyGraph{

	// initializing variables
	private XYChart<Double, Double> graph;
	private double range;

	/**
	 * Constructor for MyGraph
	 * @param graph the graph being plotted on
	 * @param range integer that represents the range of the graph
	 */
	public MyGraph(final XYChart<Double, Double> graph, final double range) {
		this.graph = graph;
		this.range = range;
	}

	/**
	 * Evaluates the function at each point in order to plot a line
	 * @param function string of the function being evaluated
	 * @param colour the colour of the line being plotted (to differentiate between function, derivative, second derivative)
	 */
	public void plotLine(String function, String colour) {
		final XYChart.Series<Double, Double> series = new XYChart.Series<Double, Double>(); // initialize chart with x and y values
		for (double x = -range; x <= range; x = x + 0.01) { // for loop to plot each point of the function, with a step of 0.01
			double y = Derive.evaluate(x, function); // evaluate the function at each point using the evaluate method in Derive.java
			plotPoint(x, y, series); // plot the point
		}
		graph.getData().add(series); // add the series of points to the graph to form a line
		series.getNode().setStyle("-fx-stroke: " + colour); // set the colour of the line just plotted
	}

	/**
	 * Plots a point for each x and y value of the function calculated in plotLine
	 * @param x the x value of the point being plotted
	 * @param y the y value of the point being plotted
	 * @param series the total series of points being plotted (to form the line)
	 */
	private void plotPoint(final double x, final double y, final XYChart.Series<Double, Double> series) {
		series.getData().add(new XYChart.Data<Double, Double>(x, y)); // add all the calculated x and y values to the graph as a series, to form the line
	}

	/**
	 * Clears the graph of all data, so that a new function can be plotted without the old one interfering
	 */
	public void clear() {
		graph.getData().clear(); // inbuilt method to completely clear the graph
	}




}