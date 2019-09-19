package com.pavelvorobyev.rssfeedviewer.ui.feed;

import com.hannesdorfmann.mosby3.mvp.MvpView;
import org.jetbrains.annotations.NotNull;
import io.reactivex.Observable;

public interface FeedView extends MvpView {

    @NotNull
    Observable<String> getFeed();
    @NotNull
    Observable<String> sortFeed();
    void render(FeedViewState state);

}
