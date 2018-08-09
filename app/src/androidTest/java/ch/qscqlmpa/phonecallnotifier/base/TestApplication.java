package ch.qscqlmpa.phonecallnotifier.base;

import android.support.test.InstrumentationRegistry;

public class TestApplication extends MyApplication {

    @Override
    protected ApplicationComponent initComponent() {
        return DaggerTestApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static TestApplicationComponent getComponent() {
        return (TestApplicationComponent)
                ((TestApplication) InstrumentationRegistry.getTargetContext().getApplicationContext()).component;
    }
}
