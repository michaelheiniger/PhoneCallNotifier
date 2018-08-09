package ch.qscqlmpa.phonecallnotifier.phonenumberformat.phonenumberformatlist;

import javax.inject.Inject;

import ch.qscqlmpa.phonecallnotifier.data.phonenumberformat.PhoneNumberFormatRepository;
import ch.qscqlmpa.phonecallnotifier.di.ScreenScope;

@ScreenScope
public class PhoneNumberFormatListPresenter {

    private final PhoneNumberFormatListViewModel viewModel;
    private final PhoneNumberFormatRepository repository;

    @Inject
    PhoneNumberFormatListPresenter(PhoneNumberFormatListViewModel viewModel,
                                   PhoneNumberFormatRepository repository) {

        this.viewModel = viewModel;
        this.repository = repository;
        loadPhoneNumberFormats();
    }

    private void loadPhoneNumberFormats() {
        repository.getAllPhoneNumberFormats()
                .subscribe(viewModel.phoneNumberFormatsUpdated(), viewModel.onError());
    }
}
