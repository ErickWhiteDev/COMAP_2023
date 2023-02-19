import java.util.ArrayList;
import java.io.*;

public class WeightedGraphUtilities {

    /**
     * <h1>getLayer</h1>
     * <h2>Given a central {@link Vertex}, get all nodes a certain depth away from it</h2>
     *
     * @author Erick White
     * @param parent central node (propagates outwards from here)
     * @param graph network node belongs to
     * @param curr current layer depth being checked
     * @param depth layer desired
     * @param layer ArrayList of all vertices on the final layer
     */
    public static void getLayer(Vertex parent, WeightedGraph<Vertex> graph, int curr, int depth, ArrayList<Vertex> layer) {
        if (depth == 0) { // The zero layer is only the parent node
            layer.add(parent);
        }
        else {
            for (Vertex v : graph.neighborsOf(parent)) {
                if (curr == depth - 1) { // Only add nodes in the final layer
                    layer.add(v);
                }
                else {
                    getLayer(v, graph, curr + 1, depth, layer);
                }
            }
        }

    }

    /**
     * <h1>getLayerWeights</h1>
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
     * <h1>getLayerAchievements</h1>
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
     * <h1>getGoalPriority</h1>
     * Find the priority level of a {@link Vertex} based on the weights of its connections.
     * The priority is inversely proportional to the layer depth.
     *
     * @author Erick White
     * @param parent node for which priority must be calculated
     * @param graph network parent node belongs to
     * @return priority level of node
     */
    public static double getGoalPriority(Vertex parent, WeightedGraph<Vertex> graph) {
        ArrayList<ArrayList<Vertex>> layers = new ArrayList<>();
        double priority = 0;
        double layerWeight;

        for (int i = 0; i < graph.getDepth(); i++) {
            layers.add(new ArrayList<>());
            getLayer(parent, graph, 0, i, layers.get(i));
            layerWeight = VectorUtilities.sumVector(getLayerWeights(layers.get(i), graph)) / Math.pow(graph.getVertexCount() - 1, i + 1);

            priority += (layerWeight / Math.abs(layerWeight)) / (Math.sqrt(i + 1) * Math.pow(1 - Math.abs(layerWeight), i + 1));
        }

        return Math.exp(priority);
    }

    /**
     * <h1>getNeighborhoodAchievement</h1>
     * Takes the <b>achievement metrics</b> of surrounding {@link Vertex Vertices} in a graph and propagates it to the parent vertex.
     * The relation between propagation and distance from the source vertex is inversely proportional to the distance (nonlinearly)
     * between the source and the node experiencing the effect.
     * Propagation can end up depending on the original node due to connections in the graph.
     * <b>Achievement scores should not exceed 1.</b>
     *
     * @author Erick White
     * @param parent vertex that has undergone a change in achievement score
     * @param graph network through which to propagate
     */
    public static double getNeighborhoodAchievement(Vertex parent, WeightedGraph<Vertex> graph) {
        ArrayList<ArrayList<Vertex>> layers = new ArrayList<>();
        double weightedAchievementProduct = 0;

        for (int i = 0; i < graph.getDepth(); i++) {
            layers.add(new ArrayList<>());
            getLayer(parent, graph, 0, i, layers.get(i));

            weightedAchievementProduct += VectorUtilities.dotProduct(getLayerWeights(layers.get(i), graph), getLayerAchievements(layers.get(i), graph)) / Math.pow((graph.getVertexCount() - 1) * graph.getK(), i + 1);
        }

        return parent.getAchievement() + weightedAchievementProduct;
    }

    /**
     * <h1>updateAchievements</h1>
     * Takes all the {@link Vertex Vertices} in a network and adjusts the values of the achievements
     * corresponding to their weighted connections.
     * Propagation can end up depending on the original node due to connections in the graph.
     * <b>Achievement scores should not exceed 1.</b>
     *
     * @author Erick White
     * @param graph graph being updated
     */
    public static void updateAchievements(WeightedGraph<Vertex> graph) {
        double[] newAchievements = new double[graph.getVertexCount()];

        for (int i = 0; i < graph.getVertexCount(); i++) {
            newAchievements[i] = getNeighborhoodAchievement(graph.vertexAt(i), graph);
        }

        for (int i = 0; i < graph.getVertexCount(); i++) {
            graph.vertexAt(i).setAchievement((newAchievements[i] > 0) ? Math.min(newAchievements[i], 1) : Math.max(newAchievements[i], -1));
        }
    }

    /**
     * <h1>displayAllAchievements</h1>
     * Displays the current achievement value of each {@link Vertex}. Useful for testing and debugging.
     *
     * @author Elizabeth Cutting
     * @param graph network whose achievement values will be displayed
     */
    public static void  displayAllAchievements(WeightedGraph<Vertex> graph) {
        //print out the current achievement for the base vertex
        System.out.println(graph.vertexAt(0).toString() + " " + graph.vertexAt(0).getAchievement());
        //print out the achievement for the rest of the vertices
        for(Vertex v : graph.neighborsOf(graph.vertexAt(0))) {
            System.out.println(v.toString() + " " + v.getAchievement());
        }
    }

    /**
     * <h1>setInitialAchievements</h1>
     * Sets the initial achievement values of all {@link Vertex Vertices} in the graph.
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
     * <h1>writeNames</h1>
     * Write the <b>names</b> of {@link Vertex Vertices} to a CSV file.
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

    public static void writeInitialAchievements(WeightedGraph<Vertex> graph, String outputName) throws IOException {
        File output = new File(outputName);
        FileWriter writeOutput = new FileWriter(output);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < graph.getVertexCount(); i++) {
            sb.append(graph.getInitialAchievementValues().get(i));
            sb.append(',');
        }

        writeOutput.write(sb.toString());

        writeOutput.close();
    }

    /**
     * <h1>writeAchievements</h1>
     * Write the <b>achievement metrics</b> of {@link Vertex Vertices} to a CSV file.
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

        updateAchievements(graph);

        for (int i = 0; i < graph.getVertexCount(); i++) {
            sb.append(graph.vertexAt(i).getAchievement());
            sb.append(',');
        }

        writeOutput.write(sb.toString());

        writeOutput.close();
    }

    /**
     * <h1>writePriorities</h1>
     * Write the <b>priority scores</b> of {@link Vertex Vertices} to a CSV file.
     *
     * @author Erick White
     * @param graph network being read
     * @param outputName name of output file
     * @throws IOException unable to create file
     */
    public static void writePriorities(WeightedGraph<Vertex> graph, String outputName) throws IOException {
        File output = new File(outputName);
        FileWriter writeOutput = new FileWriter(output);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < graph.getVertexCount(); i++) {
            sb.append(getGoalPriority(graph.vertexAt(i), graph));
            sb.append(',');
        }

        writeOutput.write(sb.toString());

        writeOutput.close();
    }

    /**
     * <h1>setMultipliers</h1>
     * Sets the multiplier values of a graph's {@link Vertex Vertices}.
     *
     * @author Erick White
     * @param graph network for which multipliers need to be set
     * @param multipliers multiplier values
     */
    public static void setMultipliers(WeightedGraph<Vertex> graph, double[][] multipliers) {
        for (int i = 0; i < graph.getVertexCount(); i++) {
            graph.vertexAt(i).setMultipliers(multipliers[i]);
        }
    }

    /**
     * <h1>clearMultipliers</h1>
     * Clears the multiplier values of a graph's {@link Vertex Vertices}.
     *
     * @author Erick White
     * @param graph network for which multipliers need to be cleared
     */
    public static void clearMultipliers(WeightedGraph<Vertex> graph) {
        for (int i = 0; i < graph.getVertexCount(); i++) {
            graph.vertexAt(i).clearMultipliers();
        }
    }

    /**
     * <h1>clearMultipliersExceptOne</h1>
     * Clear a {@link WeightedGraph WeightedGraph's} {@link Vertex Vertices'} multiplier values except for one.
     *
     * @author Erick White
     * @param graph network to set multipliers for
     * @param keep index of multiplier to keep
     */
    public static void clearMultipliersExceptOne(WeightedGraph<Vertex> graph, int keep) {
        for (int i = 0; i < graph.getVertexCount(); i++) {
            graph.vertexAt(i).clearMultipliersExceptOne(keep);
        }
    }
}
