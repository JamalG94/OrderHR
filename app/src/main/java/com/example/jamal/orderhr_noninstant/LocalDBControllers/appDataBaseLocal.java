package com.example.jamal.orderhr_noninstant.LocalDBControllers;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.jamal.orderhr_noninstant.Datastructures.BookingWrapper;
import com.example.jamal.orderhr_noninstant.Datastructures.DefunctWrapper;

/**
 * Created by Robin on 6/5/2018.
 */

@Database(entities = {DefunctWrapper.class, BookingWrapper.class},version = 1)
@TypeConverters({LocalDatabaseConverts.class})
public abstract class appDataBaseLocal extends RoomDatabase {
    public abstract DefunctDao defunctDao();
    public abstract BookingDao BookingDao();

    private static appDataBaseLocal INSTANCE;
    static appDataBaseLocal getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (appDataBaseLocal.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            appDataBaseLocal.class, "database-name").build();
                }
            }
        }
        return INSTANCE;
    }
}
