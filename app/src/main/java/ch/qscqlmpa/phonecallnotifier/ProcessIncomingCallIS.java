package ch.qscqlmpa.phonecallnotifier;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import ch.qscqlmpa.phonecallnotifier.base.BaseService;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatDao;
import ch.qscqlmpa.phonecallnotifier.data.database.AppRoomDatabase;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormat;
import ch.qscqlmpa.phonecallnotifier.home.MainActivity;
import ch.qscqlmpa.phonecallnotifier.lifecycle.DisposableManager;
import ch.qscqlmpa.phonecallnotifier.phonecall.PhoneNumberProcessor;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
@Singleton
public class ProcessIncomingCallIS extends BaseService {

    public static final String ACTION_PROCESS_RECEIVED_PHONE_CALL = "action_caller_number";

    public static final String EXTRA_CALLER_NUMBER = "extra_caller_number";

    private DisposableManager disposableManager = new DisposableManager();

    @Inject AppRoomDatabase database;

    public ProcessIncomingCallIS() {
        super("");
    }

    public static void startActionProcessReceivedCallEvent(Context context, String callerNumber) {
        Intent intent = new Intent(context, ProcessIncomingCallIS.class);
        intent.setAction(ACTION_PROCESS_RECEIVED_PHONE_CALL);
        intent.putExtra(EXTRA_CALLER_NUMBER, callerNumber);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PROCESS_RECEIVED_PHONE_CALL.equals(action)) {
                final String callerNumber = intent.getStringExtra(EXTRA_CALLER_NUMBER);
                handleActionProcessReceivedCallEvent(callerNumber);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposableManager.dispose();
    }

    private void handleActionProcessReceivedCallEvent(String callerNumber) {
        PhoneNumberFormatDao phoneNumberFormatDao = database.phoneNumberFormatRegularDao();
        disposableManager.add(
                phoneNumberFormatDao.getEnabledPhoneNumberFormats()
                .subscribeOn(Schedulers.io())
                .subscribe(phoneNumberFormats -> {
                    processPhoneNumberFormats(callerNumber, phoneNumberFormats);
                })
        );
    }

    private void processPhoneNumberFormats(String callerNumber, List<PhoneNumberFormat> phoneNumberFormats) {

        if (PhoneNumberProcessor.doesPhoneNumberMatchAtLeastOneFormat(
                callerNumber,
                PhoneNumberFormat.getFormatsAsString(phoneNumberFormats))) {

            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            try {
                Method m = tm.getClass().getDeclaredMethod("getITelephony");

                m.setAccessible(true);
                ITelephony telephonyService = (ITelephony) m.invoke(tm);

                if (callerNumber != null) {
                    telephonyService.endCall();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent notifyActivity = new Intent(this, MainActivity.class);
            notifyActivity.setAction(ACTION_PROCESS_RECEIVED_PHONE_CALL);
            notifyActivity.putExtra(EXTRA_CALLER_NUMBER, callerNumber);
            notifyActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(notifyActivity);
        }
    }
}
