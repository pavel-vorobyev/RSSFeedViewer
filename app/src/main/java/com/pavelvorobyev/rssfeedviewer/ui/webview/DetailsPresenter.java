package com.pavelvorobyev.rssfeedviewer.ui.webview;

import android.content.Intent;
import com.pavelvorobyev.rssfeedviewer.di.component.PresenterComponent;
import com.pavelvorobyev.rssfeedviewer.ui.base.BaseMviActivity;
import com.pavelvorobyev.rssfeedviewer.ui.base.BaseMviPresenter;

public class DetailsPresenter extends BaseMviPresenter<DetailsView, DetailsViewState> {


    @Override
    public void init(BaseMviActivity activity, Intent intent) {
        super.init(activity, intent);
    }

    @Override
    protected void bindIntents() {

    }

    @Override
    protected void injectComponent(PresenterComponent component) {
        component.inject(this);
    }

}
