import java.util.ArrayList;
import java.util.List;
import java.io.*;
public class Main {
    public static void main(String[] args) throws IOException {
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

        WeightedGraph<Vertex> goals = new WeightedGraph<>(goalList, 1, 3);

        // Fill in weights from CSV data
        for (int i = 0; i < goals.getVertexCount(); i++) {
            for (int j = 0; j < i; j++) {
                goals.addEdge(i, j, Double.parseDouble(weightsString[i][j]));
            }
        }

        // Fill in multipliers from CSV data
        for (int i = 0; i < goals.getVertexCount(); i++) {
            for (int j = 0; j < 5; j++) {
                multiplierDouble[i][j] = Double.parseDouble(multiplierString[i][j]);
            }
        }

        ArrayList<Double> initialAchievements = new ArrayList<>(
                List.of(.6, .4, .6, .6, .6, .6, .7, .3, .8, .6, .4, .7, .3, .3, .6, .5, .5)
        );

        goals.setInitialAchievementValues(initialAchievements);

        // Must be done before each data write to ensure clean data
        WeightedGraphUtilities.setInitialAchievements(goals, initialAchievements);

        // Write data with only initial conditions and no multipliers
        WeightedGraphUtilities.writeNames(goals, "names.txt");
        WeightedGraphUtilities.writeAchievements(goals,  "achievements.csv");
        WeightedGraphUtilities.writeInitialAchievements(goals, "initial_achievements.csv");
        WeightedGraphUtilities.writePriorities(goals, "priorities.csv");

//        File modifiedAchievements = new File("modified_achievements.csv");
//        FileWriter writeModifiedAchievements = new FileWriter(modifiedAchievements);
//        StringBuilder sb = new StringBuilder();
//
//        // Reset data
//        WeightedGraphUtilities.setInitialAchievements(goals, initialAchievements);
//        // Set multipliers from data and update achievements
//        WeightedGraphUtilities.setMultipliers(goals, multiplierDouble);
//        WeightedGraphUtilities.updateAchievements(goals);
//
//        // modified_achievements line 1 : original achievement scores with multipliers
//        for (Vertex v : goalList) {
//            sb.append(v.getAchievement());
//            sb.append(',');
//        }
//
//        sb.append('\n');
//
//        WeightedGraphUtilities.clearMultipliers(goals);
//
//        // Set one goal to completed and propagate the changes from doing so
//        // modified_achievements lines 2-18 : achievement values with one parameter completed
//        for (Vertex v : goalList) {
//            WeightedGraphUtilities.setInitialAchievements(goals, initialAchievements);
//            v.setAchievement(1);
//            WeightedGraphUtilities.updateAchievements(goals);
//
//            for (Vertex n : goalList) {
//                sb.append(n.getAchievement());
//                sb.append(',');
//            }
//
//            sb.append('\n');
//        }
//
//        // Set one goal to 0 and propagate the changes from doing so
//        // modified_achievements lines 19-34 : achievement values with one parameter set to 0
//        for (Vertex v : goalList) {
//            WeightedGraphUtilities.setInitialAchievements(goals, initialAchievements);
//            v.setAchievement(0);
//            WeightedGraphUtilities.updateAchievements(goals);
//
//            for (Vertex n : goalList) {
//                sb.append(n.getAchievement());
//                sb.append(',');
//            }
//
//            sb.append('\n');
//        }
//
//        writeModifiedAchievements.write(sb.toString());
//
//        writeModifiedAchievements.close();
        }
}
