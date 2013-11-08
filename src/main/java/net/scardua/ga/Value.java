package net.scardua.ga;

/**
* Created with IntelliJ IDEA.
* User: wendel
* Date: 11/7/13
* Time: 22:52
* To change this template use File | Settings | File Templates.
*/
public abstract class Value {
    public abstract void mutate();
    public abstract int getBinaryValue();
    public abstract int getIntValue();
    public abstract double getFloatValue();
}
