package com.pavelvorobyev.rssfeedviewer.businesslogic.http;

import android.content.SharedPreferences;
import javax.inject.Inject;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ApiHelper {

    private OkHttpClient client;
    private Services services;

    @Inject
    public ApiHelper(SharedPreferences preferences) {
        this.client = getOkHttpClient(preferences);
        Retrofit retrofit = getRetrofit();
        this.services = retrofit.create(Services.class);
    }

    private OkHttpClient getOkHttpClient(SharedPreferences preferences) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(new DynamicUrlInterceptor(preferences))
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    private Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://example.url")
                .client(client)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Services getServices() {
        return services;
    }
}
