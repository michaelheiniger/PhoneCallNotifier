package ch.qscqlmpa.phonecallnotifier.ui;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;

import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormat;

/**
 * Handles navigation
 * Abstract the navigation logic away from the view layer (makes testing easier)
 */
public interface ScreenNavigator {

    /**
     *
     * @param router
     * @param controller
     */
    void initWithRouter(Router router, Controller controller);

    /**
     * Pop the current screen.
     * @return true the screen was popped
     */
    boolean pop();

    /**
     * Clear any reference (Router, ...) when the activity is destroyed
     */
    void clear();
}
