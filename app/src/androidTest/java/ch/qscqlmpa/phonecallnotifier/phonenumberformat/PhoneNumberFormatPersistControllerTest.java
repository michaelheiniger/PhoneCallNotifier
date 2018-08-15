package ch.qscqlmpa.phonecallnotifier.phonenumberformat;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import ch.qscqlmpa.phonecallnotifier.R;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatPersist;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatDao;
import ch.qscqlmpa.phonecallnotifier.model.PhoneNumberFormat;
import ch.qscqlmpa.phonecallnotifier.test.ControllerTest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

@RunWith(AndroidJUnit4.class)
public class PhoneNumberFormatPersistControllerTest extends ControllerTest {

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

        List<PhoneNumberFormat> phoneNumberFormatList = PhoneNumberFormat.convertPnfPersistListIntoPnfList(getTestData());
        PhoneNumberFormat format = phoneNumberFormatList.get(itemPosition);

        checkDisplayData(format);
    }

    @Test
    public void testEditSaveData() {
        launch();

        int itemPosition = 0;
        showAddEditController(itemPosition);

        List<PhoneNumberFormat> phoneNumberFormatList = PhoneNumberFormat.convertPnfPersistListIntoPnfList(getTestData());
        PhoneNumberFormat format = phoneNumberFormatList.get(itemPosition);

        // Check that data has been loaded correctly
        checkAddEditData(format);

        PhoneNumberFormat formatEdited = PhoneNumberFormat.builder()
                .setDescription("The new description")
                .setFormat("The new format")
                .setIsEnabled(!format.isEnabled())
                .build();

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

        List<PhoneNumberFormat> phoneNumberFormatList = PhoneNumberFormat.convertPnfPersistListIntoPnfList(getTestData());
        PhoneNumberFormat format = phoneNumberFormatList.get(itemPosition);

        // Check that data has been loaded correctly
        checkAddEditData(format);

        PhoneNumberFormat formatEdited = PhoneNumberFormat.builder()
                .setDescription("The new description")
                .setFormat("The new format")
                .setIsEnabled(!format.isEnabled())
                .build();

        applyEditedData(formatEdited);

        // Cancel edited data
        onView(withId(R.id.aepnf_cancel_phone_number_format_btn)).perform(click());

        // Check that data has been reloaded correctly after cancel
        checkDisplayData(format);

        // Check that the list reloaded correctly after cancel
        checkListData(phoneNumberFormatList);
    }

    @Test
    public void testAddFormatSave() {
        launch();
        showAddFormat();

        checkAddEditData(getEmptyPhoneNumberFormat());

        PhoneNumberFormat newPhoneNumberFormat = getFilledPhoneNumberFormat();

        applyEditedData(newPhoneNumberFormat);

        // Save the new data
        onView(withId(R.id.aepnf_save_phone_number_format_btn)).perform(click());

        List<PhoneNumberFormat> phoneNumberFormatList = PhoneNumberFormat.convertPnfPersistListIntoPnfList(getTestData());
        phoneNumberFormatList.add(newPhoneNumberFormat);

        // Check the list for the new entry
        checkListData(phoneNumberFormatList);
    }

    @Test
    public void testAddFormatCancel() {
        launch();
        showAddFormat();

        checkAddEditData(PhoneNumberFormat.builder()
                .setDescription("")
                .setFormat("")
                .setIsEnabled(true)
                .build());

        PhoneNumberFormat newPhoneNumberFormat = getEmptyPhoneNumberFormat();
        applyEditedData(newPhoneNumberFormat);

        // Save the new data
        onView(withId(R.id.aepnf_cancel_phone_number_format_btn)).perform(click());

        List<PhoneNumberFormat> phoneNumberFormatList = PhoneNumberFormat.convertPnfPersistListIntoPnfList(getTestData());

        // Check the list for the new entry
        checkListData(phoneNumberFormatList);
    }

    @Test
    public void testDeleteData() {
        launch();

        int itemPosition = 0;
        showDisplayController(itemPosition);

        List<PhoneNumberFormat> phoneNumberFormatList = PhoneNumberFormat.convertPnfPersistListIntoPnfList(getTestData());
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

        List<PhoneNumberFormat> phoneNumberFormatList = PhoneNumberFormat.convertPnfPersistListIntoPnfList(getTestData());
        ListPhoneNumberFormatRobot.init(phoneNumberFormatList)
                .verifyListElements()
                .verifyErrorVisibility(ViewMatchers.Visibility.GONE);
    }

    private PhoneNumberFormat getEmptyPhoneNumberFormat() {
        return PhoneNumberFormat.builder()
                .setDescription("")
                .setFormat("")
                .setIsEnabled(true)
                .build();
    }

    private PhoneNumberFormat getFilledPhoneNumberFormat() {
        return PhoneNumberFormat.builder()
                .setId(10L)
                .setDescription("The description")
                .setFormat("The format")
                .setIsEnabled(false)
                .build();
    }

    private void checkDisplayData(PhoneNumberFormat format) {
        DisplayPhoneNumberFormatRobot.init()
                .verifyDescription(format.description())
                .verifyFormat(format.format())
                .verifyIsEnabled(format.isEnabled())
                .verifyErrorVisibility(ViewMatchers.Visibility.GONE);
    }

    private void checkAddEditData(PhoneNumberFormat format) {
        AddEditPhoneNumberFormatRobot.init()
                .verifyDescription(format.description())
                .verifyFormat(format.format())
                .verifyIsEnabled(format.isEnabled())
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
        onView(withId(R.id.aepnf_phone_number_format_description_edt)).perform(replaceText(formatEdited.description()));

        onView(withId(R.id.aepnf_phone_number_format_edt)).perform(ViewActions.clearText());
        onView(withId(R.id.aepnf_phone_number_format_edt)).perform(replaceText(formatEdited.format()));

        onView(withId(R.id.aepnf_is_phone_number_format_enabled_ckb)).perform(click());
    }

    private void initDb() {
        PhoneNumberFormatDao dao = database.phoneNumberFormatRegularDao();

        List<PhoneNumberFormatPersist> phoneNumberFormatPersistList = getTestData();
        for (PhoneNumberFormatPersist format : phoneNumberFormatPersistList) {
            dao.insertPhoneNumberFormat(format);
        }
    }

    private List<PhoneNumberFormatPersist> getTestData() {
        return Arrays.asList(
                new PhoneNumberFormatPersist(
                        1L,
                        "All Swisscom numbers",
                        "079 ### ## ##",
                        false),

                new PhoneNumberFormatPersist(
                        2L,
                        "Seychelles",
                        "+248 ### ### ##",
                        true),

                new PhoneNumberFormatPersist(
                        3L,
                        "Annoying telemarketing company",
                        "023 43 34 35 66",
                        true)
        );
    }
}