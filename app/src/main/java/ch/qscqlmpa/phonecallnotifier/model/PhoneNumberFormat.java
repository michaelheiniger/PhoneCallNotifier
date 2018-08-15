package ch.qscqlmpa.phonecallnotifier.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;

import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatPersist;

@AutoValue
public abstract class PhoneNumberFormat implements Parcelable {

    public static Builder builder() {
        return new AutoValue_PhoneNumberFormat.Builder()
                .setIsEnabled(true);
    }

    @Nullable
    public abstract Long id();

    public abstract String description();

    public abstract String format();

//    @Json(name = "is_enabled")
//    private Boolean isEnabled = true;

    public abstract Boolean isEnabled();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(Long id);
        public abstract Builder setDescription(String description);
        public abstract Builder setFormat(String format);
        public abstract Builder setIsEnabled(Boolean isEnabled);
        public abstract PhoneNumberFormat build();
    }


    public static PhoneNumberFormat convertPnfPersistToPnf(PhoneNumberFormatPersist pnfPersist) {
        return PhoneNumberFormat.builder()
                .setId(pnfPersist.getId())
                .setDescription(pnfPersist.getDescription())
                .setFormat(pnfPersist.getFormat())
                .setIsEnabled(pnfPersist.getIsEnabled())
                .build();
    }

    public static List<PhoneNumberFormat> convertPnfPersistListIntoPnfList(List<PhoneNumberFormatPersist> pnfPersistList) {
        List<PhoneNumberFormat> pnfList = new LinkedList<>();
        for (PhoneNumberFormatPersist pnfPersist : pnfPersistList) {
            pnfList.add(convertPnfPersistToPnf(pnfPersist));
        }
        return pnfList;
    }

    public static List<String> getFormatsAsString(List<PhoneNumberFormat> formats) {
        List<String> formatsAsString = new LinkedList<>();
        for (PhoneNumberFormat format : formats) {
            formatsAsString.add(format.format());
        }
        return formatsAsString;
    }
}