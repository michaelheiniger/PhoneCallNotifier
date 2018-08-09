package ch.qscqlmpa.phonecallnotifier.views.common;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import javax.inject.Inject;

public class ViewMvpFactory {

    private final LayoutInflater mLayoutInflater;

    @Inject
    public ViewMvpFactory(LayoutInflater layoutInflater) {
        mLayoutInflater = layoutInflater;
    }

    public <T extends ViewMvp> T newInstance(Class<T> mvpViewClass, @Nullable ViewGroup container) {
        ViewMvp viewMvp = null;

//        if (mvpViewClass == MainViewMvp.class) {
//            viewMvp = new MainViewMvpImpl(mLayoutInflater, container);
//        }
//        else if (mvpViewClass == AddEditPhoneNumberFormatViewMvp.class) {
//            viewMvp = new AddEditPhoneNumberFormatViewMvpImpl(mLayoutInflater, container);
//        }
//        else if(mvpViewClass == DisplayPhoneNumberFormatViewMvp.class) {
//            viewMvp = new DisplayPhoneNumberFormatViewMvpImpl(mLayoutInflater, container);
//        }
//        else if (mvpViewClass == PhoneNumberFormatsListViewMvp.class) {
//            viewMvp = new PhoneNumberFormatsListViewMvpImpl(mLayoutInflater, container);
//        }
//        else {
//            throw new IllegalArgumentException("Unsupported MVP view class " + mvpViewClass);
//        }

        return (T) viewMvp;
    }
}
