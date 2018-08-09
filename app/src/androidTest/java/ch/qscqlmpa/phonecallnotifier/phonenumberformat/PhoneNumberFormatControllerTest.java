package ch.qscqlmpa.phonecallnotifier.phonenumberformat;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.squareup.moshi.Types;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import ch.qscqlmpa.phonecallnotifier.R;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormat;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatDao;
import ch.qscqlmpa.phonecallnotifier.test.ControllerTest;
import ch.qscqlmpa.phonecallnotifier.test.TestUtils;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

@RunWith(AndroidJUnit4.class)
public class PhoneNumberFormatControllerTest extends ControllerTest {

    @Before
    public void setUp() {
        testRule.clearState();
        initDb();
    }

    @Test
    public void testDisplayData() {
        launch();

        int itemPosition = 0;
        showDisplayController(itemPosition);

        List<PhoneNumberFormat> phoneNumberFormatList = getTestData();
        PhoneNumberFormat format = phoneNumberFormatList.get(itemPosition);

        checkDisplayData(format);
    }

    @Test
    public void testEditSaveData() {
        launch();

        int itemPosition = 0;
        showAddEditController(itemPosition);

        List<PhoneNumberFormat> phoneNumberFormatList = getTestData();
        PhoneNumberFormat format = phoneNumberFormatList.get(itemPosition);

        // Check that data has been loaded correctly
        checkAddEditData(format);

        PhoneNumberFormat formatEdited = new PhoneNumberFormat(format);
        formatEdited.setDescription("The new description");
        formatEdited.setFormat("The new format");
        formatEdited.setIsEnabled(!formatEdited.getIsEnabled());

        applyEditedData(formatEdited);

        // Check that data has been reloaded correctly after edition
        checkAddEditData(formatEdited);

        // Save edited data
        onView(withId(R.id.aepnf_save_phone_number_format_btn)).perform(click());

        phoneNumberFormatList.set(itemPosition, formatEdited);

        // Check that the list reloaded correctly after edition
        checkListData(phoneNumberFormatList);
    }

    @Test
    public void testEditCancelData() {
        launch();

        int itemPosition = 0;
        showAddEditController(itemPosition);

        List<PhoneNumberFormat> phoneNumberFormatList = getTestData();
        PhoneNumberFormat format = phoneNumberFormatList.get(itemPosition);

        // Check that data has been loaded correctly
        checkAddEditData(format);

        PhoneNumberFormat formatEdited = new PhoneNumberFormat(format);
        formatEdited.setDescription("The new description");
        formatEdited.setFormat("The new format");
        formatEdited.setIsEnabled(!formatEdited.getIsEnabled());

        applyEditedData(formatEdited);

        // Cancel edited data
        onView(withId(R.id.aepnf_cancel_phone_number_format_btn)).perform(click());

        // Check that data has been reloaded correctly after cancel
        checkDisplayData(format);

        // Check that the list reloaded correctly after cancel
        checkListData(phoneNumberFormatList);
    }

    @Test
    public void testDeleteData() {
        launch();

        int itemPosition = 0;
        showDisplayController(itemPosition);

        List<PhoneNumberFormat> phoneNumberFormatList = getTestData();
        PhoneNumberFormat format = phoneNumberFormatList.get(itemPosition);

        checkDisplayData(format);

        // Delete the data
        onView(withId(R.id.dpnf_delete_phone_number_format_btn)).perform(click());
        phoneNumberFormatList.remove(itemPosition);

        // Check that the corresponding entry is not displayed in the list anymore
        checkListData(phoneNumberFormatList);
    }

    @Test
    public void testListData() {
        launch();

        List<PhoneNumberFormat> phoneNumberFormatList = getTestData();
        ListPhoneNumberFormatRobot.init(phoneNumberFormatList)
                .verifyListElements()
                .verifyErrorVisibility(ViewMatchers.Visibility.GONE);
    }

    @Test
    public void testAddFormatSave() {
        launch();
        showAddFormat();

        checkAddEditData(new PhoneNumberFormat());

        PhoneNumberFormat newPhoneNumberFormat = new PhoneNumberFormat("The description", "The format", false);
        applyEditedData(newPhoneNumberFormat);

        // Save the new data
        onView(withId(R.id.aepnf_save_phone_number_format_btn)).perform(click());

        List<PhoneNumberFormat> phoneNumberFormatList = getTestData();
        phoneNumberFormatList.add(newPhoneNumberFormat);

        // Check the list for the new entry
        checkListData(phoneNumberFormatList);
    }

    @Test
    public void testAddFormatCancel() {
        launch();
        showAddFormat();

        checkAddEditData(new PhoneNumberFormat());

        PhoneNumberFormat newPhoneNumberFormat = new PhoneNumberFormat("The description", "The format", false);
        applyEditedData(newPhoneNumberFormat);

        // Save the new data
        onView(withId(R.id.aepnf_cancel_phone_number_format_btn)).perform(click());

        List<PhoneNumberFormat> phoneNumberFormatList = getTestData();

        // Check the list for the new entry
        checkListData(phoneNumberFormatList);
    }

    private void checkDisplayData(PhoneNumberFormat format) {
        DisplayPhoneNumberFormatRobot.init()
                .verifyDescription(format.getDescription())
                .verifyFormat(format.getFormat())
                .verifyIsEnabled(format.getIsEnabled())
                .verifyErrorVisibility(ViewMatchers.Visibility.GONE);
    }

    private void checkAddEditData(PhoneNumberFormat format) {
        AddEditPhoneNumberFormatRobot.init()
                .verifyDescription(format.getDescription())
                .verifyFormat(format.getFormat())
                .verifyIsEnabled(format.getIsEnabled())
                .verifyErrorVisibility(ViewMatchers.Visibility.GONE);
    }

    private void checkListData(List<PhoneNumberFormat> phoneNumberFormatList) {
        ListPhoneNumberFormatRobot.init(phoneNumberFormatList)
                .verifyListElements()
                .verifyErrorVisibility(ViewMatchers.Visibility.GONE);
    }

    private void showDisplayController(int itemPosition) {
        onView(withId(R.id.lpnf_phone_number_formats_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(itemPosition, click()));
    }

    private void showAddEditController(int itemPosition) {
        showDisplayController(itemPosition);

        onView(withId(R.id.dpnf_edit_phone_number_format_btn)).perform(click());
    }

    private void showAddFormat() {
        onView(withId(R.id.lpnf_add_new_phone_number_formats_btn)).perform(click());
    }

    private void applyEditedData(PhoneNumberFormat formatEdited) {
        onView(withId(R.id.aepnf_phone_number_format_description_edt)).perform(ViewActions.clearText());
        onView(withId(R.id.aepnf_phone_number_format_description_edt)).perform(replaceText(formatEdited.getDescription()));

        onView(withId(R.id.aepnf_phone_number_format_edt)).perform(ViewActions.clearText());
        onView(withId(R.id.aepnf_phone_number_format_edt)).perform(replaceText(formatEdited.getFormat()));

        onView(withId(R.id.aepnf_is_phone_number_format_enabled_ckb)).perform(click());
    }

    private void initDb() {
        PhoneNumberFormatDao dao = database.phoneNumberFormatRegularDao();

        List<PhoneNumberFormat> phoneNumberFormatList = getTestData();
        for (PhoneNumberFormat format : phoneNumberFormatList) {
            dao.insertPhoneNumberFormat(format);
        }
    }

    private List<PhoneNumberFormat> getTestData() {
        return TestUtils.loadJson(
                "mock/database_content.json",
                Types.newParameterizedType(List.class, PhoneNumberFormat.class));
    }
}