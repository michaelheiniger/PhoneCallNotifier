package ch.qscqlmpa.phonecallnotifier.data.database;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@Module
public abstract class DatabaseModule {

    @Provides
    @Singleton
    static AppRoomDatabase provideDatabase(Context context) {
        return Room.databaseBuilder(context,
                AppRoomDatabase.class, "app_database")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    @Named("database_scheduler")
    static Scheduler provideDatabaseScheduler() {
        return Schedulers.io();
    }
}
