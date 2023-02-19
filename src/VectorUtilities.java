import java.util.ArrayList;

public class VectorUtilities {
    /**
     * <h1>dotProduct</h1>
     * Takes the dot product of two vectors of doubles.
     *
     * @author Erick White
     * @param vec1 first vector
     * @param vec2 second vector
     * @return dot product of two input vectors
     */
    public static double dotProduct(ArrayList<Double> vec1, ArrayList<Double> vec2) {
        double sum = 0;

        for (int i = 0; i < vec1.size(); i++) {
            sum += vec1.get(i) * vec2.get(i);
        }

        return sum;
    }

    /**
     * <h1>sumArray</h1>
     * Sums contents of an array.
     *
     * @author Erick White
     * @param vec array to sum (doubles)
     * @return sum of elements in array
     */
    public static double sumVector(ArrayList<Double> vec) {
        double sum = 0;

        for (double d : vec) {
            sum += d;
        }

        return sum;
    }

    public static double getMagnitude(ArrayList<Double> vec) {
        double sum = 0;

        for (double d : vec) {
            sum += Math.pow(d, 2);
        }

        return Math.sqrt(sum);
    }
}
