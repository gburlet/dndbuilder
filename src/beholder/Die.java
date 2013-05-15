package beholder;

import java.util.Random;

public class Die {
    private final int numSides;
    private Random r;

    public Die(int sides) {
        this.numSides = sides;

        r = new Random();
    }

    /*
     * Returns the values of rolling the die numTimes
     */
    public int[] roll(int numTimes) {
        int[] rollValues = new int[numTimes];

        for (int i = 0; i < numTimes; i++) {
            rollValues[i] = r.nextInt(this.numSides)+1;
        }

        return rollValues;
    }

    /*
     * Returns the cummulative value of rolling the die numTimes
     */
    public int rollCumulative(int numTimes) {
        int totalRollValue = 0;
        
        for (int i = 0; i < numTimes; i++) {
            totalRollValue += r.nextInt(this.numSides)+1;
        }

        return totalRollValue;
    }
}
