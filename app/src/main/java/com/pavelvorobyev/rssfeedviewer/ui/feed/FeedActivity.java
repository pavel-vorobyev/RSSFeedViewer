package com.pavelvorobyev.rssfeedviewer.ui.feed;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.pavelvorobyev.rssfeedviewer.R;
import com.pavelvorobyev.rssfeedviewer.businesslogic.pojo.Item;
import com.pavelvorobyev.rssfeedviewer.ui.base.BaseMviActivity;
import com.pavelvorobyev.rssfeedviewer.ui.webview.DetailsActivity;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class FeedActivity extends BaseMviActivity<FeedView, FeedPresenter, FeedViewState>
        implements FeedView {

    @BindView(R.id.urlInputView)
    EditText urlInputView;
    @BindView(R.id.getFeedBtnView)
    ImageButton getFeedBtnView;
    @BindView(R.id.settingsBtnView)
    ImageButton settingsBtnView;
    @BindView(R.id.progressView)
    ProgressBar progressView;
    @BindView(R.id.noInternetMessageView)
    TextView noInternetMessageView;
    @BindView(R.id.errorMessageView)
    TextView errorMessageView;
    @BindView(R.id.feedContainerView)
    RecyclerView feedContainerView;

    private PublishSubject<String> getFeedSubject;
    private PublishSubject<String> sortFeedSubject;
    private FeedAdapter adapter;
    private FeedFilterDialog filterDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFeedSubject = PublishSubject.create();
        sortFeedSubject = PublishSubject.create();
        filterDialog = new FeedFilterDialog();

        adapter = new FeedAdapter();
        adapter.setCallback(item -> {
            DetailsActivity.start(this, item.getLink());
        });

        feedContainerView.setLayoutManager(new LinearLayoutManager(this));
        feedContainerView.setAdapter(adapter);

        settingsBtnView.setOnClickListener(v -> {
            if (!filterDialog.isAdded())
                filterDialog.showNow(getSupportFragmentManager(), "f_f_d");
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        getFeedBtnView.setOnClickListener(v -> {
            hideKeyboard();
            getFeedSubject.onNext(urlInputView.getText().toString());
        });

        filterDialog.setCallback(keywords -> {
            sortFeedSubject.onNext(keywords);
            hideKeyboard();
        });
    }

    @NonNull
    @Override
    public FeedPresenter createPresenter() {
        FeedPresenter presenter = new FeedPresenter();
        presenter.init(this, getIntent());
        return presenter;
    }

    @NotNull
    @Override
    public Observable<String> getFeed() {
        return getFeedSubject;
    }

    @NotNull
    @Override
    public Observable<String> sortFeed() {
        return sortFeedSubject;
    }

    @MainThread
    @Override
    public void render(FeedViewState state) {
        if (state instanceof FeedViewState.DataState) {
            dataState(((FeedViewState.DataState) state).getFeed());
        }else if (state instanceof FeedViewState.LoadingState) {
            loadingState();
        }else if (state instanceof FeedViewState.ErrorState) {
            errorState();
        }else if (state instanceof FeedViewState.NoConnectionState)
            noInternetConnectionErrorState();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    private void loadingState() {
        noInternetMessageView.setVisibility(View.GONE);
        errorMessageView.setVisibility(View.GONE);
        feedContainerView.setVisibility(View.GONE);
        progressView.setVisibility(View.VISIBLE);
    }

    private void errorState() {
        noInternetMessageView.setVisibility(View.GONE);
        feedContainerView.setVisibility(View.GONE);
        progressView.setVisibility(View.GONE);
        errorMessageView.setVisibility(View.VISIBLE);
    }

    private void noInternetConnectionErrorState() {
        errorMessageView.setVisibility(View.GONE);
        feedContainerView.setVisibility(View.GONE);
        progressView.setVisibility(View.GONE);
        noInternetMessageView.setVisibility(View.VISIBLE);
    }

    private void dataState(List<Item> items) {
        noInternetMessageView.setVisibility(View.GONE);
        errorMessageView.setVisibility(View.GONE);
        progressView.setVisibility(View.GONE);
        feedContainerView.setVisibility(View.VISIBLE);
        adapter.updateItems(items);
    }

}
