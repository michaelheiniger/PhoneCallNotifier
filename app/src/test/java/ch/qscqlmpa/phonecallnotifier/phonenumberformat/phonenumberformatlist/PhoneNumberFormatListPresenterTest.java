package ch.qscqlmpa.phonecallnotifier.phonenumberformat.phonenumberformatlist;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormat;
import ch.qscqlmpa.phonecallnotifier.data.phonenumberformat.PhoneNumberFormatRepository;
import ch.qscqlmpa.phonecallnotifier.lifecycle.DisposableManager;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class PhoneNumberFormatListPresenterTest {

    @Mock PhoneNumberFormatListViewModel viewModel;
    @Mock
    PhoneNumberFormatRepository repository;
    @Mock Consumer<Throwable> onErrorConsumer;
    @Mock Consumer<List<PhoneNumberFormat>> onSuccessConsumer;

    private PhoneNumberFormatListPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(viewModel.onError()).thenReturn(onErrorConsumer);
        when(viewModel.phoneNumberFormatsUpdated()).thenReturn(onSuccessConsumer);
    }

    @Test
    public void phoneNumberFormatListLoaded() throws Exception {
        List<PhoneNumberFormat> formatList = setupPhoneNumberFormatListSuccess();
        initializePresenter();

        verify(repository).getAllPhoneNumberFormats();
        verify(onSuccessConsumer).accept(formatList);
        verifyZeroInteractions(onErrorConsumer);
    }

    @Test
    public void phoneNumberFormatListLoadedError() throws Exception {
        Throwable error = setUpError();
        initializePresenter();

        verify(onErrorConsumer).accept(error);
        verifyZeroInteractions(onSuccessConsumer);
    }

    private List<PhoneNumberFormat> setupPhoneNumberFormatListSuccess() {
        List<PhoneNumberFormat> phoneNumberFormatList = phoneNumberFormatListToDisplay();
        when(repository.getAllPhoneNumberFormats()).thenReturn(Flowable.just(phoneNumberFormatList));
        return phoneNumberFormatList;
    }

    private List<PhoneNumberFormat> phoneNumberFormatListToDisplay() {
        return Arrays.asList(
                new PhoneNumberFormat(1, "the description1", "the format1", true),
                new PhoneNumberFormat(2, "the description2", "the format2", true),
                new PhoneNumberFormat(3, "the description3", "the format3", false));
    }

    private void initializePresenter() {
        presenter = new PhoneNumberFormatListPresenter(viewModel, repository, mock(DisposableManager.class));
    }

    private Throwable setUpError() {
        Throwable error = new IOException();
        when(repository.getAllPhoneNumberFormats()).thenReturn(Flowable.error(error));

        return error;
    }
}
