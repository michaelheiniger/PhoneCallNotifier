package ch.qscqlmpa.phonecallnotifier.test;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;


import ch.qscqlmpa.phonecallnotifier.base.TestApplication;
import ch.qscqlmpa.phonecallnotifier.data.database.AppRoomDatabase;
import ch.qscqlmpa.phonecallnotifier.ui.TestScreenNavigator;

public class ControllerTestRule<T extends Activity> extends ActivityTestRule<T> {

    public final TestScreenNavigator screenNavigator;
    public AppRoomDatabase database;

    public ControllerTestRule(Class<T> activityClass) {
        super(activityClass, true, false);
        screenNavigator = TestApplication.getComponent().screenNavigator();
        database = TestApplication.getComponent().database();
    }

    public void clearState() {
        database.clearAllTables();

    }
}

