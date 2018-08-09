package ch.qscqlmpa.phonecallnotifier.phonenumberformat.phonenumberformatlist;

import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.List;

import javax.inject.Inject;

import ch.qscqlmpa.phonecallnotifier.R;
import ch.qscqlmpa.phonecallnotifier.di.ScreenScope;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormat;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

@ScreenScope
class PhoneNumberFormatListViewModel {

    private final BehaviorRelay<List<PhoneNumberFormat>> phoneNumberFormatsRelay = BehaviorRelay.create();

    private final BehaviorRelay<Integer> errorRelay = BehaviorRelay.create();

    @Inject
    PhoneNumberFormatListViewModel() {}

    Observable<List<PhoneNumberFormat>> phoneNumberFormats() {
        return phoneNumberFormatsRelay;
    }

    Observable<Integer> error() {
        return errorRelay;
    }

    Consumer<List<PhoneNumberFormat>> phoneNumberFormatsUpdated() {
        errorRelay.accept(-1);
        return phoneNumberFormatsRelay;
    }

    Consumer<Throwable> onError() {
        return throwable -> {
            Timber.e("Error loading PhoneNumberFormats", throwable);
            errorRelay.accept(R.string.db_error_phonenumberformats);
        };
    }
}
