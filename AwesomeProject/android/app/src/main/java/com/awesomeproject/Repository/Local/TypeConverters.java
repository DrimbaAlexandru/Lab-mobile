package com.awesomeproject.Repository.Local;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by Alex on 27.12.2017.
 */

public class TypeConverters
{
    @TypeConverter
    public static Long fromDate(Date date ) {
        if (date==null) {
            return(null);
        }

        return(date.getTime());
    }

    @TypeConverter
    public static Date toDate(Long millisSinceEpoch) {
        if (millisSinceEpoch==null) {
            return(null);
        }

        return(new Date(millisSinceEpoch));
    }

}
