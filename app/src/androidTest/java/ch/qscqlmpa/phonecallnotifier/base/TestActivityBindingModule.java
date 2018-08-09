package ch.qscqlmpa.phonecallnotifier.base;

import android.app.Activity;

import ch.qscqlmpa.phonecallnotifier.home.MainActivity;
import ch.qscqlmpa.phonecallnotifier.home.TestMainActivityComponent;
import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = TestMainActivityComponent.class)
public abstract class TestActivityBindingModule {

    @Binds
    @IntoMap
    @ActivityKey(MainActivity.class)
    abstract AndroidInjector.Factory<? extends Activity> bindMainActivityInjector(TestMainActivityComponent.Builder builder);
}
