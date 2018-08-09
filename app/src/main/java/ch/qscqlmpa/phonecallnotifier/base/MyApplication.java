package ch.qscqlmpa.phonecallnotifier.base;

import android.app.Application;

import javax.inject.Inject;

import ch.qscqlmpa.phonecallnotifier.BuildConfig;
import ch.qscqlmpa.phonecallnotifier.di.ActivityInjector;
import ch.qscqlmpa.phonecallnotifier.di.ServiceInjector;
import timber.log.Timber;

public class MyApplication extends Application {

    @Inject
    ActivityInjector activityInjector;

    @Inject
    ServiceInjector serviceInjector;

    protected ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = initComponent();
        component.inject(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    protected ApplicationComponent initComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ActivityInjector getActivityInjector() {
        return activityInjector;
    }

    public ServiceInjector getServiceInjector() {
        return serviceInjector;
    }

}
