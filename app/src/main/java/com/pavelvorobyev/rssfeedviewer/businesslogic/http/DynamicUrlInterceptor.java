package com.pavelvorobyev.rssfeedviewer.businesslogic.http;

import android.content.SharedPreferences;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class DynamicUrlInterceptor implements Interceptor {

    private SharedPreferences preferences;

    public DynamicUrlInterceptor(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String url = preferences.getString("url", "");

        Request request = chain.request();
        request = request.newBuilder()
                .url(url)
                .build();

        return chain.proceed(request);
    }
}
