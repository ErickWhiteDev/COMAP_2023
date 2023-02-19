import java.util.ArrayList;
import java.util.List;
import java.io.*;
public class Main {
    public static void main(String[] args) throws IOException {
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

        // Iterable ArrayList of all vertices for use in data writing
        ArrayList<Vertex> goalList = new ArrayList<>(
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

        ArrayList<Double> initialAchievements = new ArrayList<>(
                List.of(.6, .4, .6, .6, .6, .6, .7, .3, .8, .6, .4, .7, .3, .3, .6, .5, .5)
        );

        ArrayList<String> multiplierNames = new ArrayList<>(
          List.of("Tech", "Pandemic", "Climate", "War", "Refugee")
        );

        // Write data with only initial conditions and no multipliers
        Vertex.writeMultiplierNames(multiplierNames, "multiplier_names.txt");

        ArrayList<String> baseFileNames = new ArrayList<>(
                List.of("names.txt", "initial_achievements.csv", "achievements.csv", "priorities.csv", "modified_achievements.csv")
        );
        ArrayList<String> quarterFileNames = new ArrayList<>(
                List.of("names.txt", "initial_achievements_quarter.csv", "achievements_quarter.csv", "priorities_quarter.csv", "modified_achievements_quarter.csv")
        );
        ArrayList<String> halfFileNames = new ArrayList<>(
                List.of("names.txt", "initial_achievements_half.csv", "achievements_half.csv", "priorities_half.csv", "modified_achievements_half.csv")
        );
        ArrayList<String> fiveQuartersFileNames = new ArrayList<>(
                List.of("names.txt", "initial_achievements_five_quarters.csv", "achievements_five_quarters.csv", "priorities_five_quarters.csv", "modified_achievements_five_quarters.csv")
        );

        ArrayList<String> threeHalvesFileNames = new ArrayList<>(
                List.of("names.txt", "initial_achievements_three_halves.csv", "achievements_three_halves.csv", "priorities_three_halves.csv", "modified_achievements_three_halves.csv")
        );

        String outputDirName = "data";

        WeightedGraph<Vertex> base = new WeightedGraph<>(goalList, 3, 2.56);
        WeightedGraph<Vertex> quarter = new WeightedGraph<>(goalList, 3, 2.56);
        WeightedGraph<Vertex> half = new WeightedGraph<>(goalList, 3, 2.56);
        WeightedGraph<Vertex> fiveQuarters = new WeightedGraph<>(goalList, 3, 2.56);
        WeightedGraph<Vertex> threeHalves = new WeightedGraph<>(goalList, 3, 2.56);

        runAnalysis(base, goalList, initialAchievements, 1, 1, baseFileNames, outputDirName);
        runAnalysis(quarter, goalList, initialAchievements, .25, 1, quarterFileNames, outputDirName);
        runAnalysis(half, goalList, initialAchievements, .5, 1, halfFileNames, outputDirName);
        runAnalysis(fiveQuarters, goalList, initialAchievements, 1.25, 1, fiveQuartersFileNames, outputDirName);
        runAnalysis(threeHalves, goalList, initialAchievements, 1.5, 1, threeHalvesFileNames, outputDirName);
    }

    public static void runAnalysis(WeightedGraph<Vertex> graph, ArrayList<Vertex> goalList, ArrayList<Double> initialAchievements, double weightSensitivity, double multiplierSensitivity, ArrayList<String> fileNames, String outputDirName) throws IOException {
        String line;
        String delimiter = ",";
        int row = 0;

        BufferedReader weightsReader = new BufferedReader(new FileReader("src/weights.csv"));
        String[][] weightsString = new String[17][17];

        while ((line = weightsReader.readLine()) != null) {
            weightsString[row] = line.split(delimiter);
            row++;
        }

        weightsReader.close();

        row = 0;

        BufferedReader multiplierReader = new BufferedReader(new FileReader("src/multipliers.csv"));
        String[][] multiplierString = new String[17][5];
        double[][] multiplierDouble = new double[17][5];

        while ((line = multiplierReader.readLine()) != null) {
            multiplierString[row] = line.split(delimiter);
            row++;
        }

        multiplierReader.close();

        // Fill in weights from CSV data
        for (int i = 0; i < graph.getVertexCount(); i++) {
            for (int j = 0; j < i; j++) {
                graph.addEdge(i, j, Double.parseDouble(weightsString[i][j]) * weightSensitivity);
            }
        }

        // Fill in multipliers from CSV data
        for (int i = 0; i < graph.getVertexCount(); i++) {
            for (int j = 0; j < 5; j++) {
                multiplierDouble[i][j] = Double.parseDouble(multiplierString[i][j]) * Math.sqrt(5) * multiplierSensitivity;
            }
        }

        graph.setInitialAchievementValues(initialAchievements);

        // Must be done before each data write to ensure clean data
        WeightedGraphUtilities.setInitialAchievements(graph, initialAchievements);

        WeightedGraphUtilities.writeNames(graph, fileNames.get(0));
        WeightedGraphUtilities.writeInitialAchievements(graph, fileNames.get(1), outputDirName);
        WeightedGraphUtilities.writeAchievements(graph,  fileNames.get(2), outputDirName);
        WeightedGraphUtilities.writePriorities(graph, fileNames.get(3), outputDirName);

        File modifiedAchievements = new File(outputDirName, fileNames.get(4));
        FileWriter writeModifiedAchievements = new FileWriter(modifiedAchievements);
        StringBuilder sb = new StringBuilder();

        // Reset data
        WeightedGraphUtilities.setInitialAchievements(graph, initialAchievements);
        // Set multipliers from data and update achievements
        WeightedGraphUtilities.setMultipliers(graph, multiplierDouble);
        WeightedGraphUtilities.updateAchievements(graph);

        // modified_achievements line 1 : original achievement scores with multipliers
        for (Vertex v : goalList) {
            sb.append(v.getAchievement());
            sb.append(',');
        }

        sb.append('\n');

        WeightedGraphUtilities.clearMultipliers(graph);

        for (int i = 0; i < 5; i++) {
            // Reset data
            WeightedGraphUtilities.setInitialAchievements(graph, initialAchievements);
            // Set multipliers from data and update achievements
            WeightedGraphUtilities.setMultipliers(graph, multiplierDouble);
            WeightedGraphUtilities.clearMultipliersExceptOne(graph, i);
            WeightedGraphUtilities.updateAchievements(graph);

            // modified_achievements lines 2 - 6 : original achievement scores with multipliers
            for (Vertex v : goalList) {
                sb.append(v.getAchievement());
                sb.append(',');
            }

            sb.append('\n');

            WeightedGraphUtilities.clearMultipliers(graph);
        }

        // Set one graph to completed and propagate the changes from doing so
        // modified_achievements lines 2-18 : achievement values with one parameter completed
        for (Vertex v : goalList) {
            WeightedGraphUtilities.setInitialAchievements(graph, initialAchievements);
            v.setAchievement(1);
            WeightedGraphUtilities.updateAchievements(graph);

            for (Vertex n : goalList) {
                sb.append(n.getAchievement());
                sb.append(',');
            }

            sb.append('\n');
        }
        writeModifiedAchievements.write(sb.toString());

        writeModifiedAchievements.close();
    }
}
