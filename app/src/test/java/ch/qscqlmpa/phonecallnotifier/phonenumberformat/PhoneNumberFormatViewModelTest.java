package ch.qscqlmpa.phonecallnotifier.phonenumberformat;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import ch.qscqlmpa.phonecallnotifier.R;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatPersist;
import ch.qscqlmpa.phonecallnotifier.model.PhoneNumberFormat;
import ch.qscqlmpa.phonecallnotifier.phonenumberformat.PhoneNumberFormatViewModel;
import io.reactivex.observers.TestObserver;

import static org.junit.Assert.assertEquals;

public class PhoneNumberFormatViewModelTest {

    private PhoneNumberFormatViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new PhoneNumberFormatViewModel();
    }

    @Test
    public void format() throws Exception {
        PhoneNumberFormat phoneNumberFormat = phoneNumberFormatToEdit();
        viewModel.phoneNumberFormatUpdated().accept(phoneNumberFormat);
        viewModel.phoneNumberFormat().test().assertValue(phoneNumberFormat);

        assertEquals(true, phoneNumberFormat.equals(viewModel.lastPhoneNumberFormat()));
    }

    @Test
    public void lastFormat() throws Exception {
        PhoneNumberFormat phoneNumberFormat = phoneNumberFormatToEdit();
        viewModel.phoneNumberFormatUpdated().accept(otherPhoneNumberFormat());
        viewModel.phoneNumberFormatUpdated().accept(phoneNumberFormat);

        assertEquals(true, phoneNumberFormat.equals(viewModel.lastPhoneNumberFormat()));
    }

    @Test
    public void error() throws Exception {
        TestObserver<Integer> errorObserver = viewModel.error().test();
        viewModel.onError().accept(new IOException());
        viewModel.phoneNumberFormatUpdated().accept(otherPhoneNumberFormat());
        errorObserver.assertValues(R.string.db_error_phonenumberformat,-1);
    }

    private PhoneNumberFormat phoneNumberFormatToEdit() {
        return PhoneNumberFormat.builder()
                .setId(1L)
                .setDescription("the description1")
                .setFormat("the format1")
                .setIsEnabled(true)
                .build();
    }

    private PhoneNumberFormat otherPhoneNumberFormat() {
        return PhoneNumberFormat.builder()
                .setId(2L)
                .setDescription("the description2")
                .setFormat("the format2")
                .setIsEnabled(false)
                .build();
    }
}

