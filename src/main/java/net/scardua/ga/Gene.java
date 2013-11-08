package net.scardua.ga;

/**
* Created with IntelliJ IDEA.
* User: wendel
* Date: 11/7/13
* Time: 01:26
*/
public enum Gene {
    COLOR_RED("red", 0, BinaryValue.class),
    COLOR_GREEN("green", 1, BinaryValue.class),
    COLOR_BLUE("blue", 2, BinaryValue.class),
    POWER("power", 3, FloatValue.class),
    RESISTANCE("resistance", 4, FloatValue.class),
    LIFE("life", 5, FloatValue.class)
    ;

    public final String name;
    public final int position;
    public final Class<? extends Value> type;

    Gene(String name, int position, Class<? extends Value> type) {
        this.name = name;
        this.position = position;
        this.type = type;
    }
}
