package ch.qscqlmpa.phonecallnotifier.base;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application application;

    ApplicationModule(Application application) {
        this.application = application;
    }

    /**
     * Useful to access sharedPreferences, system services, ...
     * @return
     */
    @Provides
    Context provideApplicationContext() {
        return application;
    }
}
