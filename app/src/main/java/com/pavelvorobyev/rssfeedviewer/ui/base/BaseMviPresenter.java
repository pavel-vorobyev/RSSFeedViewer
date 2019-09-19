package com.pavelvorobyev.rssfeedviewer.ui.base;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter;
import com.hannesdorfmann.mosby3.mvp.MvpView;
import com.pavelvorobyev.rssfeedviewer.RssFeedViewer;
import com.pavelvorobyev.rssfeedviewer.di.component.PresenterComponent;
import com.pavelvorobyev.rssfeedviewer.di.module.PresenterModule;

public abstract class BaseMviPresenter<V extends MvpView, VS> extends MviBasePresenter<V, VS> {

    private Context context;
    private Intent intent;

    public void init(BaseMviActivity activity, Intent intent) {
        context = activity;
        this.intent = intent;

        PresenterComponent component = ((RssFeedViewer) activity.getApplication())
                .applicationComponent
                .plus(new PresenterModule());
        injectComponent(component);
    }

    protected boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    protected Intent getIntent() {
        return intent;
    }

    protected abstract void injectComponent(PresenterComponent component);

}
