package net.scardua.ga;

import java.util.Random;

/**
* Created with IntelliJ IDEA.
* User: wendel
* Date: 11/7/13
* Time: 22:53
* To change this template use File | Settings | File Templates.
*/
public class IntegerValue extends Value {
    private int value;

    public IntegerValue() {
        this(new Random().nextInt(256));
    }

    public IntegerValue(int value) {
        this.value = value;
    }

    public int get() {
        return this.value;
    }

    public void mutate() {
        this.value = new Random().nextInt(256);
    }

    @Override
    public int getBinaryValue() {
        if (this.value > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int getIntValue() {
        return this.value;
    }

    @Override
    public double getFloatValue() {
        return ((double) this.value) / 128.0 - 1.0;
    }

    public String toString() {
        return String.valueOf(this.value);
    }
}
