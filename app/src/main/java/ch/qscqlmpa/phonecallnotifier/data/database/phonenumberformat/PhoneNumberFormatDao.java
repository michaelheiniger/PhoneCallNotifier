package ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface PhoneNumberFormatDao {

    @Insert
    void insertPhoneNumberFormat(PhoneNumberFormat phoneNumberFormat);

    @Update
    void updatePhoneNumberFormat(PhoneNumberFormat phoneNumberFormat);

    @Delete
    void deletePhoneNumberFormat(PhoneNumberFormat phoneNumberFormat);

    @Query("SELECT * FROM phone_number_format ORDER BY id ASC")
    Flowable<List<PhoneNumberFormat>> getAllPhoneNumberFormats();

    @Query("SELECT * FROM phone_number_format WHERE isEnabled=1 ORDER BY id ASC")
    Flowable<List<PhoneNumberFormat>> getEnabledPhoneNumberFormats();

    @Query("SELECT * FROM phone_number_format WHERE id=:id")
    Single<PhoneNumberFormat> getPhoneNumberFormat(int id);

}
