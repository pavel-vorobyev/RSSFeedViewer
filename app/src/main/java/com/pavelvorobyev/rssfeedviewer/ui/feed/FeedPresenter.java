package com.pavelvorobyev.rssfeedviewer.ui.feed;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import com.pavelvorobyev.rssfeedviewer.businesslogic.http.ApiHelper;
import com.pavelvorobyev.rssfeedviewer.businesslogic.pojo.Item;
import com.pavelvorobyev.rssfeedviewer.businesslogic.repository.FeedRepository;
import com.pavelvorobyev.rssfeedviewer.di.component.PresenterComponent;
import com.pavelvorobyev.rssfeedviewer.ui.base.BaseMviPresenter;
import org.apache.commons.collections4.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@SuppressLint({"ApplySharedPref", "CheckResult"})
public class FeedPresenter extends BaseMviPresenter<FeedView, FeedViewState> {

    public static String TAG = "MY_TAG";

    @Inject
    ApiHelper apiHelper;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    FeedRepository feedRepository;

    private List<Item> loadedFeed = new ArrayList<>();

    @Override
    protected void bindIntents() {
        Observable<FeedViewState> getFeed = intent(FeedView::getFeed)
                .doOnNext(url -> {
                    sharedPreferences.edit()
                            .putString("url", url.trim())
                            .commit();
                })
                .flatMap(ignored -> {
                    if (!isNetworkAvailable())
                        return Observable.just(new FeedViewState.NoConnectionState());

                    return getFeed();
                })
                .observeOn(AndroidSchedulers.mainThread());

        Observable<FeedViewState> sortFeed = intent(FeedView::sortFeed)
                .map(kw -> {
                    List<Item> sortedItems = sortFeed(kw.trim());
                    return (FeedViewState) new FeedViewState.DataState(sortedItems);
                })
                .observeOn(AndroidSchedulers.mainThread());

        Observable<FeedViewState> allIntentsObservable = Observable.merge(getFeed, sortFeed);
        subscribeViewState(allIntentsObservable, FeedView::render);

    }

    @Override
    protected void injectComponent(PresenterComponent component) {
        component.inject(this);
    }

    private Observable<FeedViewState> getFeed() {
        return feedRepository.getFeed()
                .map(r -> {
                    replaceHtmlTagsInItems(r.getChannel().getItems());
                    loadedFeed = r.getChannel().getItems();
                    return (FeedViewState) new FeedViewState.DataState(r.getChannel().getItems());
                })
                .startWith(new FeedViewState.LoadingState())
                .onErrorReturn(t -> {
                    t.printStackTrace();
                    return new FeedViewState.ErrorState(t);
                })
                .subscribeOn(Schedulers.io());
    }

    private void replaceHtmlTagsInItems(List<Item> items) {
        for (Item item : items) {
            String newTitle = item.getTitle().replaceAll("<[^>]*>", "");
            String newDescription = item.getDescription().replaceAll("<[^>]*>", "");
            item.setTitle(newTitle.trim());
            item.setDescription(newDescription.trim());
        }
    }

    private List<Item> sortFeed(String keywords) {
        return (List<Item>) CollectionUtils.select(loadedFeed, object ->
                object.getTitle().contains(keywords) || object.getDescription().contains(keywords));
    }

}
