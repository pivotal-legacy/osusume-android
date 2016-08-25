package io.pivotal.beach.osusume.android.models;

public class RestaurantWrapper {

    private final NewRestaurant restaurant;

    public RestaurantWrapper(NewRestaurant restaurant) {
        this.restaurant = restaurant;
    }

    public NewRestaurant getRestaurant() {
        return restaurant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestaurantWrapper that = (RestaurantWrapper) o;

        return restaurant != null ? restaurant.equals(that.restaurant) : that.restaurant == null;
    }

    @Override
    public int hashCode() {
        return restaurant != null ? restaurant.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "RestaurantWrapper{" +
                "restaurant=" + restaurant +
                '}';
    }
}
