package ch.qscqlmpa.phonecallnotifier.base;

import javax.inject.Singleton;

import ch.qscqlmpa.phonecallnotifier.data.database.DatabaseModule;
import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        ActivityBindingModule.class,
        ServiceModule.class,
        DatabaseModule.class
})
public interface ApplicationComponent {

    void inject(MyApplication myApplication);
}
