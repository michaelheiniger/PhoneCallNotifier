package ch.qscqlmpa.phonecallnotifier.phonenumberformat.phonenumberformatlist;

import ch.qscqlmpa.phonecallnotifier.di.ScreenScope;
import ch.qscqlmpa.phonecallnotifier.phonenumberformat.EmptyLayoutController;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ScreenScope
@Subcomponent
public interface EmptyLayoutComponent extends AndroidInjector<EmptyLayoutController> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<EmptyLayoutController> {

    }
}
