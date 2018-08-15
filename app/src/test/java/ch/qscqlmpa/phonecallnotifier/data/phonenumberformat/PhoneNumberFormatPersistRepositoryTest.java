package ch.qscqlmpa.phonecallnotifier.data.phonenumberformat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import ch.qscqlmpa.phonecallnotifier.data.database.AppRoomDatabase;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatPersist;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatDao;
import ch.qscqlmpa.phonecallnotifier.model.PhoneNumberFormat;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.when;

public class PhoneNumberFormatPersistRepositoryTest {

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
        List<PhoneNumberFormatPersist> pnfPersistList = setupPhoneNumberFormatList();
        List<PhoneNumberFormat> pnfList = PhoneNumberFormat.convertPnfPersistListIntoPnfList(pnfPersistList);

        when(dao.getAllPhoneNumberFormats()).thenReturn(Flowable.just(pnfPersistList));

        repository.getAllPhoneNumberFormats().test().assertValues(pnfList);
    }

    @Test
    public void getPhoneNumberFormat() {
        List<PhoneNumberFormatPersist> pnfPersistList = setupPhoneNumberFormatList();
        when(dao.getPhoneNumberFormat(pnfPersistList.get(1).getId())).thenReturn(Single.just(pnfPersistList.get(1)));

        PhoneNumberFormat pnf = PhoneNumberFormat.convertPnfPersistToPnf(pnfPersistList.get(1));
        repository.getPhoneNumberFormat(pnf).test().assertValues(pnf);
    }


    private List<PhoneNumberFormatPersist> setupPhoneNumberFormatList() {
        return Arrays.asList(
                new PhoneNumberFormatPersist(1L, "description1", "format1", true),
                new PhoneNumberFormatPersist(2L, "description2", "format2", false),
                new PhoneNumberFormatPersist(3L, "description3", "format3", true)
        );
    }
}
