package ch.qscqlmpa.phonecallnotifier.test;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;


import ch.qscqlmpa.phonecallnotifier.base.TestApplication;
import ch.qscqlmpa.phonecallnotifier.data.database.AppRoomDatabase;

public class ControllerTestRule<T extends Activity> extends ActivityTestRule<T> {

    public AppRoomDatabase database;

    public ControllerTestRule(Class<T> activityClass) {
        super(activityClass, true, false);
        database = TestApplication.getComponent().database();
    }

    public void clearState() {
        database.clearAllTables();

    }
}

