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
    private double value;

    public FloatValue() {
        this(new Random().nextDouble() * 2 - 1);
    }

    public FloatValue(double value) {
        this.value = value;
    }

    public double get() {
        return this.value;
    }

    public void mutate() {
        this.value += new Random().nextGaussian();
        if (this.value > 1.0) this.value = 1.0;
        if (this.value < -1.0) this.value = -1.0;
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
    public int getIntValue() {
        if (this.value < 0) {
            return -1;
        } else if (this.value > 0) {
            return 1;
        } else {
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
