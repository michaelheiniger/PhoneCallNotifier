package ch.qscqlmpa.phonecallnotifier.phonenumberformat.phonenumberformatlist;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatPersist;
import ch.qscqlmpa.phonecallnotifier.data.phonenumberformat.PhoneNumberFormatRepository;
import ch.qscqlmpa.phonecallnotifier.lifecycle.DisposableManager;
import ch.qscqlmpa.phonecallnotifier.model.PhoneNumberFormat;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class PhoneNumberFormatPersistListPresenterTest {

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
        List<PhoneNumberFormat> pnfList = setupPhoneNumberFormatListSuccess();
        initializePresenter();

        verify(repository).getAllPhoneNumberFormats();
        verify(onSuccessConsumer).accept(pnfList);
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
        List<PhoneNumberFormat> pnfList = phoneNumberFormatListToDisplay();
        when(repository.getAllPhoneNumberFormats()).thenReturn(Flowable.just(pnfList));
        return pnfList;
    }

    private List<PhoneNumberFormat> phoneNumberFormatListToDisplay() {
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

    private void initializePresenter() {
        presenter = new PhoneNumberFormatListPresenter(viewModel, repository, mock(DisposableManager.class));
    }

    private Throwable setUpError() {
        Throwable error = new IOException();
        when(repository.getAllPhoneNumberFormats()).thenReturn(Flowable.error(error));

        return error;
    }
}
