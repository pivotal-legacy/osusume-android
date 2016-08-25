package io.pivotal.beach.osusume.android.models;

import com.google.gson.annotations.SerializedName;

public class NewRestaurant {
    private final String name;

    @SerializedName("cuisine_type_id")
    private final int cuisineTypeId;

    @SerializedName("price_range_id")
    private final int priceRangeId;

    @SerializedName("photo_urls")
    private final String[] photoUrls;

    public NewRestaurant(String name, int cuisineTypeId, int priceRangeId, String[] photoUrls) {
        this.name = name;
        this.cuisineTypeId = cuisineTypeId;
        this.priceRangeId = priceRangeId;
        this.photoUrls = photoUrls;
    }

    public String getName() {
        return name;
    }

    public int getCuisineTypeId() {
        return cuisineTypeId;
    }

    public int getPriceRangeId() {
        return priceRangeId;
    }
}