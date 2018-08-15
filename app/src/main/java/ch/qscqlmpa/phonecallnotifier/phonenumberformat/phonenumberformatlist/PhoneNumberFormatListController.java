package ch.qscqlmpa.phonecallnotifier.phonenumberformat.phonenumberformatlist;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class PhoneNumberFormatListController extends BaseController  implements PhoneNumberFormatsAdapter.PhoneNumberFormatClickedListener {

    @Inject
    PhoneNumberFormatListPresenter presenter;

    @Inject
    PhoneNumberFormatListViewModel viewModel;

    @BindView(R.id.lpnf_phone_number_formats_recycler_view)
    RecyclerView phoneNumberFormatList;

    @BindView(R.id.lpnf_error_tv)
    TextView errorTv;

    @Override
    protected void onViewBound(View view) {
        super.onViewBound(view);
        phoneNumberFormatList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        phoneNumberFormatList.setAdapter(new PhoneNumberFormatsAdapter(this));

    }

    @Override
    protected Disposable[] subscriptions() {
        return new Disposable[] {

            viewModel.phoneNumberFormats()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(phoneNumberFormats ->
                    ((PhoneNumberFormatsAdapter) phoneNumberFormatList
                            .getAdapter())
                            .setData(phoneNumberFormats)),

            viewModel.error()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(errorRes -> {
                if (errorRes == -1) {
                    errorTv.setText(null);
                    errorTv.setVisibility(View.GONE);
                } else {
                    errorTv.setVisibility(View.VISIBLE);
                    phoneNumberFormatList.setVisibility(View.GONE);
                    errorTv.setText(errorRes);
                }
            })
        };
    }

    @Override
    protected int layoutRes() {
        return R.layout.layout_phone_number_formats_list;
    }

    @OnClick(R.id.lpnf_add_new_phone_number_formats_btn)
    public void onAddPhoneNumberFormatClicked() {
        Controller parentController = getParentController();
        if (parentController instanceof PhoneNumberFormatController) {
            ((PhoneNumberFormatController) parentController).addPhoneNumberFormat();
        }
    }

    @Override
    public void onPhoneNumberFormatClicked(PhoneNumberFormat formatToEdit) {
        Controller parentController = getParentController();
        if (parentController instanceof PhoneNumberFormatController) {
            ((PhoneNumberFormatController) parentController).displayPhoneNumberFormat(formatToEdit);
        }
    }
}
