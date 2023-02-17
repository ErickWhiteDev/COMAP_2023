
import java.util.List;
import java.io.*;
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/weights.csv"));

        String line;
        String delimiter = ",";
        String[][] weightsString = new String[17][17];
        int row = 0;

        while ((line = br.readLine()) != null) {
            weightsString[row] = line.split(delimiter);
            row++;
        }

        br.close();

        Vertex noPoverty = new Vertex("No Poverty");
        Vertex zeroHunger = new Vertex("Zero Hunger");
        Vertex goodHealthAndWellBeing = new Vertex("Good Health and Well Being");
        Vertex qualityEducation = new Vertex("Quality Education");
        Vertex genderEquality = new Vertex("Gender Equality");
        Vertex cleanWaterAndSanitation = new Vertex("Clean Water and Sanitation");
        Vertex affordableAndCleanEnergy = new Vertex("Affordable and Clean Energy");
        Vertex decentWorkAndEconomicGrowth = new Vertex("Decent Work and Economic Growth");
        Vertex industryInnovationAndInfrastructure = new Vertex("Industry, Innovation, and Infrastructure");
        Vertex reducedInequalities = new Vertex("Reduced Inequalities");
        Vertex sustainableCitiesAndCommunities = new Vertex("Sustainable Cities and Communities");
        Vertex responsibleConsumptionAndProduction = new Vertex("Responsible Consumption and Production");
        Vertex climateAction = new Vertex("Climate Action");
        Vertex lifeBelowWater = new Vertex("Life Below Water");
        Vertex lifeOnLand = new Vertex("Life on Land");
        Vertex peaceJusticeAndStrongInstitutions = new Vertex("Peace, Justice, and Strong Institutions");
        Vertex partnershipsForTheGoals = new Vertex("Partnerships for the Goals");

        WeightedGraph<Vertex> goals = new WeightedGraph<>(
                List.of(noPoverty,
                        zeroHunger,
                        goodHealthAndWellBeing,
                        qualityEducation,
                        genderEquality,
                        cleanWaterAndSanitation,
                        affordableAndCleanEnergy,
                        decentWorkAndEconomicGrowth,
                        industryInnovationAndInfrastructure,
                        reducedInequalities,
                        sustainableCitiesAndCommunities,
                        responsibleConsumptionAndProduction,
                        climateAction,
                        lifeBelowWater,
                        lifeOnLand,
                        peaceJusticeAndStrongInstitutions,
                        partnershipsForTheGoals
                )
        );

        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < i; j++) {
                goals.addEdge(i, j, Double.parseDouble(weightsString[i][j]));
            }
        }

        propagateAchievement(noPoverty, goals, 1, 2);
    }

    /**
     * <h1>Propagate Achievement</h1>
     * propagateAchievement takes the achievement metric of one vertex in a graph and propagates it to other vertices.
     * The relation between propagation and distance from the source vertex is inversely proportional to the distance
     * between the source and the node experiencing the effect. Propagation can end up affecting the original node
     * due to connections in the graph.
     *
     * @author Erick White
     * @param parent vertex that has undergone a change in achievement score
     * @param graph network through which to propagate
     * @param curr current depth of propagation
     * @param depth maximum propagation depth
     */
    private static void propagateAchievement(Vertex parent, WeightedGraph<Vertex> graph, int curr, int depth) {
        for (Vertex v : graph.neighborsOf(parent)) {
            v.setAchievement(v.getAchievement() + (v.getAchievement() * graph
                    .edgesOf(parent)
                    .get((graph.indexOf(v) < graph.indexOf(parent)) ? graph.indexOf(v) : graph.indexOf(v) - 1)
                    .weight) / Math.pow(16, curr)
            );

            if (curr != depth) {
                propagateAchievement(v, graph, curr + 1, depth);
            }
        }
    }
}
