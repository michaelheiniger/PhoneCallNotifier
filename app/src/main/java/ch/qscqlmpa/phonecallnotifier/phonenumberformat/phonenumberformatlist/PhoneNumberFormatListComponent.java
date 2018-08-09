package ch.qscqlmpa.phonecallnotifier.phonenumberformat.phonenumberformatlist;

import ch.qscqlmpa.phonecallnotifier.di.ScreenScope;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ScreenScope
@Subcomponent
public interface PhoneNumberFormatListComponent extends AndroidInjector<PhoneNumberFormatListController> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<PhoneNumberFormatListController> {

    }
}
