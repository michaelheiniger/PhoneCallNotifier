package ch.qscqlmpa.phonecallnotifier.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormatDao;
import ch.qscqlmpa.phonecallnotifier.data.database.phonenumberformat.PhoneNumberFormat;

@Database(entities = {PhoneNumberFormat.class}, version = 3, exportSchema = false)
public abstract class AppRoomDatabase extends RoomDatabase {

    public abstract PhoneNumberFormatDao phoneNumberFormatRegularDao();

}
