package ch.qscqlmpa.phonecallnotifier.phonenumberformat;

import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;

import butterknife.BindView;
import ch.qscqlmpa.phonecallnotifier.R;
import ch.qscqlmpa.phonecallnotifier.base.BaseController;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormat;
import ch.qscqlmpa.phonecallnotifier.phonenumberformat.addeditphonenumberformat.AddEditPhoneNumberFormatController;
import ch.qscqlmpa.phonecallnotifier.phonenumberformat.displayphonenumberformat.DisplayPhoneNumberFormatController;
import ch.qscqlmpa.phonecallnotifier.phonenumberformat.phonenumberformatlist.PhoneNumberFormatListController;

public class PhoneNumberFormatController extends BaseController {

    @BindView(R.id.phone_number_formats_list_frame)
    ViewGroup phoneNumberFormatsListFrame;

    @BindView(R.id.phone_number_format_frame)
    ViewGroup phoneNumberFormatFrame;

    Router phoneNumberFormatRouter;

    Router phoneNumberFormatsListRouter;


    public PhoneNumberFormatController() {}

    @Override
    protected void onViewBound(View view) {
        super.onViewBound(view);

        phoneNumberFormatsListRouter = getChildRouter(phoneNumberFormatsListFrame);
        if (!phoneNumberFormatsListRouter.hasRootController()) {
            phoneNumberFormatsListRouter.setRoot(RouterTransaction.with(new PhoneNumberFormatListController()));
        }

        phoneNumberFormatRouter = getChildRouter(phoneNumberFormatFrame);
        if (!phoneNumberFormatRouter.hasRootController()) {
            phoneNumberFormatRouter.setRoot(RouterTransaction.with(new EmptyLayoutController()));
        }
    }

    @Override
    protected int layoutRes() {
        return R.layout.controller_phonenumberformat;
    }

    public void displayPhoneNumberFormat(PhoneNumberFormat phoneNumberFormat) {
        phoneNumberFormatRouter.pushController(RouterTransaction.with(DisplayPhoneNumberFormatController.newInstance(phoneNumberFormat)));
    }

    public void addPhoneNumberFormat() {
        phoneNumberFormatRouter.pushController(RouterTransaction.with(AddEditPhoneNumberFormatController.newInstance()));
    }

    public void editPhoneNumberFormat(PhoneNumberFormat phoneNumberFormat) {
        phoneNumberFormatRouter.pushController(RouterTransaction.with(AddEditPhoneNumberFormatController.newInstance(phoneNumberFormat)));
    }

    public void cancelEditPhoneNumberFormat() {
        phoneNumberFormatRouter.popCurrentController();
    }

    public void saveEditPhoneNumberFormat() {
        phoneNumberFormatRouter.popToRoot();
    }

    public void deletePhoneNumberFormat() {
        phoneNumberFormatRouter.popCurrentController();
    }
}