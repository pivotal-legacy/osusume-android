package io.pivotal.beach.osusume.android;

import javax.inject.Singleton;

import dagger.Component;
import io.pivotal.beach.osusume.android.activities.NewRestaurantActivity;
import io.pivotal.beach.osusume.android.activities.RestaurantListActivity;
import io.pivotal.beach.osusume.android.fragments.ApiFragment;

@Singleton
@Component(modules={AppModule.class})
public interface AppComponent {
    void inject(ApiFragment fragment);
    void inject(NewRestaurantActivity activity);
    void inject(RestaurantListActivity activity);
}
