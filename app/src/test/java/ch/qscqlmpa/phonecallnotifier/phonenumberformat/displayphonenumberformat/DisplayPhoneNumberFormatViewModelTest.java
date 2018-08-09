package ch.qscqlmpa.phonecallnotifier.phonenumberformat.displayphonenumberformat;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import ch.qscqlmpa.phonecallnotifier.R;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormat;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertEquals;

public class DisplayPhoneNumberFormatViewModelTest {

    private DisplayPhoneNumberFormatViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new DisplayPhoneNumberFormatViewModel();
    }

    @Test
    public void format() throws Exception {
        PhoneNumberFormat phoneNumberFormat = phoneNumberFormatToDisplay();
        viewModel.phoneNumberFormatUpdated().accept(phoneNumberFormat);
        viewModel.phoneNumberFormat().test().assertValue(phoneNumberFormat);
    }

    @Test
    public void lastFormat() throws Exception {
        PhoneNumberFormat phoneNumberFormat = phoneNumberFormatToDisplay();
        viewModel.phoneNumberFormatUpdated().accept(new PhoneNumberFormat());
        viewModel.phoneNumberFormatUpdated().accept(phoneNumberFormat);

        assertEquals(true, phoneNumberFormat.isContentEqual(viewModel.lastPhoneNumberFormat()));
    }

    @Test
    public void error() throws Exception {
        TestObserver<Integer> errorObserver = viewModel.error().test();
        viewModel.onError().accept(new IOException());
        viewModel.phoneNumberFormatUpdated().accept(new PhoneNumberFormat());

        errorObserver.assertValues(R.string.db_error_phonenumberformat,-1);
    }

    private PhoneNumberFormat phoneNumberFormatToDisplay() {
        return new PhoneNumberFormat(1, "the description1", "the format1", true);
    }
}

