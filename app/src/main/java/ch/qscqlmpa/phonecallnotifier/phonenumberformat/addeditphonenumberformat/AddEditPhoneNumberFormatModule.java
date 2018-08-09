package ch.qscqlmpa.phonecallnotifier.phonenumberformat.addeditphonenumberformat;

import javax.inject.Named;

import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatPersistenceManager;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormat;
import ch.qscqlmpa.phonecallnotifier.data.phonenumberformat.PhoneNumberFormatRepository;
import ch.qscqlmpa.phonecallnotifier.di.ScreenScope;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class AddEditPhoneNumberFormatModule {

    @Provides
    @ScreenScope
    static AddEditPhoneNumberFormatPresenter providePresenter(
            AddEditPhoneNumberFormatViewModel viewModel,
            PhoneNumberFormatRepository repository,
            PhoneNumberFormatPersistenceManager phoneNumberFormatPersistenceManager,
            @Named("phone_number_format_to_edit")
                    PhoneNumberFormat phoneNumberFormat) {

        return new AddEditPhoneNumberFormatPresenter(viewModel,
                repository,
                phoneNumberFormatPersistenceManager,
                phoneNumberFormat);
    }
}
