package io.pivotal.beach.osusume.android;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.pivotal.beach.osusume.android.api.AuthorizationHeaderInterceptor;
import io.pivotal.beach.osusume.android.api.OsusumeApiClient;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class TestModule {

    private HttpUrl baseUrl = HttpUrl.parse("http://192.168.16.59:8080");

    public TestModule(HttpUrl baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    @Singleton
    AuthorizationHeaderInterceptor provideAuthorizationHeaderInterceptor() {
        return new AuthorizationHeaderInterceptor();
    }

    @Provides
    @Singleton
    OsusumeApiClient provideOsusumeApiClient(AuthorizationHeaderInterceptor authorizationHeaderInterceptor) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(authorizationHeaderInterceptor)
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(OsusumeApiClient.class);
    }
}
