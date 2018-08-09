package ch.qscqlmpa.phonecallnotifier.phonecall;

import android.util.Log;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberProcessor {

    private static final String TAG = "PhoneNumberProcessor";

    public static boolean doesPhoneNumberMatchFormat(String phoneNumber, String format) {

        String phoneNumberStd = standardizePhoneNumber(phoneNumber);

        Pattern pattern = Pattern.compile(translateFormatIntoRegex(format));
        Matcher matcher = pattern.matcher(phoneNumberStd);

        Log.d(TAG, "Phone number: " + phoneNumberStd + ", format: " + format);

        return matcher.matches();
    }

    public static boolean doesPhoneNumberMatchAtLeastOneFormat(String phoneNumber, List<String> formats) {
        boolean result = false;

        for (String format : formats) {
            result = result | doesPhoneNumberMatchFormat(phoneNumber, format);
            if (result) return result;
        }
        return result;
    }

    private static String translateFormatIntoRegex(String format) {

        String regex = format;
        regex = regex.replaceAll("#", "[0-9]");
        regex = regex.replaceAll("\\s", "");
        regex = regex.replace("+", "00");

        return regex;
    }

    private static String standardizePhoneNumber(String phoneNumber) {
        return phoneNumber.replace("+", "00");
    }
}
