package ch.qscqlmpa.phonecallnotifier.lifecycle;

import android.view.View;

public abstract class ScreenLifecycleTask {

    /**
     * Callback reeived when Screen becomes the visible screen
     * @param view
     */
    public void onEnterScope(View view) {

    }

    /**
     * Callback received when a Screen is either popped or moved to the back stack.
     */
    public void onExitScope() {

    }

    /**
     * Callback received when a Screen is destroyed and will not be coming back (except as a new instance).
     * This should be used to clear any {@link ch.qscqlmpa.phonecallnotifier.di.ActivityScope}
     * connections (Disposables, ...)
     */
    public void onDestroy() {

    }
}
