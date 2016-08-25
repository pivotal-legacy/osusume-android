package io.pivotal.beach.osusume.android.models;

public class PriceRange {

    private int id;

    private String range;

    public PriceRange(int id, String range) {
        this.id = id;
        this.range = range;
    }

    public String getRange() {
        return range;
    }

    public int getId() {
        return id;
    }

}
