package io.pivotal.beach.osusume.android;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={AppModule.class})
public interface AppComponent {
     void inject(RestaurantDetailFragment fragment);
     void inject(RetaurantListFragment fragment);
}
