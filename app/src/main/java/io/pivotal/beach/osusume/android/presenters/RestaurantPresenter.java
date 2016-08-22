package io.pivotal.beach.osusume.android.presenters;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.pivotal.beach.osusume.android.models.Restaurant;

public class RestaurantPresenter {
    Restaurant restaurant;

    public RestaurantPresenter(Restaurant restaurant) {
        this.restaurant = restaurant;
    }


    public String getName() {
        return restaurant.getName();
    }

    public String getAuthorName() {
        return "Added by " + restaurant.getUser().getName();
    }

    public String getCreatedAt() {
        return "Created on " + restaurant.getCreatedAt();
    }
}
