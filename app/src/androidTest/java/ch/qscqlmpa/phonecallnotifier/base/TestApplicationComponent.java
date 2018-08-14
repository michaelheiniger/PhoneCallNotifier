package ch.qscqlmpa.phonecallnotifier.base;

import javax.inject.Singleton;

import ch.qscqlmpa.phonecallnotifier.data.database.AppRoomDatabase;
import ch.qscqlmpa.phonecallnotifier.data.database.TestDatabaseModule;
import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        TestActivityBindingModule.class,
        ServiceModule.class,
        TestDatabaseModule.class,
})
public interface TestApplicationComponent extends ApplicationComponent {

    AppRoomDatabase database();
}
