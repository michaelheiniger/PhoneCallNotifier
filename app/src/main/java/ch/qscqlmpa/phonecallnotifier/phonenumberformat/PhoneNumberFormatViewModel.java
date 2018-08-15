package ch.qscqlmpa.phonecallnotifier.phonenumberformat;

import com.jakewharton.rxrelay2.BehaviorRelay;

import javax.inject.Inject;

import ch.qscqlmpa.phonecallnotifier.R;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatPersist;
import ch.qscqlmpa.phonecallnotifier.di.ScreenScope;
import ch.qscqlmpa.phonecallnotifier.model.PhoneNumberFormat;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

@ScreenScope
public class PhoneNumberFormatViewModel {

    private final BehaviorRelay<PhoneNumberFormat> phoneNumberFormatRelay = BehaviorRelay.create();

    private final BehaviorRelay<Integer> errorRelay = BehaviorRelay.create();

    @Inject
    public PhoneNumberFormatViewModel() {}

    public Observable<PhoneNumberFormat> phoneNumberFormat() {
        return phoneNumberFormatRelay;
    }

    public Observable<Integer> error() {
        return errorRelay;
    }

    public Consumer<PhoneNumberFormat> phoneNumberFormatUpdated() {
        errorRelay.accept(-1);
        return phoneNumberFormatRelay;
    }

    public PhoneNumberFormat lastPhoneNumberFormat() {
        return phoneNumberFormatRelay.getValue();
    }

    public Consumer<Throwable> onError() {
        return throwable -> {
            Timber.e("Error loading Repos", throwable);
            errorRelay.accept(R.string.db_error_phonenumberformat);
        };
    }

}
