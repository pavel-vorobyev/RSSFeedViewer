package com.pavelvorobyev.rssfeedviewer.businesslogic.repository;

import com.pavelvorobyev.rssfeedviewer.businesslogic.http.ApiHelper;
import com.pavelvorobyev.rssfeedviewer.businesslogic.pojo.Rss;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class FeedRepository {

    private ApiHelper apiHelper;

    public FeedRepository(ApiHelper apiHelper) {
        this.apiHelper = apiHelper;
    }

    public Observable<Rss> getFeed() {
        return apiHelper.getServices().getFeed()
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::just);
    }

}
