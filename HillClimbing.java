import java.util.Random;

public class HillClimbing {

    // Function to be optimized (replace with your actual function)
    static double objectiveFunction(double x) {
        return x * x; // This example finds the maximum of a quadratic function
    }

    // Hill climbing algorithm
    static double hillClimbing(double lowerBound, double upperBound, double stepSize, int maxIterations) {
        // Initialize random number generator
        Random rand = new Random();

        // Start with a random solution
        double currentSolution = lowerBound + (upperBound - lowerBound) * rand.nextDouble();
        double currentVal = objectiveFunction(currentSolution);

        // Perform iterations
        for (int i = 0; i < maxIterations; ++i) {
            // Generate a random neighbor
            double neighbor = currentSolution + stepSize * (rand.nextDouble() - 0.5);

            // Ensure neighbor stays within bounds
            neighbor = Math.max(lowerBound, Math.min(neighbor, upperBound));

            // Evaluate neighbor
            double neighborVal = objectiveFunction(neighbor);

            // Update current solution if neighbor is better
            if (neighborVal > currentVal) {
                currentSolution = neighbor;
                currentVal = neighborVal;
            }
        }

        return currentSolution;
    }

    public static void main(String[] args) {
        double lowerBound = -1.0;
        double upperBound = 1.0;
        double stepSize = 0.1;
        int maxIterations = 1000;

        double bestSolution = hillClimbing(lowerBound, upperBound, stepSize, maxIterations);

        System.out.println("Best solution found: " + bestSolution);
    }
}
