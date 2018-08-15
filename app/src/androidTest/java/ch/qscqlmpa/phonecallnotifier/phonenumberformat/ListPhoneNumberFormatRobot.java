package ch.qscqlmpa.phonecallnotifier.phonenumberformat;

import android.support.test.espresso.matcher.ViewMatchers;

import java.util.List;

import ch.qscqlmpa.phonecallnotifier.R;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatPersist;
import ch.qscqlmpa.phonecallnotifier.model.PhoneNumberFormat;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

public class ListPhoneNumberFormatRobot {

    private final List<PhoneNumberFormat> phoneNumberFormatList;

    static ListPhoneNumberFormatRobot init(List<PhoneNumberFormat> phoneNumberFormatList) {
        return new ListPhoneNumberFormatRobot(phoneNumberFormatList);
    }

    private ListPhoneNumberFormatRobot(List<PhoneNumberFormat> phoneNumberFormatList) {
        this.phoneNumberFormatList = phoneNumberFormatList;
    }

    ListPhoneNumberFormatRobot verifyListElements() {

        for (PhoneNumberFormat format : phoneNumberFormatList) {
            onView(allOf(withId(R.id.pnfr_phone_number_format_description_tv), withText(format.description()))).check(matches(withText(format.description())));
            onView(allOf(withId(R.id.pnfr_phone_number_format_tv), withText(format.format()))).check(matches(withText(format.format())));

            // Cannot verify the checkbox because there is no way to distinguish them
//            if (format.getIsEnabled()) {
//                onView(allOf(withId(R.id.pnfr_is_phone_number_format_enabled_ckb), withText("Enabled"))).check(matches(ViewMatchers.isChecked()));
//            } else {
//                onView(allOf(withId(R.id.pnfr_is_phone_number_format_enabled_ckb), withText("Enabled"))).check(matches(ViewMatchers.isNotChecked()));
//            }
        }
        return this;
    }

    ListPhoneNumberFormatRobot verifyErrorText(Integer textRes) {
        if (textRes == null) {
            onView(withId(R.id.lpnf_error_tv)).check(matches(withText("")));
        } else {
            onView(withId(R.id.lpnf_error_tv)).check(matches(withText(textRes)));
        }
        return this;
    }

    ListPhoneNumberFormatRobot verifyErrorVisibility(ViewMatchers.Visibility visibility) {
        onView(withId(R.id.lpnf_error_tv)).check(matches(withEffectiveVisibility(visibility)));
        return this;
    }
}

