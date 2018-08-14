package ch.qscqlmpa.phonecallnotifier.di;

import android.app.Activity;

import com.bluelinelabs.conductor.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import ch.qscqlmpa.phonecallnotifier.base.BaseActivity;
import ch.qscqlmpa.phonecallnotifier.base.BaseController;
import dagger.android.AndroidInjector;

@ActivityScope // This means that injected dependencies are retained across config. changes
public class ScreenInjector {

    private final Map<Class<? extends Controller>, Provider<AndroidInjector.Factory<? extends Controller>>> screenInjectors;
    private final Map<String, AndroidInjector<Controller>> cache = new HashMap<>();

    @Inject
    ScreenInjector(Map<Class<? extends Controller>, Provider<AndroidInjector.Factory<? extends Controller>>> screenInjectors) {
        this.screenInjectors = screenInjectors;
    }

    void inject(Controller controller) {
        if(!(controller instanceof BaseController)) {
            throw new IllegalArgumentException("Controller must extend BaseController");
        }

        String instanceId = controller.getInstanceId();
        if (cache.containsKey(instanceId)) {
            cache.get(instanceId).inject(controller);
            return;
        }

        //noinspection unchecked
        AndroidInjector.Factory<Controller> injectorFactory =
                (AndroidInjector.Factory<Controller>) screenInjectors.get(controller.getClass()).get();

        AndroidInjector<Controller> injector = injectorFactory.create(controller);

        cache.put(instanceId, injector);
        injector.inject(controller);
    }

    void clear(Controller controller) {
        AndroidInjector<?> injector = cache.remove(controller.getInstanceId());

        if(injector instanceof ScreenComponent) {
            ((ScreenComponent) injector).disposableManager().dispose();
        }
    }

    static ScreenInjector get(Activity activity) {
        if (!(activity instanceof BaseActivity)) {
            throw new IllegalArgumentException("Controller must be hosted by BaseActivity");
        }
        return ((BaseActivity) activity).getScreenInjector();
    }
}
