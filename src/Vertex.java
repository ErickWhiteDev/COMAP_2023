public class Vertex {
    private double achievement;
    private double tech_multiplier;
    private double pandemic_multiplier;
    private double climate_multiplier;
    private double war_multiplier;
    private double refugee_multiplier;
    private String name;

    public Vertex(String name) {
        this.name = name;
    }

    public void setAchievement(double achievement) {
        this.achievement = achievement;
    }

    public double getAchievement() {
        return achievement;
    }

    @Override
    public String toString() {
        return name;
    }
}
