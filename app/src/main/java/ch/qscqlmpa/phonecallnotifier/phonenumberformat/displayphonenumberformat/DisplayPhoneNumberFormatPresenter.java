package ch.qscqlmpa.phonecallnotifier.phonenumberformat.displayphonenumberformat;

import javax.inject.Inject;
import javax.inject.Named;

import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormat;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatPersistenceManager;
import ch.qscqlmpa.phonecallnotifier.data.phonenumberformat.PhoneNumberFormatRepository;
import ch.qscqlmpa.phonecallnotifier.di.ScreenScope;

@ScreenScope
class DisplayPhoneNumberFormatPresenter {

    private final DisplayPhoneNumberFormatViewModel viewModel;
    private final PhoneNumberFormatRepository repository;
    private final PhoneNumberFormatPersistenceManager phoneNumberFormatPersistenceManager;

    @Inject
    DisplayPhoneNumberFormatPresenter(DisplayPhoneNumberFormatViewModel viewModel,
                                      PhoneNumberFormatRepository repository,
                                      PhoneNumberFormatPersistenceManager phoneNumberFormatPersistenceManager,
                                      @Named("phone_number_format_to_display")
                                               PhoneNumberFormat phoneNumberFormat) {

        this.viewModel = viewModel;
        this.repository = repository;
        this.phoneNumberFormatPersistenceManager = phoneNumberFormatPersistenceManager;
        loadPhoneNumberFormat(phoneNumberFormat);
    }

    private void loadPhoneNumberFormat(PhoneNumberFormat phoneNumberFormat) {
        repository.getPhoneNumberFormat(phoneNumberFormat)
                .subscribe(viewModel.phoneNumberFormatUpdated(), viewModel.onError());
    }

    PhoneNumberFormat getPhoneNumberFormat() {
        return viewModel.lastPhoneNumberFormat();
    }

    void deletePhoneNumberFormat() {
        PhoneNumberFormat formatToDelete = viewModel.lastPhoneNumberFormat();
        phoneNumberFormatPersistenceManager.deletePhoneNumberFormat(formatToDelete);
    }
}
