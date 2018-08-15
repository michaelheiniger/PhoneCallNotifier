package ch.qscqlmpa.phonecallnotifier.phonenumberformat.phonenumberformatlist;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ch.qscqlmpa.phonecallnotifier.R;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatPersist;
import ch.qscqlmpa.phonecallnotifier.model.PhoneNumberFormat;
import io.reactivex.observers.TestObserver;

public class PhoneNumberFormatPersistListViewModelTest {

    private PhoneNumberFormatListViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new PhoneNumberFormatListViewModel();
    }

    @Test
    public void formatList() throws Exception {
        List<PhoneNumberFormat> pnfList = phoneNumberFormatList();
        viewModel.phoneNumberFormatsUpdated().accept(pnfList);
        viewModel.phoneNumberFormats().test().assertValue(pnfList);
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
                PhoneNumberFormat.builder()
                        .setId(1L)
                        .setDescription("The description 1")
                        .setFormat("The format 1")
                        .setIsEnabled(true)
                        .build(),
                PhoneNumberFormat.builder()
                        .setId(2L)
                        .setDescription("The description 2")
                        .setFormat("The format 1")
                        .setIsEnabled(true)
                        .build(),
                PhoneNumberFormat.builder()
                        .setId(3L)
                        .setDescription("The description 3")
                        .setFormat("The format 1")
                        .setIsEnabled(false)
                        .build()
        );
    }
}
