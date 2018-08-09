package ch.qscqlmpa.phonecallnotifier.base;


import android.app.Activity;
import android.app.Service;

import ch.qscqlmpa.phonecallnotifier.ProcessIncomingCallIS;
import ch.qscqlmpa.phonecallnotifier.home.MainActivity;
import ch.qscqlmpa.phonecallnotifier.home.MainActivityComponent;
import ch.qscqlmpa.phonecallnotifier.home.ServiceComponent;
import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.android.ServiceKey;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {
        MainActivityComponent.class
})
public abstract class ActivityBindingModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> provideMainActivityInjector(MainActivityComponent.Builder builder);
}
