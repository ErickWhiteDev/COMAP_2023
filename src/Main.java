
import java.util.List;
import java.io.*;
import java.util.Scanner;
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
    }
}
