package ch.qscqlmpa.phonecallnotifier.phonenumberformat.displayphonenumberformat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bluelinelabs.conductor.Controller;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import ch.qscqlmpa.phonecallnotifier.R;
import ch.qscqlmpa.phonecallnotifier.base.BaseController;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatPersist;
import ch.qscqlmpa.phonecallnotifier.model.PhoneNumberFormat;
import ch.qscqlmpa.phonecallnotifier.phonenumberformat.PhoneNumberFormatController;
import ch.qscqlmpa.phonecallnotifier.phonenumberformat.PhoneNumberFormatViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class DisplayPhoneNumberFormatController extends BaseController {

    static final String PHONE_NUMBER_FORMAT_KEY = "phone_number_format";

    public static Controller newInstance(PhoneNumberFormat phoneNumberFormat) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(PHONE_NUMBER_FORMAT_KEY, phoneNumberFormat);
        return new DisplayPhoneNumberFormatController(bundle);
    }

    @Inject
    DisplayPhoneNumberFormatPresenter presenter;

    @Inject
    PhoneNumberFormatViewModel viewModel;

    @BindView(R.id.dpnf_phone_number_format_description_tv)
    TextView phoneNumberFormatDescriptionTv;

    @BindView(R.id.dpnf_phone_number_format_tv)
    TextView phoneNumberFormatTv;

    @BindView(R.id.dpnf_is_phone_number_format_enabled_ckb)
    CheckBox phoneNumberFormatEnabledCkb;

    @BindView(R.id.dpnf_edit_phone_number_format_btn)
    Button editBtn;

    @BindView(R.id.dpnf_delete_phone_number_format_btn)
    Button deleteBtn;

    @BindView(R.id.dpnf_error_tv)
    TextView errorTv;

    public DisplayPhoneNumberFormatController() {
        super();
    }

    public DisplayPhoneNumberFormatController(Bundle bundle) {
        super(bundle);
    }

    @Override
    protected Disposable[] subscriptions() {
        return new Disposable[] {

                viewModel.phoneNumberFormat()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(phoneNumberFormat -> {
                            phoneNumberFormatDescriptionTv.setText(phoneNumberFormat.description());
                            phoneNumberFormatTv.setText(phoneNumberFormat.format());
                            phoneNumberFormatEnabledCkb.setChecked(phoneNumberFormat.isEnabled());
                            phoneNumberFormatEnabledCkb.setText(phoneNumberFormat.isEnabled() ? R.string.format_enabled : R.string.format_disabled);
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

    @Override
    protected int layoutRes() {
        return R.layout.layout_display_phone_number_format;
    }

    @OnClick(R.id.dpnf_edit_phone_number_format_btn)
    void editPhoneNumberFormat() {

        Controller parentController = getParentController();
        if (parentController instanceof PhoneNumberFormatController) {
            PhoneNumberFormat formatToEdit = presenter.getPhoneNumberFormat();
            ((PhoneNumberFormatController) parentController).editPhoneNumberFormat(formatToEdit);
        }
    }

    @OnClick(R.id.dpnf_delete_phone_number_format_btn)
    void deletePhoneNumberFormat() {

        presenter.deletePhoneNumberFormat();

        Controller parentController = getParentController();
        if (parentController instanceof PhoneNumberFormatController) {
            ((PhoneNumberFormatController) parentController).deletePhoneNumberFormat();
        }
    }
}
