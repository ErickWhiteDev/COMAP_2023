import java.util.ArrayList;
import java.io.*;

public class WeightedGraphUtilities {
	
	/**
	 * *@author Madison Jones and Elizabeth Cutting
	 *
	 */
	public static void setInitialAchievementVals(WeightedGraph<Vertex> graph)
	{
		graph.vertexAt(0).setAchievement(.6);
		graph.vertexAt(1).setAchievement(.4);
		graph.vertexAt(2).setAchievement(.6);
		graph.vertexAt(3).setAchievement(.6);
		graph.vertexAt(4).setAchievement(.6);
		graph.vertexAt(5).setAchievement(.6);
		graph.vertexAt(6).setAchievement(.7);
		graph.vertexAt(7).setAchievement(.3);
		graph.vertexAt(8).setAchievement(.8);
		graph.vertexAt(9).setAchievement(.6);
		graph.vertexAt(10).setAchievement(.4);
		graph.vertexAt(11).setAchievement(.7);
		graph.vertexAt(12).setAchievement(.3);
		graph.vertexAt(13).setAchievement(.3);
		graph.vertexAt(14).setAchievement(.6);
		graph.vertexAt(15).setAchievement(.5);
		graph.vertexAt(16).setAchievement(.5);
		
	}
	
    /**
     * <h1>Get Layer</h1>
     * Given a central node, get all nodes a certain depth away from it
     *
     * @author Erick White
     * @param parent central node (propagates outwards from here)
     * @param graph network node belongs to
     * @param curr current layer depth being checked
     * @param depth layer desired
     * @param layer ArrayList of all vertices on the final layer
     */
    public static void getLayer(Vertex parent, WeightedGraph<Vertex> graph, int curr, int depth, ArrayList<Vertex> layer) {
        if (depth == 0) {
            layer.add(parent);
        }
        else {
            for (Vertex v : graph.neighborsOf(parent)) {
                if (curr == depth - 1) {
                    layer.add(v);
                }
                else {
                    getLayer(v, graph, curr + 1, depth, layer);
                }
            }
        }

    }

    /**
     * <h1>Get Layer Weights</h1>
     * Returns a vector of the <b>weights of connections</b> of a given layer.
     *
     * @author Erick White
     * @param layer layer from which weights are being retrieved
     * @param graph network to which layer belongs
     * @return ArrayList of doubles containing all weights of connections on a layer
     */
    public static ArrayList<Double> getLayerWeights(ArrayList<Vertex> layer, WeightedGraph<Vertex> graph) {
        ArrayList<Double> weights = new ArrayList<>();

        for (Vertex v : layer) {
            for (Vertex n : graph.neighborsOf(v)) {
                weights.add(graph
                        .edgesOf(v)
                        .get((graph.indexOf(n) < graph.indexOf(v)) ? graph.indexOf(n) : graph.indexOf(n) - 1)
                        .weight
                );
            }
        }

        return weights;
    }

    /**
     * <h1>Get Layer Achievements</h1>
     * Returns a vector of the <b>achievement metrics</b> of a given layer.
     *
     * @author Erick White
     * @param layer layer from which achievements are being retrieved
     * @param graph network to which layer belongs
     * @return ArrayList of doubles containing all achievements on a layer
     */
    public static ArrayList<Double> getLayerAchievements(ArrayList<Vertex> layer, WeightedGraph<Vertex> graph) {
        ArrayList<Double> achievements = new ArrayList<>();

        for (Vertex v : layer) {
            for (Vertex n : graph.neighborsOf(v)) {
                achievements.add(n.getAchievement());
            }
        }

        return achievements;
    }

    /**
     * <h1>Dot Product</h1>
     * Takes the dot product of two vectors of doubles.
     *
     * @author Erick White
     * @param arr1 first vector
     * @param arr2 second vector
     * @return dot product of two input vectors
     */
    public static double dotProduct(ArrayList<Double> arr1, ArrayList<Double> arr2) {
        double sum = 0;

        for (int i = 0; i < arr1.size(); i++) {
            sum += arr1.get(i) * arr2.get(i);
        }

        return sum;
    }

    /**
     * <h1>Sum Array</h1>
     * Sums contents of an array.
     *
     * @author Erick White
     * @param arr array to sum (doubles)
     * @return sum of elements in array
     */
    public static double sumArray(ArrayList<Double> arr) {
        double sum = 0;

        for (Double d : arr) {
            sum += d;
        }

        return sum;
    }

    /**
     * <h1>Get Goal Priority</h1>
     * Find the priority level of a node based on the weights of its connections.
     * The priority is inversely proportional to the layer depth.
     *
     * @author Erick White
     * @param parent node for which priority must be calculated
     * @param graph network parent node belongs to
     * @param graphSize number of vertices in graph
     * @param depth maximum summation depth level
     * @return priority level of node
     */
    public static double getGoalPriority(Vertex parent, WeightedGraph<Vertex> graph, int graphSize, int depth) {
        ArrayList<ArrayList<Vertex>> layers = new ArrayList<>();
        double priority = 0;
        double layerWeight;

        for (int i = 0; i < depth; i++) {
            layers.add(new ArrayList<>());
            getLayer(parent, graph, 0, i, layers.get(i));
            layerWeight = sumArray(getLayerWeights(layers.get(i), graph)) / Math.pow(graphSize - 1, i + 1);

            priority += (layerWeight / Math.abs(layerWeight)) / (Math.sqrt(i + 1) * Math.pow(1 + layerWeight, i + 1));
        }

        return priority;
    }

    /**
     * <h1>Get Neighborhood Achievement</h1>
     * getNeighborhoodAchievement takes the <b>achievement metrics</b> of surrounding vertex in a graph and propagates it to the parent vertex.
     * The relation between propagation and distance from the source vertex is inversely proportional to the distance (nonlinearly)
     * between the source and the node experiencing the effect. Propagation can end up depending on the original node
     * due to connections in the graph.
     *
     * @author Erick White
     * @param parent vertex that has undergone a change in achievement score
     * @param graph network through which to propagate
     * @param graphSize number of vertices in graph
     * @param depth maximum propagation depth
     * @param k coefficient determining fall-off rate of propagation
     */
    public static double getNeighborhoodAchievement(Vertex parent, WeightedGraph<Vertex> graph, int graphSize, int depth, double k) {
        ArrayList<ArrayList<Vertex>> layers = new ArrayList<>();
        double weightedAchievementProduct = 0;

        for (int i = 0; i < depth; i++) {
            layers.add(new ArrayList<>());
            getLayer(parent, graph, 0, i, layers.get(i));

            weightedAchievementProduct += dotProduct(getLayerWeights(layers.get(i), graph), getLayerAchievements(layers.get(i), graph)) / Math.pow(graphSize - 1, i + 1 - k);
        }

        return parent.getAchievement() + weightedAchievementProduct;
    }

    /**
     * <h1>Update Achievements</h1>
     * updateAchievements takes all the vertices in a network and adjusts the values of the achievements
     * corresponding to their weighted connections. Propagation can end up depending on the original node
     * due to connections in the graph.
     *
     * @author Erick White
     * @param graph graph being updated
     * @param graphSize number of vertices in graph
     * @param depth maximum search depth for updates
     * @param k coefficient determining fall-off rate of achievement propagation
     */
    public static void updateAchievements(WeightedGraph<Vertex> graph, int graphSize, int depth, double k) {
        double[] newAchievements = new double[graph.getVertexCount()];

        for (int i = 0; i < graph.getVertexCount(); i++) {
            newAchievements[i] = getNeighborhoodAchievement(graph.vertexAt(i), graph, graphSize - 1, depth, k);
        }

        for (int i = 0; i < graph.getVertexCount(); i++) {
            graph.vertexAt(i).setAchievement((newAchievements[i] > 0) ? Math.min(newAchievements[i], 1) : Math.max(newAchievements[i], -1));
        }
    }

    /**
     * <h1>Display All Achievements</h1>
     * displayAllAchievements() displays the current achievement value of each vertex. Useful for testing and debugging.
     *
     * @author Elizabeth Cutting
     * @param graph network whose achievement values will be displayed
     */
    public static void displayAllAchievements(WeightedGraph<Vertex> graph) {
        //print out the current achievement for the base vertex
        System.out.println(graph.vertexAt(0).toString() + " " + graph.vertexAt(0).getAchievement());
        //print out the achievement for the rest of the vertices
        for(Vertex v : graph.neighborsOf(graph.vertexAt(0))) {
            System.out.println(v.toString() + " " + v.getAchievement());
        }
    }

    /**
     * <h1>Set Initial Achievements</h1>
     * Sets the initial achievement values of all vertices in the graph.
     *
     * @author Erick White
     * @param graph graph for which initial values must be set
     * @param achievements initial values
     */
    public static void setInitialAchievements(WeightedGraph<Vertex> graph, ArrayList<Double> achievements) {
        for (int i = 0; i < graph.getVertexCount(); i++) {
            graph.vertexAt(i).setAchievement(achievements.get(i));
        }
    }

    /**
     * <h1>Write Names</h1>
     * Write the names of vertices to a CSV file.
     *
     * @author Erick White
     * @param graph network being read
     * @param outputName name of output file
     * @throws IOException unable to create file
     */
    public static void writeNames(WeightedGraph<Vertex> graph, String outputName) throws IOException {
        File output = new File(outputName);
        FileWriter writeOutput = new FileWriter(output);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < graph.getVertexCount(); i++) {
            sb.append(graph.vertexAt(i).toString());
            sb.append('\n');
        }

        writeOutput.write(sb.toString());

        writeOutput.close();
    }

    /**
     * <h1>Write Achievements</h1>
     * Write the <b>achievement metrics</b> of vertices to a CSV file.
     *
     * @author Erick White
     * @param graph network being read
     * @param outputName name of output file
     * @throws IOException unable to create file
     */
    public static void writeAchievements(WeightedGraph<Vertex> graph, String outputName) throws IOException {
        File output = new File(outputName);
        FileWriter writeOutput = new FileWriter(output);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < graph.getVertexCount(); i++) {
            sb.append(graph.vertexAt(i).getAchievement());
            sb.append(',');
        }

        writeOutput.write(sb.toString());

        writeOutput.close();
    }
}
