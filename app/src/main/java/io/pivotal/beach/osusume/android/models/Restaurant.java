package io.pivotal.beach.osusume.android.models;

public class Restaurant {
    String name;
    Long created_at;
    User user;

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