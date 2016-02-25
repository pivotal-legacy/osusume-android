package io.pivotal.beach.osusume.android.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.pivotal.beach.osusume.android.R;

public class NewRestaurantFragment extends ApiFragment {

    public static final String TAG = NewRestaurantFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_restaurant, container, false);

        return rootView;
    }

    public static NewRestaurantFragment newInstance() {
        return new NewRestaurantFragment();
    }
}
