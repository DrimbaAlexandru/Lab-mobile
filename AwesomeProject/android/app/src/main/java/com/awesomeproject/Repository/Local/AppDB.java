package com.awesomeproject.Repository.Local;

import android.arch.persistence.room.*;

import com.awesomeproject.Domain.Cursa;
import com.awesomeproject.Domain.Motociclist;
import com.awesomeproject.Domain.Participare;
import com.awesomeproject.Domain.User;

/**
 * Created by Alex on 27.12.2017.
 */

@Database( entities = { User.class, Motociclist.class, Cursa.class, Participare.class, DBStatics.class }, version = 1 )
@android.arch.persistence.room.TypeConverters( { TypeConverters.class } )

public abstract class AppDB extends RoomDatabase
{
    public abstract DBCursaRepository cursaRepository();

    public abstract DBMotociclistRepository motociclistRepository();

    public abstract DBUserRepository userRepository();

    public abstract DBParticipareRepository participareRepository();

    public abstract DBStaticsRepository loggedInUser();

}