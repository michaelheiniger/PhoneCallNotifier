package ch.qscqlmpa.phonecallnotifier.di;

import android.app.Activity;
import android.app.Service;

import com.bluelinelabs.conductor.Controller;

import ch.qscqlmpa.phonecallnotifier.base.BaseActivity;

public class Injector {

    private Injector() {}

    public static void inject(Activity activity) {
        ActivityInjector.get(activity).inject(activity);
    }

    public static void clearComponent(Activity activity) {
        ActivityInjector.get(activity).clear(activity);
    }

    public static void inject(Controller controller) {
        ScreenInjector.get(controller.getActivity()).inject(controller);
    }

    public static void clearComponent(Controller controller) {
        ScreenInjector.get(controller.getActivity()).clear(controller);
    }

    public static void inject(Service service) {
        ServiceInjector.get(service).inject(service);
    }
}
