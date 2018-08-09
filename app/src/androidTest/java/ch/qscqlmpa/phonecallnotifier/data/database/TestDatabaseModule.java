package ch.qscqlmpa.phonecallnotifier.data.database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@Module
public class TestDatabaseModule {

    @Provides
    @Singleton
    static AppRoomDatabase provideDatabase() {
        return Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppRoomDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    @Named("database_scheduler")
    static Scheduler provideDatabaseScheduler() {
        return Schedulers.io();
    }
}

