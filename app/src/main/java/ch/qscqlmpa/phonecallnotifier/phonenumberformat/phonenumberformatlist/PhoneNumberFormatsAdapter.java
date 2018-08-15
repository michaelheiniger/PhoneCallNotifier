package ch.qscqlmpa.phonecallnotifier.phonenumberformat.phonenumberformatlist;


import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.qscqlmpa.phonecallnotifier.R;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatPersist;
import ch.qscqlmpa.phonecallnotifier.model.PhoneNumberFormat;

class PhoneNumberFormatsAdapter extends RecyclerView.Adapter<PhoneNumberFormatsAdapter.PhoneNumberFormatsViewHolder> {

    private final PhoneNumberFormatClickedListener listener;
    private final List<PhoneNumberFormat> data = new ArrayList<>();


    PhoneNumberFormatsAdapter(PhoneNumberFormatClickedListener listener) {
        this.listener = listener;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public PhoneNumberFormatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.phonenumberformat_row, parent, false);
        return new PhoneNumberFormatsViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(PhoneNumberFormatsViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).id();
    }

    void setData(List<PhoneNumberFormat> phoneNumberFormatList) {
        if (phoneNumberFormatList != null) {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new PhoneNumberFormatsDiffCallback(data, phoneNumberFormatList));
            data.clear();
            data.addAll(phoneNumberFormatList);
            diffResult.dispatchUpdatesTo(this);
        } else {
            data.clear();
            notifyDataSetChanged();
        }
    }

    static final class PhoneNumberFormatsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.pnfr_phone_number_format_description_tv)
        TextView formatDescriptionTv;
        @BindView(R.id.pnfr_phone_number_format_tv)
        TextView formatTv;
        @BindView(R.id.pnfr_is_phone_number_format_enabled_ckb)
        CheckBox formatEnabledCkb;

        private PhoneNumberFormat format;

        PhoneNumberFormatsViewHolder(View itemView, PhoneNumberFormatClickedListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (format != null) {
                    listener.onPhoneNumberFormatClicked(format);
                }
            });
        }

        void bind(PhoneNumberFormat format) {
            this.format = format;
            formatDescriptionTv.setText(format.description());
            formatTv.setText(format.format());
            formatEnabledCkb.setChecked(format.isEnabled());
            formatEnabledCkb.setText(format.isEnabled() ? R.string.format_enabled : R.string.format_disabled);
        }
    }

    interface PhoneNumberFormatClickedListener {
        void onPhoneNumberFormatClicked(PhoneNumberFormat format);
    }
}
