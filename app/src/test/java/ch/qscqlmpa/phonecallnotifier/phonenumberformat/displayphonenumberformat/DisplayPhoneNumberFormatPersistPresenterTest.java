package ch.qscqlmpa.phonecallnotifier.phonenumberformat.displayphonenumberformat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatPersist;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatPersistenceManager;
import ch.qscqlmpa.phonecallnotifier.data.phonenumberformat.PhoneNumberFormatRepository;
import ch.qscqlmpa.phonecallnotifier.lifecycle.DisposableManager;
import ch.qscqlmpa.phonecallnotifier.model.PhoneNumberFormat;
import ch.qscqlmpa.phonecallnotifier.phonenumberformat.PhoneNumberFormatViewModel;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class DisplayPhoneNumberFormatPersistPresenterTest {

    @Mock
    PhoneNumberFormatViewModel viewModel;
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
        PhoneNumberFormat pnf = setupPhoneNumberFormatToDisplaySuccess();
        initializePresenter(pnf);

        verify(repository).getPhoneNumberFormat(pnf);
        verify(onSuccessConsumer).accept(pnf);
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
        PhoneNumberFormat pnf = setupPhoneNumberFormatToDisplaySuccess();
        initializePresenter(pnf);

        when(viewModel.lastPhoneNumberFormat()).thenReturn(pnf);

        presenter.deletePhoneNumberFormat();

        verify(phoneNumberFormatPersistenceManager).deletePhoneNumberFormat(pnf.id());
    }

    private PhoneNumberFormat setupPhoneNumberFormatToDisplaySuccess() {
        PhoneNumberFormat pnf = phoneNumberFormatToDisplay();
        when(repository.getPhoneNumberFormat(pnf)).thenReturn(Single.just(pnf));
        return pnf;
    }

    private PhoneNumberFormat phoneNumberFormatToDisplay() {
        return PhoneNumberFormat.builder()
                .setId(1L)
                .setDescription("The format description")
                .setFormat("The format")
                .setIsEnabled(true)
                .build();
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