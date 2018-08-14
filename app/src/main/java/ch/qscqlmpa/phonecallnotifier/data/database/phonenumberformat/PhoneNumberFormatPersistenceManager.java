package ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import ch.qscqlmpa.phonecallnotifier.data.database.AppRoomDatabase;
import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
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

    public void insertPhoneNumberFormat(PhoneNumberFormat phoneNumberFormat) {
        runDbOperation(() -> {
            database.phoneNumberFormatRegularDao().insertPhoneNumberFormat(phoneNumberFormat);
        });
    }

    public void updatePhoneNumberFormat(PhoneNumberFormat phoneNumberFormat) {
        runDbOperation(() -> {
            database.phoneNumberFormatRegularDao().updatePhoneNumberFormat(phoneNumberFormat);
        });
    }

    public void deletePhoneNumberFormat(PhoneNumberFormat phoneNumberFormat) {
        runDbOperation(() -> {
            database.phoneNumberFormatRegularDao().deletePhoneNumberFormat(phoneNumberFormat);
        });
    }

    private void runDbOperation(Action action) {
        //TODO: Should it be disposed ? Since the instance of PhoneNumberFormatPersistenceManager is a Singleton, is that still necessary ?
        Completable.fromAction(action)
                .subscribeOn(scheduler)
                .subscribe(() -> {}, throwable -> Timber.e(throwable, "Error performing database operation"));
    }
}
