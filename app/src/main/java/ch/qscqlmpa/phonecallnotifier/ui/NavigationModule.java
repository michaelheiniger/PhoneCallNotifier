package ch.qscqlmpa.phonecallnotifier.ui;

import ch.qscqlmpa.phonecallnotifier.di.ActivityScope;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class NavigationModule {

    @Binds
    @ActivityScope
    abstract ScreenNavigator provideScreenNavigator(DefaultScreenNavigator screenNavigator);
}
