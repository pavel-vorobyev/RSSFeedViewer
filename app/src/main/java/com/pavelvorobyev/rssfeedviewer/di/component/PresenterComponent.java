package com.pavelvorobyev.rssfeedviewer.di.component;

import com.pavelvorobyev.rssfeedviewer.di.module.PresenterModule;
import com.pavelvorobyev.rssfeedviewer.di.scope.PresenterScope;
import com.pavelvorobyev.rssfeedviewer.ui.feed.FeedPresenter;
import com.pavelvorobyev.rssfeedviewer.ui.webview.DetailsPresenter;
import dagger.Subcomponent;

@PresenterScope
@Subcomponent(modules = PresenterModule.class)
public interface PresenterComponent {
    void inject(FeedPresenter target);
    void inject(DetailsPresenter target);
}
