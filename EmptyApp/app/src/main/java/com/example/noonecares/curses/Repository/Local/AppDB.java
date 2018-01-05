package com.example.noonecares.curses.Repository.Local;

import android.arch.persistence.room.*;

import com.example.noonecares.curses.Domain.Cursa;
import com.example.noonecares.curses.Domain.Motociclist;
import com.example.noonecares.curses.Domain.Participare;
import com.example.noonecares.curses.Domain.User;

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