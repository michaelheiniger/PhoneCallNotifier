package ch.qscqlmpa.phonecallnotifier.phonenumberformat;

import ch.qscqlmpa.phonecallnotifier.di.ScreenScope;
import ch.qscqlmpa.phonecallnotifier.phonenumberformat.phonenumberformatlist.PhoneNumberFormatListController;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ScreenScope
@Subcomponent
public interface PhoneNumberFormatComponent extends AndroidInjector<PhoneNumberFormatController> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<PhoneNumberFormatController> {

    }
}
