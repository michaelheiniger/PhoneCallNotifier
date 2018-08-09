package ch.qscqlmpa.phonecallnotifier.data.phonenumberformat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import ch.qscqlmpa.phonecallnotifier.data.database.AppRoomDatabase;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormat;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatDao;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.when;

public class PhoneNumberFormatRepositoryTest {

    private PhoneNumberFormatRepository repository;


    @Mock AppRoomDatabase database;
    @Mock
    PhoneNumberFormatDao dao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(database.phoneNumberFormatRegularDao()).thenReturn(dao);

        repository = new PhoneNumberFormatRepository(database, Schedulers.trampoline());
    }

    @Test
    public void getAllPhoneNumberFormats() {
        List<PhoneNumberFormat> formatList = setupPhoneNumberFormatList();
        when(dao.getAllPhoneNumberFormats()).thenReturn(Flowable.just(formatList));

        repository.getAllPhoneNumberFormats().test().assertValues(formatList);
    }

    @Test
    public void getPhoneNumberFormat() {
        List<PhoneNumberFormat> formatList = setupPhoneNumberFormatList();
        when(dao.getPhoneNumberFormat(formatList.get(1).getId())).thenReturn(Single.just(formatList.get(1)));

        repository.getPhoneNumberFormat(formatList.get(1)).test().assertValues(formatList.get(1));
    }


    private List<PhoneNumberFormat> setupPhoneNumberFormatList() {
        return Arrays.asList(
                new PhoneNumberFormat(1, "description1", "format1", true),
                new PhoneNumberFormat(2, "description2", "format2", false),
                new PhoneNumberFormat(3, "description3", "format3", true)
        );
    }
}
