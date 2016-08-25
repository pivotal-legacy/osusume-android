package io.pivotal.beach.osusume.android.models;

public class Cuisine {

    private int id;

    private String name;

    public Cuisine(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
