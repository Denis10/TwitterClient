package com.vodolazskiy.twitterclient.data.db.room;

import android.arch.persistence.room.TypeConverter;
import android.support.annotation.Nullable;

import java.util.Date;

public class DbConverters {

    @TypeConverter
    @Nullable
    public static Date longToDate(@Nullable Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    @Nullable
    public static Long dateToLong(@Nullable Date date) {
        return date == null ? null : date.getTime();
    }
}
