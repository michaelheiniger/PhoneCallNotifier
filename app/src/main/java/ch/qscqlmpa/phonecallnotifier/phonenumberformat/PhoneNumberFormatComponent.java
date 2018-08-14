package ch.qscqlmpa.phonecallnotifier.phonenumberformat;

import ch.qscqlmpa.phonecallnotifier.base.ScreenModule;
import ch.qscqlmpa.phonecallnotifier.di.ScreenComponent;
import ch.qscqlmpa.phonecallnotifier.di.ScreenScope;
import ch.qscqlmpa.phonecallnotifier.phonenumberformat.phonenumberformatlist.PhoneNumberFormatListController;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ScreenScope
@Subcomponent(modules = {
        ScreenModule.class
})
public interface PhoneNumberFormatComponent extends ScreenComponent<PhoneNumberFormatController> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<PhoneNumberFormatController> {

    }
}
