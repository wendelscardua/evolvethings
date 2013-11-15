package net.scardua.ga;

import java.util.Random;

/**
* Created with IntelliJ IDEA.
* User: wendel
* Date: 11/7/13
* Time: 22:53
* To change this template use File | Settings | File Templates.
*/
public class FloatValue extends Value {
    private static final double MAX_PERTURBATION = 0.3;
    private double value;
    private static final Random random = new Random();

    public FloatValue() {
        this(2 * (random.nextDouble() - random.nextDouble()));
    }

    public FloatValue(double value) {
        this.value = value;
    }

    public void mutate() {        
        this.value = this.value + random.nextGaussian();
    }

    @Override
    public int getBinaryValue() {
        if (this.value > 0) {
            return 1;
        }  else {
            return 0;
        }
    }

    @Override
    public double getFloatValue() {
        return value;
    }

    public String toString() {
        return String.valueOf(this.value);
    }
}
