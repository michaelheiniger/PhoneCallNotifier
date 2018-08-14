package ch.qscqlmpa.phonecallnotifier.home;

import ch.qscqlmpa.phonecallnotifier.base.ActivityModule;
import ch.qscqlmpa.phonecallnotifier.di.ActivityScope;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ActivityScope
@Subcomponent(modules = {
        TestScreenBindingModule.class,
        ActivityModule.class
})
public interface TestMainActivityComponent extends AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> {

    }
}
