package io.pivotal.beach.osusume.android.models;

public class Restaurant {
    Integer id;
    String name;
    Long created_at;
    User user;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getCreatedAt() {
        return created_at;
    }

    public User getUser() {
        return user;
    }
}