package ch.qscqlmpa.phonecallnotifier.phonenumberformat.addeditphonenumberformat;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

    @OnClick(R.id.aepnf_format_help_btn)
    public void displayHelp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(
                "The format of a phone number (as understood in this app) can contain the following characters:\n"
                + "digits [0-9], + which is understood as 00, spaces which are ignored (it allows to make the format easier to read for humans),"
                + " dash # which is a placeholder for one digit. Using # allows to determine the number of digits that "
                + "contain the numbers to be blocked. There is no wildcard allowing to block numbers with an arbitrary number of digits to "
                + "avoid user mistakes (which could lead to block unexpected numbers) Any other characters are ignored\n"
                + "Examples: +41 78 772 23 23 is the same as 0041 78 772 23 23 and corresponds to as single phone number\n"
                + "00248 ### ### ## corresponds to any phone number starting with 00248 followed by any 8 digits\n"
                + "0aq034gx?$ / corresponds to the number 0034")
                .setTitle("Help - Phone number format")
                .setPositiveButton("Ok",null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
