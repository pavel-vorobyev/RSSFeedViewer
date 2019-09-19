package com.pavelvorobyev.rssfeedviewer.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.pavelvorobyev.rssfeedviewer.BuildConfig;
import com.pavelvorobyev.rssfeedviewer.businesslogic.http.ApiHelper;
import com.pavelvorobyev.rssfeedviewer.businesslogic.repository.FeedRepository;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return provideApplication().getSharedPreferences(
                BuildConfig.APPLICATION_ID + "_shared_preferences_filename",
                Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper() {
        return new ApiHelper(provideSharedPreferences());
    }

    @Provides
    @Singleton
    FeedRepository provideFeedRepository() {
        return new FeedRepository(provideApiHelper());
    }

}
