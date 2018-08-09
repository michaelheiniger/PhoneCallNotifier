package ch.qscqlmpa.phonecallnotifier.phonenumberformat;

import com.jakewharton.rxrelay2.BehaviorRelay;

import javax.inject.Inject;

import ch.qscqlmpa.phonecallnotifier.R;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormat;
import ch.qscqlmpa.phonecallnotifier.di.ScreenScope;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

@ScreenScope
public class PhoneNumberFormatViewModel {

    private final BehaviorRelay<PhoneNumberFormat> phoneNumberFormatRelay = BehaviorRelay.create();
//    private final BehaviorRelay<Integer> selectedPhoneNumberFormatIdRelay = BehaviorRelay.create();

    private final BehaviorRelay<Integer> errorRelay = BehaviorRelay.create();
    private final BehaviorRelay<Boolean> loadingRelay = BehaviorRelay.create();

    @Inject
    PhoneNumberFormatViewModel() {

    }

    Observable<PhoneNumberFormat> phoneNumberFormat() {
        return phoneNumberFormatRelay;
    }

//    Observable<Integer> selectedPhoneNumberFormatId() {
//        return selectedPhoneNumberFormatIdRelay;
//    }

    Observable<Integer> error() {
        return errorRelay;
    }

    Observable<Boolean> loading() {
        return loadingRelay;
    }

    Consumer<PhoneNumberFormat> phoneNumberFormatUpdated() {
        errorRelay.accept(-1);
        return phoneNumberFormatRelay;
    }

    PhoneNumberFormat lastPhoneNumberFormat() {
        return phoneNumberFormatRelay.getValue();
    }

//    Consumer<Integer> selectedPhoneNumberFormatIdUpdated() {
//        return selectedPhoneNumberFormatIdRelay;
//    }

    Consumer<Throwable> onError() {
        return throwable -> {
            Timber.e("Error loading Repos", throwable);
            errorRelay.accept(R.string.db_error_phonenumberformats);
        };
    }

    Consumer<Boolean> loadingUpdated() {
        return loadingRelay;
    }

}
