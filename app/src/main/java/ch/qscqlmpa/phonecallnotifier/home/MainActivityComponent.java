package ch.qscqlmpa.phonecallnotifier.home;


import ch.qscqlmpa.phonecallnotifier.di.ActivityScope;
import ch.qscqlmpa.phonecallnotifier.ui.NavigationModule;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ActivityScope
@Subcomponent(modules = {
        MainScreenBindingModule.class,
        NavigationModule.class
})
public interface MainActivityComponent extends AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<MainActivity> {

        /**
         * Overriden to avoid the possibility of MainActivity instance leak
         * (it forbids to inject MainActivity anywhere)
         * @param instance
         */
        @Override
        public void seedInstance(MainActivity instance) {

        }
    }
}
