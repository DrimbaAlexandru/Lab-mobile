package com.example.alex.emptyapp.Repository.Local;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.util.StringUtil;

import com.example.alex.emptyapp.Domain.Difficulty;
import com.example.alex.emptyapp.Domain.Role;

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

    @TypeConverter
    public static Integer fromDifficulty( Difficulty dif ) {
        if( dif == null )
        {
            return ( null );
        }

        return ( dif.ordinal() );
    }

    @TypeConverter
    public static Difficulty toDifficulty(Integer dif) {
        if( dif == null )
        {
            return ( null );
        }

        return ( Difficulty.values()[ dif ] );
    }

    @TypeConverter
    public static Integer fromRole( Role dif ) {
        if( dif == null )
        {
            return ( null );
        }

        return ( dif.ordinal() );
    }

    @TypeConverter
    public static Role toRole(Integer dif) {
        if( dif == null )
        {
            return ( null );
        }

        return ( Role.values()[ dif ] );
    }
}
