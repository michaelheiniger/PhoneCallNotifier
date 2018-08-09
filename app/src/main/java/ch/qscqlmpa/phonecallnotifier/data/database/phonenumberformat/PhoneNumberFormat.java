package ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.LinkedList;
import java.util.List;

@Entity(tableName = "phone_number_format")
public class PhoneNumberFormat implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Integer id;

    private String description;

    private String format;

    @Json(name = "is_enabled")
    private Boolean isEnabled = true;

    @Ignore
    public PhoneNumberFormat() {
    }

    public PhoneNumberFormat(Integer id, String description, String format, Boolean isEnabled) {
        this.id = id;
        this.description = description;
        this.format = format;
        this.isEnabled = isEnabled;
    }

    @Ignore
    public PhoneNumberFormat(PhoneNumberFormat phoneNumberFormat) {
        this.id = phoneNumberFormat.getId();
        this.description = phoneNumberFormat.getDescription();
        this.format = phoneNumberFormat.getFormat();
        this.isEnabled = phoneNumberFormat.getIsEnabled();
    }

    @Ignore
    public PhoneNumberFormat(String description, String format, Boolean isEnabled) {
        this.description = description;
        this.format = format;
        this.isEnabled = isEnabled;
    }

    @Ignore
    public PhoneNumberFormat(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Ignore
    protected PhoneNumberFormat(Parcel in) {
        id = in.readInt();
        description = in.readString();
        format = in.readString();
        isEnabled = in.readByte() != 0;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public String getFormat() {
        return format == null ? "" : format;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setIsEnabled(Boolean value) {
        isEnabled = value;
    }

    @Override
    public String toString() {
        return "PhoneNumberFormat:\n"
                + "id: " + this.id + "\n"
                + "description: " + this.description + "\n"
                + "format: " + this.format + "\n"
                + "isEnabled: " + this.isEnabled;
    }

    public static List<String> getFormatsAsString(List<PhoneNumberFormat> formats) {
        List<String> formatsAsString = new LinkedList<>();
        for (PhoneNumberFormat format : formats) {
            formatsAsString.add(format.getFormat());
        }
        return formatsAsString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(description);
        parcel.writeString(format);
        parcel.writeByte((byte) (isEnabled ? 1 : 0));
    }

    public static final Creator<PhoneNumberFormat> CREATOR = new Creator<PhoneNumberFormat>() {
        @Override
        public PhoneNumberFormat createFromParcel(Parcel in) {
            return new PhoneNumberFormat(in);
        }

        @Override
        public PhoneNumberFormat[] newArray(int size) {
            return new PhoneNumberFormat[size];
        }
    };

    public boolean isContentEqual(PhoneNumberFormat otherFormat) {
        if (otherFormat == this) {
            return true;
        }

        return ((this.id == null) ? (otherFormat.getId() == null) : this.id.equals(otherFormat.getId()))
        && ((this.description == null) ? (otherFormat.getDescription() == null) : this.description.equals(otherFormat.getDescription()))
                && ((this.format == null) ? (otherFormat.getFormat() == null) : this.format.equals(otherFormat.getFormat()))
                && ((this.isEnabled == null) ? (otherFormat.getIsEnabled() == null) : this.isEnabled.equals(otherFormat.getIsEnabled())
        );

    }
}