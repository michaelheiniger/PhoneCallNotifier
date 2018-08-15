package ch.qscqlmpa.phonecallnotifier.phonenumberformat.phonenumberformatlist;

import javax.inject.Inject;

import ch.qscqlmpa.phonecallnotifier.data.phonenumberformat.PhoneNumberFormatRepository;
import ch.qscqlmpa.phonecallnotifier.di.ForScreen;
import ch.qscqlmpa.phonecallnotifier.di.ScreenScope;
import ch.qscqlmpa.phonecallnotifier.lifecycle.DisposableManager;
import ch.qscqlmpa.phonecallnotifier.model.PhoneNumberFormat;

@ScreenScope
public class PhoneNumberFormatListPresenter {

    private final PhoneNumberFormatListViewModel viewModel;
    private final PhoneNumberFormatRepository repository;
    private final DisposableManager disposableManager;

    @Inject
    PhoneNumberFormatListPresenter(PhoneNumberFormatListViewModel viewModel,
                                   PhoneNumberFormatRepository repository,
                                   @ForScreen DisposableManager disposableManager) {

        this.viewModel = viewModel;
        this.repository = repository;
        this.disposableManager = disposableManager;
        loadPhoneNumberFormats();
    }

    private void loadPhoneNumberFormats() {
        disposableManager.add(
                repository.getAllPhoneNumberFormats()
                .subscribe(viewModel.phoneNumberFormatsUpdated(), viewModel.onError())
        );
    }
}
