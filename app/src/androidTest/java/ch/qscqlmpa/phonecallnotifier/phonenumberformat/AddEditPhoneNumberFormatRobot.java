package ch.qscqlmpa.phonecallnotifier.phonenumberformat;

import android.support.test.espresso.matcher.ViewMatchers;

import ch.qscqlmpa.phonecallnotifier.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

class AddEditPhoneNumberFormatRobot {

    static AddEditPhoneNumberFormatRobot init() {
        return new AddEditPhoneNumberFormatRobot();
    }

    private AddEditPhoneNumberFormatRobot() {

    }

    AddEditPhoneNumberFormatRobot verifyDescription(String description) {
        onView(withId(R.id.aepnf_phone_number_format_description_edt)).check(matches(withText(description)));
        return this;
    }

    AddEditPhoneNumberFormatRobot verifyFormat(String format) {
        onView(withId(R.id.aepnf_phone_number_format_edt)).check(matches(withText(format)));
        return this;
    }

    AddEditPhoneNumberFormatRobot verifyIsEnabled(boolean isEnabled) {
        if (isEnabled) {
            onView(withId(R.id.aepnf_is_phone_number_format_enabled_ckb)).check(matches(ViewMatchers.isChecked()));
        } else {
            onView(withId(R.id.aepnf_is_phone_number_format_enabled_ckb)).check(matches(ViewMatchers.isNotChecked()));
        }
        return this;
    }

    AddEditPhoneNumberFormatRobot verifyErrorText(Integer textRes) {
        if (textRes == null) {
            onView(withId(R.id.aepnf_error_tv)).check(matches(withText("")));
        } else {
            onView(withId(R.id.aepnf_error_tv)).check(matches(withText(textRes)));
        }
        return this;
    }

    AddEditPhoneNumberFormatRobot verifyErrorVisibility(ViewMatchers.Visibility visibility) {
        onView(withId(R.id.aepnf_error_tv)).check(matches(withEffectiveVisibility(visibility)));
        return this;
    }
}
