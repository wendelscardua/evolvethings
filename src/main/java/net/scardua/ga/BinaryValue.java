package net.scardua.ga;

import java.util.Random;

/**
* Created with IntelliJ IDEA.
* User: wendel
* Date: 11/7/13
* Time: 22:53
* To change this template use File | Settings | File Templates.
*/
public class BinaryValue extends Value {
    private int value;

    public BinaryValue() {
        this(new Random().nextInt(2));
    }

    public BinaryValue(int value) {
        this.value = (value == 0 ? 0 : 1);
    }

    public void mutate() {
        this.value = 1 - this.value;
    }

    @Override
    public int getBinaryValue() {
        return value;
    }

    @Override
    public double getFloatValue() {
        return (double) value;
    }

    public String toString() {
        return String.valueOf(this.value);
    }
}
