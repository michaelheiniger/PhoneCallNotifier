package ch.qscqlmpa.phonecallnotifier.phonenumberformat.displayphonenumberformat;

import javax.inject.Named;

import ch.qscqlmpa.phonecallnotifier.base.ScreenModule;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatPersist;
import ch.qscqlmpa.phonecallnotifier.di.ScreenComponent;
import ch.qscqlmpa.phonecallnotifier.di.ScreenScope;
import ch.qscqlmpa.phonecallnotifier.model.PhoneNumberFormat;
import dagger.BindsInstance;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ScreenScope
@Subcomponent(modules = {
        ScreenModule.class
})
public interface DisplayPhoneNumberFormatComponent extends ScreenComponent<DisplayPhoneNumberFormatController> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<DisplayPhoneNumberFormatController> {

        @BindsInstance
        public abstract void bindPhoneNumberFormat(@Named("phone_number_format_to_display") PhoneNumberFormat phoneNumberFormat);

        @Override
        public void seedInstance(DisplayPhoneNumberFormatController instance) {
            bindPhoneNumberFormat((PhoneNumberFormat) instance
                    .getArgs()
                    .get(DisplayPhoneNumberFormatController.PHONE_NUMBER_FORMAT_KEY));
        }
    }
}
