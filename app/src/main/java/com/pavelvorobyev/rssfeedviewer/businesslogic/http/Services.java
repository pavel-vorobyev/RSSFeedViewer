package com.pavelvorobyev.rssfeedviewer.businesslogic.http;

import com.pavelvorobyev.rssfeedviewer.businesslogic.pojo.Rss;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Services {

    @GET("/path")
    Observable<Rss> getFeed();

}
