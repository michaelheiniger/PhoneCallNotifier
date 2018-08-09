package ch.qscqlmpa.phonecallnotifier.base;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import ch.qscqlmpa.phonecallnotifier.di.Injector;
import ch.qscqlmpa.phonecallnotifier.di.ServiceInjector;

public abstract class BaseService extends IntentService {

    @Inject
    ServiceInjector serviceInjector;

    public BaseService(String name) {
        super(name);
    }


    @Override
    public void onCreate() {
        Injector.inject(this);
        super.onCreate();
    }

    public ServiceInjector getServiceInjector() {
        return serviceInjector;
    }
}
