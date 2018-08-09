package ch.qscqlmpa.phonecallnotifier.phonecall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ch.qscqlmpa.phonecallnotifier.ProcessIncomingCallIS;
import timber.log.Timber;

import static android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED;
import static android.telephony.TelephonyManager.EXTRA_INCOMING_NUMBER;
import static android.telephony.TelephonyManager.EXTRA_STATE;
import static android.telephony.TelephonyManager.EXTRA_STATE_RINGING;


public class PhoneCallEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(ACTION_PHONE_STATE_CHANGED)) {

            String state = intent.getStringExtra(EXTRA_STATE);

            if (EXTRA_STATE_RINGING.equals(state)) {
                String callerNumber = intent.getStringExtra(EXTRA_INCOMING_NUMBER);

                Timber.i("Phone is ringing, caller number is " +  callerNumber);

                ProcessIncomingCallIS.startActionProcessReceivedCallEvent(context, callerNumber);
            }
        }
    }
}
