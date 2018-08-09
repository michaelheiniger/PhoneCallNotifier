package ch.qscqlmpa.phonecallnotifier.phonenumberformat.addeditphonenumberformat;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.bluelinelabs.conductor.Controller;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import ch.qscqlmpa.phonecallnotifier.R;
import ch.qscqlmpa.phonecallnotifier.base.BaseController;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormat;
import ch.qscqlmpa.phonecallnotifier.phonenumberformat.PhoneNumberFormatController;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class AddEditPhoneNumberFormatController extends BaseController {

    static final String PHONE_NUMBER_FORMAT_TO_EDIT_KEY = "phone_number_format_to_edit";

    public static Controller newInstance() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(PHONE_NUMBER_FORMAT_TO_EDIT_KEY, new PhoneNumberFormat(true));
        return new AddEditPhoneNumberFormatController(bundle);
    }

    public static Controller newInstance(PhoneNumberFormat phoneNumberFormat) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(PHONE_NUMBER_FORMAT_TO_EDIT_KEY, phoneNumberFormat);
        return new AddEditPhoneNumberFormatController(bundle);
    }

    @Inject
    AddEditPhoneNumberFormatPresenter presenter;

    @Inject
    AddEditPhoneNumberFormatViewModel viewModel;

    @BindView(R.id.aepnf_phone_number_format_description_edt) EditText phoneNumberFormatDescriptionEdt;

    @BindView(R.id.aepnf_phone_number_format_edt) EditText phoneNumberFormatEdt;

    @BindView(R.id.aepnf_is_phone_number_format_enabled_ckb) CheckBox phoneNumberFormatEnabledCkb;

    @BindView(R.id.aepnf_error_tv) TextView errorTv;

    public AddEditPhoneNumberFormatController(Bundle bundle) {
        super(bundle);
    }

    @Override
    protected int layoutRes() {
        return R.layout.layout_add_edit_phone_number_format;
    }

    @Override
    protected Disposable[] subscriptions() {
        return new Disposable[] {
                viewModel.phoneNumberFormat()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(phoneNumberFormat -> {
                    phoneNumberFormatDescriptionEdt.setText(phoneNumberFormat.getDescription());
                    phoneNumberFormatEdt.setText(phoneNumberFormat.getFormat());
                    phoneNumberFormatEnabledCkb.setChecked(phoneNumberFormat.getIsEnabled());
                    phoneNumberFormatEnabledCkb.setText(phoneNumberFormat.getIsEnabled() ? R.string.format_enabled : R.string.format_disabled);
                }),

                viewModel.error()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(errorRes -> {
                    if (errorRes == -1) {
                        errorTv.setText(null);
                        errorTv.setVisibility(View.GONE);
                    } else {
                        errorTv.setVisibility(View.VISIBLE);
                        errorTv.setText(errorRes);
                    }
                })
        };
    }

    @OnClick(R.id.aepnf_save_phone_number_format_btn)
    public void onSaveClicked() {

        PhoneNumberFormat phoneNumberFormat = new PhoneNumberFormat(
                phoneNumberFormatDescriptionEdt.getText().toString(),
                phoneNumberFormatEdt.getText().toString(),
                phoneNumberFormatEnabledCkb.isChecked()
        );
        presenter.onSaveClick(phoneNumberFormat);

        Controller parentController = getParentController();
        if (parentController instanceof PhoneNumberFormatController) {
            ((PhoneNumberFormatController) parentController).saveEditPhoneNumberFormat();
        }
    }

    @OnClick(R.id.aepnf_cancel_phone_number_format_btn)
    public void onCancelClicked() {
        Controller parentController = getParentController();
        if (parentController instanceof PhoneNumberFormatController) {
            ((PhoneNumberFormatController) parentController).cancelEditPhoneNumberFormat();
        }
    }
}
