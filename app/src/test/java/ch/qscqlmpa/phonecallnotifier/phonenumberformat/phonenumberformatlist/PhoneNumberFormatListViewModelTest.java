package ch.qscqlmpa.phonecallnotifier.phonenumberformat.phonenumberformatlist;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ch.qscqlmpa.phonecallnotifier.R;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormat;
import io.reactivex.observers.TestObserver;

public class PhoneNumberFormatListViewModelTest {

    private PhoneNumberFormatListViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new PhoneNumberFormatListViewModel();
    }

    @Test
    public void formatList() throws Exception {
        List<PhoneNumberFormat> phoneNumberFormatList = phoneNumberFormatList();
        viewModel.phoneNumberFormatsUpdated().accept(phoneNumberFormatList);
        viewModel.phoneNumberFormats().test().assertValue(phoneNumberFormatList);
    }

    @Test
    public void error() throws Exception {
        TestObserver<Integer> errorObserver = viewModel.error().test();
        viewModel.onError().accept(new IOException());
        viewModel.phoneNumberFormatsUpdated().accept(Collections.emptyList());

        errorObserver.assertValues(R.string.db_error_phonenumberformats,-1);
    }

    private List<PhoneNumberFormat> phoneNumberFormatList() {
        return Arrays.asList(
                new PhoneNumberFormat(1, "the description1", "the format1", true),
                new PhoneNumberFormat(2, "the description2", "the format2", true),
                new PhoneNumberFormat(3, "the description3", "the format3", false));
    }
}
