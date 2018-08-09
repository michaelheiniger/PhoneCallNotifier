package ch.qscqlmpa.phonecallnotifier.phonenumberformat.displayphonenumberformat;

import com.jakewharton.rxrelay2.BehaviorRelay;

import javax.inject.Inject;

import ch.qscqlmpa.phonecallnotifier.R;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormat;
import ch.qscqlmpa.phonecallnotifier.di.ScreenScope;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

@ScreenScope
class DisplayPhoneNumberFormatViewModel {

    private final BehaviorRelay<PhoneNumberFormat> phoneNumberFormatRelay = BehaviorRelay.create();

    private final BehaviorRelay<Integer> errorRelay = BehaviorRelay.create();

    @Inject
    DisplayPhoneNumberFormatViewModel() {}

    Observable<PhoneNumberFormat> phoneNumberFormat() {
        return phoneNumberFormatRelay;
    }

    Observable<Integer> error() {
        return errorRelay;
    }

    Consumer<PhoneNumberFormat> phoneNumberFormatUpdated() {
        errorRelay.accept(-1);
        return phoneNumberFormatRelay;
    }

    PhoneNumberFormat lastPhoneNumberFormat() {
        return phoneNumberFormatRelay.getValue();
    }

    Consumer<Throwable> onError() {
        return throwable -> {
            Timber.e("Error loading Repos", throwable);
            errorRelay.accept(R.string.db_error_phonenumberformat);
        };
    }
}
