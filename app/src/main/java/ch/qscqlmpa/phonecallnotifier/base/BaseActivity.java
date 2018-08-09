package ch.qscqlmpa.phonecallnotifier.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.Router;

import java.util.UUID;

import javax.inject.Inject;

import ch.qscqlmpa.phonecallnotifier.R;
import ch.qscqlmpa.phonecallnotifier.di.Injector;
import ch.qscqlmpa.phonecallnotifier.di.ScreenInjector;
import ch.qscqlmpa.phonecallnotifier.ui.ScreenNavigator;

public abstract class BaseActivity extends AppCompatActivity {

    private static String INSTANCE_ID_KEY = "instance_id";

    @Inject ScreenInjector screenInjector;
    @Inject ScreenNavigator screenNavigator;

    private String instanceId;
    private Router router;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            instanceId = savedInstanceState.getString(INSTANCE_ID_KEY);
        } else {
            instanceId = UUID.randomUUID().toString();
        }
        Injector.inject(this);

        setContentView(layoutRes());

        ViewGroup controllerContainer = findViewById(R.id.controller_container);

        if (controllerContainer == null) {
            throw new NullPointerException("Activity must have a view with id:controller_container");
        }

        router = Conductor.attachRouter(this, controllerContainer, savedInstanceState);

        screenNavigator.initWithRouter(router, initialScreen());
//        monitorBackStack();

        super.onCreate(savedInstanceState);
    }

    /**
     * Provide the layout resource
     * @return
     */
    @LayoutRes
    protected abstract int layoutRes();

    /**
     * Provides the initial controller to the Activity
     * @return
     */
    protected abstract Controller initialScreen();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(INSTANCE_ID_KEY, instanceId);
    }

    @Override
    public void onBackPressed() {
        // If the screenNavigator did NOT handle the pop, then we finish the activity
//        if (!screenNavigator.pop()) {
            super.onBackPressed();
//        }
    }

    public String getInstanceId() {
        return instanceId;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        screenNavigator.clear();

        if (isFinishing()) {
            Injector.clearComponent(this);
        }
    }

    public ScreenInjector getScreenInjector() {
        return screenInjector;
    }


    private void monitorBackStack() {
        router.addChangeListener(new ControllerChangeHandler.ControllerChangeListener() {
            @Override
            public void onChangeStarted(
                    @Nullable Controller to,
                    @Nullable Controller from,
                    boolean isPush, @NonNull ViewGroup container,
                    @NonNull ControllerChangeHandler handler) {


            }

            @Override
            public void onChangeCompleted(@Nullable Controller to,
                                          @Nullable Controller from,
                                          boolean isPush,
                                          @NonNull ViewGroup container,
                                          @NonNull ControllerChangeHandler handler) {

                // Pop event: the controller is not in the backstack anymore, we don't want
                // its component to survive
                if (!isPush && from != null) {
                    Injector.clearComponent(from);
                }

            }
        });
    }
}
