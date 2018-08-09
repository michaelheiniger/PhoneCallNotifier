package ch.qscqlmpa.phonecallnotifier.base;

import android.app.Service;

import ch.qscqlmpa.phonecallnotifier.ProcessIncomingCallIS;
import ch.qscqlmpa.phonecallnotifier.home.ServiceComponent;
import dagger.Binds;
import dagger.Module;

import dagger.android.AndroidInjector;
import dagger.android.ServiceKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {
        ServiceComponent.class
})
public abstract class ServiceModule {

    @Binds
    @IntoMap
    @ServiceKey(ProcessIncomingCallIS.class)
    abstract AndroidInjector.Factory<? extends Service> provideServiceInjector(ServiceComponent.Builder builder);
}

