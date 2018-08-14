package ch.qscqlmpa.phonecallnotifier.base;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

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

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

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
