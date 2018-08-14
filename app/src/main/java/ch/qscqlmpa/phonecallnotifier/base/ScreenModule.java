package ch.qscqlmpa.phonecallnotifier.base;

import java.util.Set;

import ch.qscqlmpa.phonecallnotifier.di.ForScreen;
import ch.qscqlmpa.phonecallnotifier.di.ScreenScope;
import ch.qscqlmpa.phonecallnotifier.lifecycle.DisposableManager;
import ch.qscqlmpa.phonecallnotifier.lifecycle.ScreenLifecycleTask;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.Multibinds;

/**
 * This module has to be included in all Screen components that do not have a ScreenLifecycleTask in their dependency graph
 */
@Module
public abstract class ScreenModule {

    @Provides
    @ScreenScope
    @ForScreen
    static DisposableManager provideDisposableManager() {
        return new DisposableManager();
    }

    @Multibinds // needed if a screen has no ScreenLifecycleTask: if so, the set returned is empty (and Dagger is happy)
    abstract Set<ScreenLifecycleTask> screenLifecycleTaskSet();
}
