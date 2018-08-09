package ch.qscqlmpa.phonecallnotifier.phonenumberformat.addeditphonenumberformat;

import javax.inject.Named;

import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormat;
import ch.qscqlmpa.phonecallnotifier.di.ScreenScope;
import dagger.BindsInstance;
import dagger.Subcomponent;
import dagger.android.AndroidInjector;

@ScreenScope
@Subcomponent(modules = {
        AddEditPhoneNumberFormatModule.class
})
public interface AddEditPhoneNumberFormatComponent extends AndroidInjector<AddEditPhoneNumberFormatController> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<AddEditPhoneNumberFormatController> {

        @BindsInstance
        public abstract void bindPhoneNumberFormat(@Named("phone_number_format_to_edit") PhoneNumberFormat phoneNumberFormat);

        @Override
        public void seedInstance(AddEditPhoneNumberFormatController instance) {

            PhoneNumberFormat formatToEdit = (PhoneNumberFormat) instance
                    .getArgs()
                    .get(AddEditPhoneNumberFormatController.PHONE_NUMBER_FORMAT_TO_EDIT_KEY);

            bindPhoneNumberFormat(formatToEdit);
        }
    }

}
