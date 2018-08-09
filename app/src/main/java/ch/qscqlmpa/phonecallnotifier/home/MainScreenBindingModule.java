package ch.qscqlmpa.phonecallnotifier.home;

import com.bluelinelabs.conductor.Controller;

import ch.qscqlmpa.phonecallnotifier.phonenumberformat.EmptyLayoutController;
import ch.qscqlmpa.phonecallnotifier.phonenumberformat.addeditphonenumberformat.AddEditPhoneNumberFormatComponent;
import ch.qscqlmpa.phonecallnotifier.phonenumberformat.addeditphonenumberformat.AddEditPhoneNumberFormatController;
import ch.qscqlmpa.phonecallnotifier.di.ControllerKey;
import ch.qscqlmpa.phonecallnotifier.phonenumberformat.displayphonenumberformat.DisplayPhoneNumberFormatComponent;
import ch.qscqlmpa.phonecallnotifier.phonenumberformat.displayphonenumberformat.DisplayPhoneNumberFormatController;
import ch.qscqlmpa.phonecallnotifier.phonenumberformat.PhoneNumberFormatComponent;
import ch.qscqlmpa.phonecallnotifier.phonenumberformat.PhoneNumberFormatController;
import ch.qscqlmpa.phonecallnotifier.phonenumberformat.phonenumberformatlist.EmptyLayoutComponent;
import ch.qscqlmpa.phonecallnotifier.phonenumberformat.phonenumberformatlist.PhoneNumberFormatListComponent;
import ch.qscqlmpa.phonecallnotifier.phonenumberformat.phonenumberformatlist.PhoneNumberFormatListController;
import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;


// Part of the Activity scope
@Module(subcomponents = {
        PhoneNumberFormatComponent.class,
        DisplayPhoneNumberFormatComponent.class,
        AddEditPhoneNumberFormatComponent.class,
        EmptyLayoutComponent.class,
        PhoneNumberFormatListComponent.class
})
public abstract class MainScreenBindingModule {

    @Binds
    @IntoMap
    @ControllerKey(PhoneNumberFormatController.class)
    abstract AndroidInjector.Factory<? extends Controller> bindPhoneNumberFormatInjector(PhoneNumberFormatComponent.Builder builder);

    @Binds
    @IntoMap
    @ControllerKey(DisplayPhoneNumberFormatController.class)
    abstract AndroidInjector.Factory<? extends Controller> bindDisplayPhoneNumberFormatInjector(DisplayPhoneNumberFormatComponent.Builder builder);

    @Binds
    @IntoMap
    @ControllerKey(AddEditPhoneNumberFormatController.class)
    abstract AndroidInjector.Factory<? extends Controller> bindAddEditPhoneNumberFormatInjector(AddEditPhoneNumberFormatComponent.Builder builder);

    @Binds
    @IntoMap
    @ControllerKey(PhoneNumberFormatListController.class)
    abstract AndroidInjector.Factory<? extends Controller> bindPhoneNumberFormatListInjector(PhoneNumberFormatListComponent.Builder builder);

    @Binds
    @IntoMap
    @ControllerKey(EmptyLayoutController.class)
    abstract AndroidInjector.Factory<? extends Controller> bindTestInjector(EmptyLayoutComponent.Builder builder);

}
