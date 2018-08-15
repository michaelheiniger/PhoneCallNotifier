package ch.qscqlmpa.phonecallnotifier.phonenumberformat.addeditphonenumberformat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class AddEditPhoneNumberFormatPresenterTest {

    @Mock
    PhoneNumberFormatViewModel viewModel;
    @Mock
    PhoneNumberFormatRepository repository;
    @Mock
    PhoneNumberFormatPersistenceManager phoneNumberFormatPersistenceManager;
    @Mock Consumer<Throwable> onErrorConsumer;
    @Mock Consumer<PhoneNumberFormat> onSuccessConsumer;

    private AddEditPhoneNumberFormatPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(viewModel.onError()).thenReturn(onErrorConsumer);
        when(viewModel.phoneNumberFormatUpdated()).thenReturn(onSuccessConsumer);
    }

    @Test
    public void saveNewPhoneNumberFormatTest() {
        PhoneNumberFormat pnf = setupNewPhoneNumberFormatSuccess();
        PhoneNumberFormatPersist pnfPersist = PhoneNumberFormatPersist.convertPnfToPnfPersist(pnf);
        initializePresenterNewFormat(pnf);

        presenter.onSaveClick(pnf);

        ArgumentCaptor<PhoneNumberFormatPersist> argForInsert = ArgumentCaptor.forClass(PhoneNumberFormatPersist.class);
        verify(phoneNumberFormatPersistenceManager).insertPhoneNumberFormat(argForInsert.capture());
        assertEquals(true, argForInsert.getValue().isContentEqual(pnfPersist));

        verify(phoneNumberFormatPersistenceManager, times(0)).updatePhoneNumberFormat(pnfPersist);
    }

    @Test
    public void saveEditedPhoneNumberFormatTest() {
        PhoneNumberFormat pnf = setupPhoneNumberFormatToEditSuccess();
        initializePresenterEditFormat(pnf);

        PhoneNumberFormatPersist pnfPersist = PhoneNumberFormatPersist.convertPnfToPnfPersist(pnf);

        when(viewModel.lastPhoneNumberFormat()).thenReturn(pnf);

        presenter.onSaveClick(pnf);

        ArgumentCaptor<PhoneNumberFormatPersist> argForUpdate = ArgumentCaptor.forClass(PhoneNumberFormatPersist.class);
        verify(phoneNumberFormatPersistenceManager).updatePhoneNumberFormat(argForUpdate.capture());
        assertEquals(true, argForUpdate.getValue().isContentEqual(pnfPersist));

        verify(phoneNumberFormatPersistenceManager, times(0)).insertPhoneNumberFormat(pnfPersist);
    }

    @Test
    public void phoneNumberFormatToEditLoaded() throws Exception {
        PhoneNumberFormat format = setupPhoneNumberFormatToEditSuccess();
        initializePresenterEditFormat(format);

        verify(repository).getPhoneNumberFormat(format);
        verify(onSuccessConsumer).accept(format);
        verifyZeroInteractions(onErrorConsumer);
    }

    @Test
    public void phoneNumberFormatNewLoaded() throws Exception {
        PhoneNumberFormat format = setupNewPhoneNumberFormatSuccess();
        initializePresenterNewFormat(format);

        verifyZeroInteractions(repository);
        verify(onSuccessConsumer).accept(format); // Uses equals() for comparison ---> error TODO: Fix
        verifyZeroInteractions(onErrorConsumer);
    }

    @Test
    public void phoneNumberFormatToEditLoadedError() throws Exception {
        Throwable error = setUpError();
        PhoneNumberFormat phoneNumberFormat = phoneNumberFormatToEdit();
        when(repository.getPhoneNumberFormat(phoneNumberFormat)).thenReturn(Single.error(error));
        initializePresenterEditFormat(phoneNumberFormat);

        verify(onErrorConsumer).accept(error);
        verifyZeroInteractions(onSuccessConsumer);
    }

    @Test
    public void phoneNumberFormatNewLoadedError() throws Exception {
        Throwable error = setUpError();
        PhoneNumberFormat phoneNumberFormat = phoneNumberFormatToEdit();
        when(repository.getPhoneNumberFormat(phoneNumberFormat)).thenReturn(Single.error(error));
        initializePresenterNewFormat(phoneNumberFormat);

        verify(onErrorConsumer).accept(error);
        verifyZeroInteractions(onSuccessConsumer);
    }

    private PhoneNumberFormat setupPhoneNumberFormatToEditSuccess() {
        PhoneNumberFormat phoneNumberFormat = phoneNumberFormatToEdit();
        when(repository.getPhoneNumberFormat(phoneNumberFormat)).thenReturn(Single.just(phoneNumberFormat));
        return phoneNumberFormat;
    }

    private PhoneNumberFormat setupNewPhoneNumberFormatSuccess() {
        return newPhoneNumberFormat();
    }

    private void initializePresenterNewFormat(PhoneNumberFormat phoneNumberFormat) {
        presenter = new AddEditPhoneNumberFormatPresenter(
                viewModel,
                repository,
                phoneNumberFormatPersistenceManager,
                mock(DisposableManager.class),
                phoneNumberFormat
        );
    }

    private void initializePresenterEditFormat(PhoneNumberFormat phoneNumberFormat) {
        presenter = new AddEditPhoneNumberFormatPresenter(
                viewModel,
                repository,
                phoneNumberFormatPersistenceManager,
                mock(DisposableManager.class),
                phoneNumberFormat
        );
    }

    private PhoneNumberFormat newPhoneNumberFormat() {
        return PhoneNumberFormat.builder()
                .setDescription("")
                .setFormat("")
                .setIsEnabled(true)
                .build();
    }

    private PhoneNumberFormat phoneNumberFormatToEdit() {
        return PhoneNumberFormat.builder()
                .setId(1L)
                .setDescription("The format description")
                .setFormat("The format")
                .setIsEnabled(true)
                .build();
    }

    private Throwable setUpError() {
        Throwable error = new IOException();
        return error;
    }
}
