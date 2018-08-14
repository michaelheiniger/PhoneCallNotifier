package ch.qscqlmpa.phonecallnotifier.test;

import android.content.Intent;

import org.junit.Rule;

import ch.qscqlmpa.phonecallnotifier.data.database.AppRoomDatabase;
import ch.qscqlmpa.phonecallnotifier.home.MainActivity;

public abstract class ControllerTest {

    @Rule
    public ControllerTestRule<MainActivity> testRule = new ControllerTestRule<>(MainActivity.class);

    protected AppRoomDatabase database = testRule.database;

    public ControllerTest() {
    }

    protected void launch() {
        launch(null);
    }

    protected void launch(Intent intent) {
        testRule.launchActivity(intent);
    }
}
