package ch.qscqlmpa.phonecallnotifier.base;

import javax.inject.Singleton;

import ch.qscqlmpa.phonecallnotifier.data.database.AppRoomDatabase;
import ch.qscqlmpa.phonecallnotifier.data.database.TestDatabaseModule;
import ch.qscqlmpa.phonecallnotifier.ui.TestNavigationModule;
import ch.qscqlmpa.phonecallnotifier.ui.TestScreenNavigator;
import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        TestActivityBindingModule.class,
        ServiceModule.class,
        TestDatabaseModule.class,
        TestNavigationModule.class,
})
public interface TestApplicationComponent extends ApplicationComponent {

    TestScreenNavigator screenNavigator();

    AppRoomDatabase database();
}
