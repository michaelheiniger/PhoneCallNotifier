package ch.qscqlmpa.phonecallnotifier.home;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bluelinelabs.conductor.Controller;

import ch.qscqlmpa.phonecallnotifier.ProcessIncomingCallIS;
import ch.qscqlmpa.phonecallnotifier.R;
import ch.qscqlmpa.phonecallnotifier.base.BaseActivity;
import ch.qscqlmpa.phonecallnotifier.phonenumberformat.PhoneNumberFormatController;
import timber.log.Timber;

public class MainActivity extends BaseActivity {

    private static final int READ_PHONE_STATE_PERMISSION_REQUEST = 1;
    private static final int CALL_PHONE_PERMISSION_REQUEST = 2;

    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected Controller initialScreen() {
        return new PhoneNumberFormatController();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkAndAskPhoneStatePermission();
        checkAndAskPhoneCallPermission();
        handleIntent(getIntent());
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void checkAndAskPhoneStatePermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {

                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "default_channel")
                        .setSmallIcon(R.drawable.ic_add_white_24dp)
                        .setContentTitle("Permission request")
                        .setContentText("This permission is necessary to be able to detect " +
                                "when a call is received and get the caller number")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                if (Build.VERSION.SDK_INT >= 21) mBuilder.setVibrate(new long[0]);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

                int notificationId = 1;
                notificationManager.notify(notificationId, mBuilder.build());
            }

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    READ_PHONE_STATE_PERMISSION_REQUEST);

        } else {
            // Permission has already been granted
        }
    }

    private void checkAndAskPhoneCallPermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {

                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "default_channel")
                        .setSmallIcon(R.drawable.ic_add_white_24dp)
                        .setContentTitle("Permission request")
                        .setContentText("This permission is necessary to be able to reject " +
                                "a phone call when the caller's number is blacklisted")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                if (Build.VERSION.SDK_INT >= 21) mBuilder.setVibrate(new long[0]);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

                int notificationId = 1;
                notificationManager.notify(notificationId, mBuilder.build());
            }

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    CALL_PHONE_PERMISSION_REQUEST);

        } else {
            // Permission has already been granted
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case READ_PHONE_STATE_PERMISSION_REQUEST:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Timber.i("Permission granted by the user");
                } else {
                    Timber.i("Permission denied by the user");
                }
                return;

            case CALL_PHONE_PERMISSION_REQUEST:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Timber.i("Permission granted by the user");
                } else {
                    Timber.i("Permission denied by the user");
                }
                return;
        }
    }

    public void handleIntent(Intent intent) {
        if (intent != null) {
            if (ProcessIncomingCallIS.ACTION_PROCESS_RECEIVED_PHONE_CALL.equals(intent.getAction())) {
                String callerNumber = intent.getStringExtra(ProcessIncomingCallIS.EXTRA_CALLER_NUMBER);
                showDialog(callerNumber);
            }
        }
    }

    private void showDialog(String callerNumber) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Phone call blocked (number: " + callerNumber + ")")
                .setNeutralButton("Ok", (dialogInterface, i) -> {
                    // Nothing to do
                });
        builder.create().show();
    }
}
