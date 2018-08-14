package ch.qscqlmpa.phonecallnotifier.phonenumberformat.displayphonenumberformat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormat;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatPersistenceManager;
import ch.qscqlmpa.phonecallnotifier.data.phonenumberformat.PhoneNumberFormatRepository;
import ch.qscqlmpa.phonecallnotifier.lifecycle.DisposableManager;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class DisplayPhoneNumberFormatPresenterTest {

    @Mock DisplayPhoneNumberFormatViewModel viewModel;
    @Mock
    PhoneNumberFormatRepository repository;
    @Mock
    PhoneNumberFormatPersistenceManager phoneNumberFormatPersistenceManager;
    @Mock Consumer<Throwable> onErrorConsumer;
    @Mock Consumer<PhoneNumberFormat> onSuccessConsumer;

    private DisplayPhoneNumberFormatPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(viewModel.onError()).thenReturn(onErrorConsumer);
        when(viewModel.phoneNumberFormatUpdated()).thenReturn(onSuccessConsumer);
    }

    @Test
    public void phoneNumberFormatToDisplayLoaded() throws Exception {
        PhoneNumberFormat format = setupPhoneNumberFormatToDisplaySuccess();
        initializePresenter(format);

        verify(repository).getPhoneNumberFormat(format);
        verify(onSuccessConsumer).accept(format);
        verifyZeroInteractions(onErrorConsumer);
    }

    @Test
    public void phoneNumberFormatToDisplayLoadedError() throws Exception {
        Throwable error = setUpError();
        initializePresenter(null);

        verify(onErrorConsumer).accept(error);
        verifyZeroInteractions(onSuccessConsumer);
    }

    @Test
    public void deletePhoneNumberFormatTest() {
        PhoneNumberFormat format = setupPhoneNumberFormatToDisplaySuccess();
        initializePresenter(format);

        when(viewModel.lastPhoneNumberFormat()).thenReturn(format);

        presenter.deletePhoneNumberFormat();

        verify(phoneNumberFormatPersistenceManager).deletePhoneNumberFormat(format);
    }

    private PhoneNumberFormat setupPhoneNumberFormatToDisplaySuccess() {
        PhoneNumberFormat phoneNumberFormat = phoneNumberFormatToDisplay();
        when(repository.getPhoneNumberFormat(phoneNumberFormat)).thenReturn(Single.just(phoneNumberFormat));
        return phoneNumberFormat;
    }

    private PhoneNumberFormat phoneNumberFormatToDisplay() {
        Integer id = 1;
        String description = "the format description";
        String format = " the format";
        Boolean isEnabled = true;
        return new PhoneNumberFormat(id, description, format, isEnabled);
    }

    private void initializePresenter(PhoneNumberFormat phoneNumberFormat) {
        presenter = new DisplayPhoneNumberFormatPresenter(
                viewModel,
                repository,
                phoneNumberFormatPersistenceManager,
                mock(DisposableManager.class),
                phoneNumberFormat
        );
    }

    private Throwable setUpError() {
        Throwable error = new IOException();
        when(repository.getPhoneNumberFormat(null)).thenReturn(Single.error(error));

        return error;
    }
}