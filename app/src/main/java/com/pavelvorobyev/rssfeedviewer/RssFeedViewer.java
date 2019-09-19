package com.pavelvorobyev.rssfeedviewer;

import android.app.Application;

import com.pavelvorobyev.rssfeedviewer.di.component.ApplicationComponent;
import com.pavelvorobyev.rssfeedviewer.di.component.DaggerApplicationComponent;
import com.pavelvorobyev.rssfeedviewer.di.module.ApplicationModule;

public class RssFeedViewer extends Application {

    public ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(new ApplicationModule((this)))
            .build();

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
