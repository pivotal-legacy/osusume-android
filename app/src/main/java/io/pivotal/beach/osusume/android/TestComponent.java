package io.pivotal.beach.osusume.android;

import javax.inject.Singleton;

import dagger.Component;
import io.pivotal.beach.osusume.android.activities.LoginActivity;
import io.pivotal.beach.osusume.android.activities.NewRestaurantActivity;
import io.pivotal.beach.osusume.android.fragments.ApiFragment;
import io.pivotal.beach.osusume.android.fragments.NewRestaurantFragment;

@Singleton
@Component(modules={TestModule.class})
public interface TestComponent extends AppComponent {
    void inject(ApiFragment fragment);
    void inject(NewRestaurantActivity activity);
    void inject(LoginActivity activity);
    void inject(NewRestaurantFragment activity);
}
