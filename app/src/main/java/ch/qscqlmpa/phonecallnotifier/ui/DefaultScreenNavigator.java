package ch.qscqlmpa.phonecallnotifier.ui;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;

import javax.inject.Inject;

import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormat;
import ch.qscqlmpa.phonecallnotifier.di.ActivityScope;
import ch.qscqlmpa.phonecallnotifier.phonenumberformat.PhoneNumberFormatController;

/**
 * Provided to activities through NavigationModule
 */
public class DefaultScreenNavigator implements ScreenNavigator {

    private Router router;

    @Inject
    DefaultScreenNavigator() {

    }

    @Override
    public void initWithRouter(Router router, Controller controller) {
        this.router = router;

        if (!router.hasRootController()) {
            RouterTransaction rt = RouterTransaction.with(controller);
            this.router.setRoot(rt);
        }
    }

    @Override
    public boolean pop() {
        // true if the router handled the back request
        // false if the backstack has only one item
        return router != null && router.handleBack();
//        return false; //TODO
    }

    @Override
    public void clear() {
        router = null;
    }
}
