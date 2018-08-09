package ch.qscqlmpa.phonecallnotifier.di;

import android.app.Application;
import android.app.Service;
import android.content.Context;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import ch.qscqlmpa.phonecallnotifier.base.BaseService;
import ch.qscqlmpa.phonecallnotifier.base.MyApplication;
import dagger.android.AndroidInjector;

@Singleton
public class ServiceInjector {
    private final Map<Class<? extends Service>, Provider<AndroidInjector.Factory<? extends Service>>> serviceInjectors;

    @Inject
    ServiceInjector(Map<Class<? extends Service>, Provider<AndroidInjector.Factory<? extends Service>>> serviceInjectors) {
        this.serviceInjectors = serviceInjectors;
    }

    void inject(Service service) {
        if(!(service instanceof BaseService)) {
            throw new IllegalArgumentException("Service must extend BaseService");
        }


        //noinspection unchecked
        AndroidInjector.Factory<Service> injectorFactory =
                (AndroidInjector.Factory<Service>) serviceInjectors.get(service.getClass()).get();

        AndroidInjector<Service> injector = injectorFactory.create(service);

        injector.inject(service);
    }

    static ServiceInjector get(Context context) {
        return ((MyApplication) context.getApplicationContext()).getServiceInjector();
    }
}
