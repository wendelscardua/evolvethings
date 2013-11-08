package net.scardua.ga;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

/**
* Created with IntelliJ IDEA.
* User: wendel
* Date: 11/7/13
* Time: 01:26
*/
public class Genes {
//    COLOR_RED("red", 0, BinaryValue.class),
//    COLOR_GREEN("green", 1, BinaryValue.class),
//    COLOR_BLUE("blue", 2, BinaryValue.class),
//    POWER("power", 3, FloatValue.class),
//    RESISTANCE("resistance", 4, FloatValue.class),
//    LIFE("life", 5, FloatValue.class)
//    ;

    private static final Genes instance = new Genes();

    private LinkedHashMap<String, Gene> genes = new LinkedHashMap<String, Gene>();

    private Genes() {

    }

    public static Genes getInstance() {
        return instance;
    }

    public Genes addGene(String name, Class<? extends Value> type) {
        genes.put(name, new Gene(name, genes.size(), type));
        return this;
    }

    public Collection<Gene> values() {
        return this.genes.values();
    }

    public Gene getGene(String geneName) {
        return this.genes.get(geneName);
    }

    public static class Gene {
        public final String name;
        public final int position;
        public final Class<? extends Value> type;

        public Gene(String name, int position, Class<? extends Value> type) {
            this.name = name;
            this.position = position;
            this.type = type;
        }
    }
}