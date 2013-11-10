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

    public FloatValue() {
        this(new Random().nextDouble());
    }

    public FloatValue(double value) {
        this.value = value;
    }

    public void mutate() {        
        this.value = this.value + (Math.random() - Math.random()) * MAX_PERTURBATION;
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
