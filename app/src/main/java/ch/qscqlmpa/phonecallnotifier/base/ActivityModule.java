package ch.qscqlmpa.phonecallnotifier.base;

import java.util.Set;

import ch.qscqlmpa.phonecallnotifier.di.ActivityScope;
import ch.qscqlmpa.phonecallnotifier.di.ForActivity;
import ch.qscqlmpa.phonecallnotifier.lifecycle.ActivityLifecycleTask;
import ch.qscqlmpa.phonecallnotifier.lifecycle.DisposableManager;
import ch.qscqlmpa.phonecallnotifier.lifecycle.ScreenLifecycleTask;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.Multibinds;

/**
 * This module has to be included in all Activity components that do not have a ActivityLifecycleTask in their dependency graph
 */
@Module
public abstract class ActivityModule {

    @Provides
    @ActivityScope
    @ForActivity
    static DisposableManager provideDisposableManager() {
        return new DisposableManager();
    }

    @Multibinds // needed if an activity has no ActivityLifecycleTask: if so, the set returned is empty (and Dagger is happy)
    abstract Set<ActivityLifecycleTask> activityLifecycleTaskSet();
}
