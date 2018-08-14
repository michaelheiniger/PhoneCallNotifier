package ch.qscqlmpa.phonecallnotifier.di;

import ch.qscqlmpa.phonecallnotifier.lifecycle.DisposableManager;
import dagger.android.AndroidInjector;

public interface ScreenComponent<T> extends AndroidInjector<T> {


    @ForScreen
    DisposableManager disposableManager();
}
