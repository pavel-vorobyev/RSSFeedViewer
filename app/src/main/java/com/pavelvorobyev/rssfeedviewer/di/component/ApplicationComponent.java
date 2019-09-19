package com.pavelvorobyev.rssfeedviewer.di.component;

import android.app.Application;
import com.pavelvorobyev.rssfeedviewer.di.module.ApplicationModule;
import com.pavelvorobyev.rssfeedviewer.di.module.PresenterModule;
import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(Application application);
    PresenterComponent plus(PresenterModule presenterModule);
}
