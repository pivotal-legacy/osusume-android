package io.pivotal.beach.osusume.android.presenters;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

import io.pivotal.beach.osusume.android.models.Restaurant;

// TODO: Unit test this class
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

    public String getCreatedAtInText() {
        return "Created on " + getDate(restaurant.getCreatedAt());
    }

    private String getDate(Long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis((time.longValue() * 1000));
        return DateFormat.format("MM/dd/yy", cal).toString();
    }
}
