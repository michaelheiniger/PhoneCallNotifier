package ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.squareup.moshi.Json;

import java.util.LinkedList;
import java.util.List;

import ch.qscqlmpa.phonecallnotifier.model.PhoneNumberFormat;

@Entity(tableName = "phone_number_format")
public class PhoneNumberFormatPersist {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;

    private String description;

    private String format;

    @Json(name = "is_enabled")
    private Boolean isEnabled = true;

    @Ignore
    public PhoneNumberFormatPersist() {
    }

    public PhoneNumberFormatPersist(Long id, String description, String format, Boolean isEnabled) {
        this.id = id;
        this.description = description;
        this.format = format;
        this.isEnabled = isEnabled;
    }

    @Ignore
    public PhoneNumberFormatPersist(PhoneNumberFormatPersist phoneNumberFormatPersist) {
        this.id = phoneNumberFormatPersist.getId();
        this.description = phoneNumberFormatPersist.getDescription();
        this.format = phoneNumberFormatPersist.getFormat();
        this.isEnabled = phoneNumberFormatPersist.getIsEnabled();
    }

    @Ignore
    public PhoneNumberFormatPersist(String description, String format, Boolean isEnabled) {
        this.description = description;
        this.format = format;
        this.isEnabled = isEnabled;
    }

    @Ignore
    public PhoneNumberFormatPersist(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Long getId() {
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

    public void setId(Long id) {
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
        return "PhoneNumberFormatPersist:\n"
                + "id: " + this.id + "\n"
                + "description: " + this.description + "\n"
                + "format: " + this.format + "\n"
                + "isEnabled: " + this.isEnabled;
    }

    public static List<String> getFormatsAsString(List<PhoneNumberFormatPersist> formats) {
        List<String> formatsAsString = new LinkedList<>();
        for (PhoneNumberFormatPersist format : formats) {
            formatsAsString.add(format.getFormat());
        }
        return formatsAsString;
    }

    public boolean isContentEqual(PhoneNumberFormatPersist otherFormat) {
        if (otherFormat == this) {
            return true;
        }

        return ((this.id == null) ? (otherFormat.getId() == null) : this.id.equals(otherFormat.getId()))
        && ((this.description == null) ? (otherFormat.getDescription() == null) : this.description.equals(otherFormat.getDescription()))
                && ((this.format == null) ? (otherFormat.getFormat() == null) : this.format.equals(otherFormat.getFormat()))
                && ((this.isEnabled == null) ? (otherFormat.getIsEnabled() == null) : this.isEnabled.equals(otherFormat.getIsEnabled())
        );
    }

    public static PhoneNumberFormatPersist convertPnfToPnfPersist(PhoneNumberFormat pnf) {
        return new PhoneNumberFormatPersist(pnf.id(), pnf.description(), pnf.format(), pnf.isEnabled());
    }
}