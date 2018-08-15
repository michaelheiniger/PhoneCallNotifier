package ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import ch.qscqlmpa.phonecallnotifier.data.database.AppRoomDatabase;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import timber.log.Timber;

@Singleton
public class PhoneNumberFormatPersistenceManager {

    private final AppRoomDatabase database;
    private final Scheduler scheduler;

    @Inject
    PhoneNumberFormatPersistenceManager(AppRoomDatabase database,
                                        @Named("database_scheduler")
                                        Scheduler scheduler) {
        this.database = database;
        this.scheduler = scheduler;
    }

    public void insertPhoneNumberFormat(PhoneNumberFormatPersist phoneNumberFormatPersist) {
        runDbOperation(() -> {
            database.phoneNumberFormatRegularDao().insertPhoneNumberFormat(phoneNumberFormatPersist);
        });
    }

    public void updatePhoneNumberFormat(PhoneNumberFormatPersist phoneNumberFormatPersist) {
        runDbOperation(() -> {
            database.phoneNumberFormatRegularDao().updatePhoneNumberFormat(phoneNumberFormatPersist);
        });
    }

    public void deletePhoneNumberFormat(Long id) {
        runDbOperation(() -> {
            database.phoneNumberFormatRegularDao().deletePhoneNumberFormat(id);
        });
    }

    private void runDbOperation(Action action) {
        //TODO: Should it be disposed ? Since the instance of PhoneNumberFormatPersistenceManager is a Singleton, is that still necessary ?
        Completable.fromAction(action)
                .subscribeOn(scheduler)
                .subscribe(() -> {}, throwable -> Timber.e(throwable, "Error performing database operation"));
    }
}
