package ch.qscqlmpa.phonecallnotifier.phonenumberformat.phonenumberformatlist;

import android.support.v7.util.DiffUtil;

import java.util.List;

import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatPersist;
import ch.qscqlmpa.phonecallnotifier.model.PhoneNumberFormat;

public class PhoneNumberFormatsDiffCallback extends DiffUtil.Callback {

    private final List<PhoneNumberFormat> oldList;
    private final List<PhoneNumberFormat> newList;

    public PhoneNumberFormatsDiffCallback(List<PhoneNumberFormat> oldList,
                                          List<PhoneNumberFormat> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).id() == newList.get(newItemPosition).id();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        PhoneNumberFormat oldItem = oldList.get(oldItemPosition);
        PhoneNumberFormat newItem = newList.get(newItemPosition);

        return oldItem.equals(newItem);
    }
}
