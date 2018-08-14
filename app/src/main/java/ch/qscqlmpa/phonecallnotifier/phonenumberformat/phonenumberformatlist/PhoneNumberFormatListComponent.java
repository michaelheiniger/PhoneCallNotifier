package ch.qscqlmpa.phonecallnotifier.phonenumberformat.phonenumberformatlist;

import ch.qscqlmpa.phonecallnotifier.base.ScreenModule;
import ch.qscqlmpa.phonecallnotifier.di.ScreenComponent;
import ch.qscqlmpa.phonecallnotifier.di.ScreenScope;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ScreenScope
@Subcomponent(modules = {
        ScreenModule.class
})
public interface PhoneNumberFormatListComponent extends ScreenComponent<PhoneNumberFormatListController> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<PhoneNumberFormatListController> {

    }
}
