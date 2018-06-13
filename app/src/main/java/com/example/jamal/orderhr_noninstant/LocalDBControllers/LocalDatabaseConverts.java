package com.example.jamal.orderhr_noninstant.LocalDBControllers;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Robin on 6/5/2018.
 */

//These static methods describe how to convert certain fields not recognized by the ROOM system into
//fields that it does recognize.
public class LocalDatabaseConverts {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}