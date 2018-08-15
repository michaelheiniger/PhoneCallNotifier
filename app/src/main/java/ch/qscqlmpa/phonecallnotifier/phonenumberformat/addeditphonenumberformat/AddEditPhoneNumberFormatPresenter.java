package ch.qscqlmpa.phonecallnotifier.phonenumberformat.addeditphonenumberformat;

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
class AddEditPhoneNumberFormatPresenter {

    private final PhoneNumberFormatViewModel viewModel;
    private final PhoneNumberFormatRepository repository;
    private final PhoneNumberFormatPersistenceManager phoneNumberFormatPersistenceManager;
    private final DisposableManager disposableManager;

    private final boolean isAddOperation;

    @Inject
    AddEditPhoneNumberFormatPresenter(PhoneNumberFormatViewModel viewModel,
                                      PhoneNumberFormatRepository repository,
                                      PhoneNumberFormatPersistenceManager phoneNumberFormatPersistenceManager,
                                      @ForScreen DisposableManager disposableManager,
                                      @Named("phone_number_format_to_edit")
                                              PhoneNumberFormat phoneNumberFormat) {

        this.viewModel = viewModel;
        this.repository = repository;
        this.phoneNumberFormatPersistenceManager = phoneNumberFormatPersistenceManager;
        this.disposableManager = disposableManager;

        if (phoneNumberFormat.id() != null) {
            loadPhoneNumberFormatToEdit(phoneNumberFormat);
            isAddOperation = false;
        } else {
            loadNewPhoneNumberFormat(phoneNumberFormat);
            isAddOperation = true;
        }
    }

    private void loadPhoneNumberFormatToEdit(PhoneNumberFormat phoneNumberFormat) {
        disposableManager.add(
                repository.getPhoneNumberFormat(phoneNumberFormat)
                .subscribe(viewModel.phoneNumberFormatUpdated(), viewModel.onError())
        );
    }

    private void loadNewPhoneNumberFormat(PhoneNumberFormat phoneNumberFormat){
        try {
            viewModel.phoneNumberFormatUpdated().accept(phoneNumberFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSaveClick(PhoneNumberFormat phoneNumberFormat) {
        PhoneNumberFormatPersist pnfPersist = PhoneNumberFormatPersist.convertPnfToPnfPersist(phoneNumberFormat);
        if (isAddOperation) {
            phoneNumberFormatPersistenceManager.insertPhoneNumberFormat(pnfPersist);
        } else {
            PhoneNumberFormat formatToEdit = viewModel.lastPhoneNumberFormat();
            pnfPersist.setId(formatToEdit.id());
            phoneNumberFormatPersistenceManager.updatePhoneNumberFormat(pnfPersist);
        }
    }
}
