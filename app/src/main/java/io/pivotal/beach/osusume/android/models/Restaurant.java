package io.pivotal.beach.osusume.android.models;

public class Restaurant {
    Integer id;
    String name;
    Long created_at;
    User user;

    public Restaurant(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public Restaurant(Integer id, String name, Long created_at, User user) {
        this.id = id;
        this.name = name;
        this.created_at = created_at;
        this.user = user;
    }

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