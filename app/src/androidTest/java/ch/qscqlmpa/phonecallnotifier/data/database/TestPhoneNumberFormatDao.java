package ch.qscqlmpa.phonecallnotifier.data.database;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormat;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatDao;


@RunWith(AndroidJUnit4.class)
public class TestPhoneNumberFormatDao {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppRoomDatabase database;

    private PhoneNumberFormatDao dao;

    @Before
    public void initDb() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppRoomDatabase.class)
                .allowMainThreadQueries()
                .build();

        dao = database.phoneNumberFormatRegularDao();
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void getPhoneNumberFormat() {
        List<PhoneNumberFormat> phoneNumberFormatEnabledList = getTestData();
        fillDb(phoneNumberFormatEnabledList);

        PhoneNumberFormat format = phoneNumberFormatEnabledList.get(0);

        dao.getPhoneNumberFormat(format.getId()).test().assertValue(f -> format.isContentEqual(f));
    }

    @Test
    public void getEnabledPhoneNumberFormats() {
        List<PhoneNumberFormat> phoneNumberFormatEnabledList = Arrays.asList(
                new PhoneNumberFormat(1, "description1", "format1", true),
                new PhoneNumberFormat(3, "description3", "format3", true));
        List<PhoneNumberFormat> phoneNumberFormatDisabledList = Arrays.asList(
                new PhoneNumberFormat(2, "description2", "format2", false));

        fillDb(phoneNumberFormatEnabledList);
        fillDb(phoneNumberFormatDisabledList);

        dao.getEnabledPhoneNumberFormats().test().assertValue(formatReceivedList -> {
            boolean allFormatsAreEnabled = true;
            boolean allFormatsAreReturned = true;
            for (int i = 0; i < formatReceivedList.size(); i++) {

                PhoneNumberFormat format = formatReceivedList.get(i);
                if (!format.isContentEqual(phoneNumberFormatEnabledList.get(i))) {
                    allFormatsAreReturned = false;
                    break;
                }

                if (!format.getIsEnabled()) {
                    allFormatsAreEnabled = false;
                    break;
                }
            }
            // Check that the content is correct
            // Check that we have only enabled formats
            // Check that all the enabled formats are returned
            return allFormatsAreEnabled
                    && allFormatsAreReturned
                    && formatReceivedList.size() == phoneNumberFormatEnabledList.size();
        });
    }

    @Test
    public void getAllPhoneNumberFormats() {
        List<PhoneNumberFormat> formatList = getTestData();
        fillDb(formatList);

        dao.getAllPhoneNumberFormats().test().assertValue(formatReceivedList -> {
            boolean allFormatsAreReturned = true;
            for (int i = 0; i < formatList.size(); i++) {

                PhoneNumberFormat format = formatList.get(i);
                if (!format.isContentEqual(formatReceivedList.get(i))) {
                    allFormatsAreReturned = false;
                    break;
                }
            }
            // Check that the content is correct
            // Check that all the formats are returned
            return allFormatsAreReturned
                    && formatList.size() == formatReceivedList.size();
        });

    }

    private void fillDb(List<PhoneNumberFormat> phoneNumberFormatList) {
        for (PhoneNumberFormat format : phoneNumberFormatList) {
            dao.insertPhoneNumberFormat(format);
        }
    }

    private List<PhoneNumberFormat> getTestData() {
        return Arrays.asList(
                new PhoneNumberFormat(1, "description1", "format1", true),
                new PhoneNumberFormat(2, "description2", "format2", false),
                new PhoneNumberFormat(3, "description3", "format3", true)
        );
    }
}
