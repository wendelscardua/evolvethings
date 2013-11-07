package net.scardua.ga;

/**
* Created with IntelliJ IDEA.
* User: wendel
* Date: 11/7/13
* Time: 01:26
*/
public enum Gene {
    COLOR_RED("red", 0, GeneticAlgorithm.BinaryValue.class),
    COLOR_GREEN("green", 1, GeneticAlgorithm.BinaryValue.class),
    COLOR_BLUE("blue", 2, GeneticAlgorithm.BinaryValue.class),
    POWER("power", 3, GeneticAlgorithm.FloatValue.class),
    RESISTANCE("resistance", 4, GeneticAlgorithm.FloatValue.class),
    LIFE("life", 5, GeneticAlgorithm.FloatValue.class)
    ;

    public final String name;
    public final int position;
    public final Class<? extends GeneticAlgorithm.Value> type;

    Gene(String name, int position, Class<? extends GeneticAlgorithm.Value> type) {
        this.name = name;
        this.position = position;
        this.type = type;
    }
}
