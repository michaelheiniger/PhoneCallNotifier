package ch.qscqlmpa.phonecallnotifier.phonenumberformat.displayphonenumberformat;

import javax.inject.Inject;
import javax.inject.Named;

import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatPersist;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatPersistenceManager;
import ch.qscqlmpa.phonecallnotifier.data.phonenumberformat.PhoneNumberFormatRepository;
import ch.qscqlmpa.phonecallnotifier.di.ForScreen;
import ch.qscqlmpa.phonecallnotifier.di.ScreenScope;
import ch.qscqlmpa.phonecallnotifier.lifecycle.DisposableManager;
import ch.qscqlmpa.phonecallnotifier.model.PhoneNumberFormat;
import ch.qscqlmpa.phonecallnotifier.phonenumberformat.PhoneNumberFormatViewModel;

@ScreenScope
class DisplayPhoneNumberFormatPresenter {

    private final PhoneNumberFormatViewModel viewModel;
    private final PhoneNumberFormatRepository repository;
    private final PhoneNumberFormatPersistenceManager phoneNumberFormatPersistenceManager;
    private final DisposableManager disposableManager;

    @Inject
    DisplayPhoneNumberFormatPresenter(PhoneNumberFormatViewModel viewModel,
                                      PhoneNumberFormatRepository repository,
                                      PhoneNumberFormatPersistenceManager phoneNumberFormatPersistenceManager,
                                      @ForScreen DisposableManager disposableManager,
                                      @Named("phone_number_format_to_display")
                                              PhoneNumberFormat phoneNumberFormat) {

        this.viewModel = viewModel;
        this.repository = repository;
        this.phoneNumberFormatPersistenceManager = phoneNumberFormatPersistenceManager;
        this.disposableManager = disposableManager;
        loadPhoneNumberFormat(phoneNumberFormat);
    }

    private void loadPhoneNumberFormat(PhoneNumberFormat phoneNumberFormat) {
        disposableManager.add(
                repository.getPhoneNumberFormat(phoneNumberFormat)
                .subscribe(viewModel.phoneNumberFormatUpdated(), viewModel.onError())
        );
    }

    PhoneNumberFormat getPhoneNumberFormat() {
        return viewModel.lastPhoneNumberFormat();
    }

    void deletePhoneNumberFormat() {
        PhoneNumberFormat formatToDelete = viewModel.lastPhoneNumberFormat();
        phoneNumberFormatPersistenceManager.deletePhoneNumberFormat(formatToDelete.id());
    }
}
