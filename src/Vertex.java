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

    public void setAchievement(double achievement) {
        double temp =
                achievement *
                        Math.sqrt(
                                Math.pow(this.techMultiplier, 2) +
                                Math.pow(this.pandemicMultiplier, 2) +
                                Math.pow(this.climateMultiplier, 2) +
                                Math.pow(this.warMultiplier, 2) +
                                Math.pow(this.refugeeMultiplier, 2)
                        ) / 5;
        this.achievement = temp > 0 ? Math.min(temp, 1) : Math.max(temp, -1);
    }

    public double getAchievement() {
        return achievement;
    }

    @Override
    public String toString() {
        return NAME;
    }
}
