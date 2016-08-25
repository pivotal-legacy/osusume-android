package io.pivotal.beach.osusume.android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.annimon.stream.Stream;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.pivotal.beach.osusume.android.OsusumeApplication;
import io.pivotal.beach.osusume.android.R;
import io.pivotal.beach.osusume.android.api.OsusumeApiClient;
import io.pivotal.beach.osusume.android.models.Cuisine;
import io.pivotal.beach.osusume.android.models.NewRestaurant;
import io.pivotal.beach.osusume.android.models.PriceRange;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.annimon.stream.Collectors.toList;

public class NewRestaurantFragment extends ApiFragment {

    public static final String TAG = NewRestaurantFragment.class.getName();

    @Inject
    OsusumeApiClient osusumeApiClient;

    @Bind(R.id.newRestaurantCuisineTypeSpinner)
    Spinner cuisineSpinner;

    @Bind(R.id.newRestaurantPriceRangeSpinner)
    Spinner priceRangeSpinner;

    @Bind(R.id.newRestaurantNameField)
    EditText restaurantNameField;

    private List<Cuisine> cuisineList;

    private List<PriceRange> priceRangeList;

    public static NewRestaurantFragment newInstance() {
        return new NewRestaurantFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_restaurant, container, false);
        ((OsusumeApplication) getActivity().getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this, rootView);

        osusumeApiClient.getCuisines().enqueue(new Callback<List<Cuisine>>() {
            @Override
            public void onResponse(Call<List<Cuisine>> call, Response<List<Cuisine>> response) {
                cuisineList = response.body();
                List<String> cuisineNames = Stream.of(cuisineList).map(cuisine -> cuisine.getName()).collect(toList());
                ArrayAdapter cuisineAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, cuisineNames);
                cuisineSpinner.setAdapter(cuisineAdapter);
            }

            @Override
            public void onFailure(Call<List<Cuisine>> call, Throwable t) {

            }
        });

        osusumeApiClient.getPriceRanges().enqueue(new Callback<List<PriceRange>>() {
            @Override
            public void onResponse(Call<List<PriceRange>> call, Response<List<PriceRange>> response) {
                priceRangeList = response.body();
                List<String> priceRanges = Stream.of(priceRangeList).map(priceRange -> priceRange.getRange()).collect(toList());
                ArrayAdapter priceRangeAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, priceRanges);
                priceRangeSpinner.setAdapter(priceRangeAdapter);
            }

            @Override
            public void onFailure(Call<List<PriceRange>> call, Throwable t) {

            }
        });

        return rootView;
    }

    public NewRestaurant getNewRestaurant() {
        String restaurantName = restaurantNameField.getText().toString();
        Cuisine selectedCuisine = cuisineList.get(cuisineSpinner.getSelectedItemPosition());
        PriceRange selectedPriceRange = priceRangeList.get(priceRangeSpinner.getSelectedItemPosition());

        return new NewRestaurant(restaurantName, selectedCuisine.getId(), selectedPriceRange.getId(), new String[0]);
    }
}
