package ch.qscqlmpa.phonecallnotifier.phonenumberformat.addeditphonenumberformat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import java.io.IOException;

import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatPersistenceManager;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormat;
import ch.qscqlmpa.phonecallnotifier.data.phonenumberformat.PhoneNumberFormatRepository;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class AddEditPhoneNumberFormatPresenterTest {

    @Mock AddEditPhoneNumberFormatViewModel viewModel;
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
        PhoneNumberFormat format = setupNewPhoneNumberFormatSuccess();
        initializePresenterNewFormat(format);

        presenter.onSaveClick(format);

        verify(phoneNumberFormatPersistenceManager).insertPhoneNumberFormat(format);
        verify(phoneNumberFormatPersistenceManager, times(0)).updatePhoneNumberFormat(format);
    }

    @Test
    public void saveEditedPhoneNumberFormatTest() {
        PhoneNumberFormat format = setupPhoneNumberFormatToEditSuccess();
        initializePresenterEditFormat(format);

        PhoneNumberFormat formatFromDb = new PhoneNumberFormat(
                format.getDescription(),
                format.getFormat(),
                format.getIsEnabled()
        );
        when(viewModel.lastPhoneNumberFormat()).thenReturn(formatFromDb);

        presenter.onSaveClick(format);

        verify(phoneNumberFormatPersistenceManager).updatePhoneNumberFormat(format);
        verify(phoneNumberFormatPersistenceManager, times(0)).insertPhoneNumberFormat(format);
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
        PhoneNumberFormat phoneNumberFormat = newPhoneNumberFormat();
        return phoneNumberFormat;
    }

    private void initializePresenterNewFormat(PhoneNumberFormat phoneNumberFormat) {
        presenter = new AddEditPhoneNumberFormatPresenter(
                viewModel,
                repository,
                phoneNumberFormatPersistenceManager,
                phoneNumberFormat
        );
    }

    private void initializePresenterEditFormat(PhoneNumberFormat phoneNumberFormat) {
        presenter = new AddEditPhoneNumberFormatPresenter(
                viewModel,
                repository,
                phoneNumberFormatPersistenceManager,
                phoneNumberFormat
        );
    }

    private PhoneNumberFormat newPhoneNumberFormat() {
        boolean isEnabled = true;
        PhoneNumberFormat phoneNumberFormat = new PhoneNumberFormat(isEnabled);
        return phoneNumberFormat;
    }

    private PhoneNumberFormat phoneNumberFormatToEdit() {
        Integer id = 1;
        String description = "the format description";
        String format = " the format";
        Boolean isEnabled = true;
        return new PhoneNumberFormat(id, description, format, isEnabled);
    }

    private Throwable setUpError() {
        Throwable error = new IOException();
        return error;
    }
}
