package ch.qscqlmpa.phonecallnotifier.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;

import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import ch.qscqlmpa.phonecallnotifier.R;
import ch.qscqlmpa.phonecallnotifier.di.Injector;
import ch.qscqlmpa.phonecallnotifier.di.ScreenInjector;
import ch.qscqlmpa.phonecallnotifier.lifecycle.ActivityLifecycleTask;

public abstract class BaseActivity extends AppCompatActivity {

    private static String INSTANCE_ID_KEY = "instance_id";

    @Inject ScreenInjector screenInjector;
    @Inject Set<ActivityLifecycleTask> activityLifecycleTaskSet;

    private String instanceId;

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

        Router router = Conductor.attachRouter(this, controllerContainer, savedInstanceState);
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(initialScreen()));
        }

        for (ActivityLifecycleTask task : activityLifecycleTaskSet) {
            task.onCreate(this);
        }
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


    public String getInstanceId() {
        return instanceId;
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (ActivityLifecycleTask task : activityLifecycleTaskSet) {
            task.onStart(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (ActivityLifecycleTask task : activityLifecycleTaskSet) {
            task.onStop(this);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (ActivityLifecycleTask task : activityLifecycleTaskSet) {
            task.onResume(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (ActivityLifecycleTask task : activityLifecycleTaskSet) {
            task.onPause(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (isFinishing()) {
            Injector.clearComponent(this);
        }
        for (ActivityLifecycleTask task : activityLifecycleTaskSet) {
            task.onDestroy(this);
        }
    }

    public ScreenInjector getScreenInjector() {
        return screenInjector;
    }
}
