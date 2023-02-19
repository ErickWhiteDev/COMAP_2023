import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private double achievement;
    private double techMultiplier;
    private double pandemicMultiplier;
    private double climateMultiplier;
    private double warMultiplier;
    private double refugeeMultiplier;
    private final String NAME;

    public Vertex(String name) {
        this.NAME = name;
        clearMultipliers();
    }

    public void setMultipliers(double[] vals) {
        this.techMultiplier = vals[0];
        this.pandemicMultiplier = vals[1];
        this.climateMultiplier = vals[2];
        this.warMultiplier = vals[3];
        this.refugeeMultiplier = vals[4];
    }

    public void clearMultipliers() {
        setMultipliers(new double[]{Math.sqrt(5), Math.sqrt(5), Math.sqrt(5), Math.sqrt(5), Math.sqrt(5)});
    }

    public void clearMultipliersExceptOne(int keep) {
        ArrayList<Double> curr = this.getMultiplierVector();

        double[] vals = new double[curr.size()];

        for (int i = 0; i < curr.size(); i++) {
            vals[i] = i == keep ? curr.get(i) : Math.sqrt(curr.size());
        }

        this.setMultipliers(vals);
    }

    public ArrayList<Double> getMultiplierVector() {
        return new ArrayList<>(List.of(this.techMultiplier, this.pandemicMultiplier, this.climateMultiplier, this.warMultiplier, this.refugeeMultiplier));
    }

    public void setAchievement(double achievement) {
        double temp = achievement * VectorUtilities.getMagnitude(this.getMultiplierVector())/ 5;
        this.achievement = temp > 0 ? Math.min(temp, 1) : Math.max(temp, -1);
    }

    public double getAchievement() {
        return achievement;
    }

    @Override
    public String toString() {
        return NAME;
    }

    public static void writeMultiplierNames(ArrayList<String> multiplierNames, String outputName) throws IOException {
        File output = new File(outputName);
        FileWriter writeOutput = new FileWriter(output);
        StringBuilder sb = new StringBuilder();

        for (String s : multiplierNames) {
            sb.append(s);
            sb.append('\n');
        }

        writeOutput.write(sb.toString());

        writeOutput.close();
    }
}
