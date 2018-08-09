package ch.qscqlmpa.phonecallnotifier.phonenumberformat;

import android.support.test.espresso.matcher.ViewMatchers;

import ch.qscqlmpa.phonecallnotifier.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

class DisplayPhoneNumberFormatRobot {

    static DisplayPhoneNumberFormatRobot init() {
        return new DisplayPhoneNumberFormatRobot();
    }

    private DisplayPhoneNumberFormatRobot() {

    }

    DisplayPhoneNumberFormatRobot verifyDescription(String description) {
        onView(withId(R.id.dpnf_phone_number_format_description_tv)).check(matches(withText(description)));
        return this;
    }

    DisplayPhoneNumberFormatRobot verifyFormat(String format) {
        onView(withId(R.id.dpnf_phone_number_format_tv)).check(matches(withText(format)));
        return this;
    }

    DisplayPhoneNumberFormatRobot verifyIsEnabled(boolean isEnabled) {
        if (isEnabled) {
            onView(withId(R.id.dpnf_is_phone_number_format_enabled_ckb)).check(matches(ViewMatchers.isChecked()));
        } else {
            onView(withId(R.id.dpnf_is_phone_number_format_enabled_ckb)).check(matches(ViewMatchers.isNotChecked()));
        }
        return this;
    }

    DisplayPhoneNumberFormatRobot verifyErrorText(Integer textRes) {
        if (textRes == null) {
            onView(withId(R.id.dpnf_error_tv)).check(matches(withText("")));
        } else {
            onView(withId(R.id.dpnf_error_tv)).check(matches(withText(textRes)));
        }
        return this;
    }

    DisplayPhoneNumberFormatRobot verifyErrorVisibility(ViewMatchers.Visibility visibility) {
        onView(withId(R.id.dpnf_error_tv)).check(matches(withEffectiveVisibility(visibility)));
        return this;
    }
}