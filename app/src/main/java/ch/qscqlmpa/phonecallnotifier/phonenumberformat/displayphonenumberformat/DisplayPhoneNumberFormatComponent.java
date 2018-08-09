package ch.qscqlmpa.phonecallnotifier.phonenumberformat.displayphonenumberformat;

import javax.inject.Named;

import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormat;
import ch.qscqlmpa.phonecallnotifier.di.ScreenScope;
import dagger.BindsInstance;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ScreenScope
@Subcomponent
public interface DisplayPhoneNumberFormatComponent extends AndroidInjector<DisplayPhoneNumberFormatController> {

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
