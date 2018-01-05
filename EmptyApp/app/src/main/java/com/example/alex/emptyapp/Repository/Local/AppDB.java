package com.example.alex.emptyapp.Repository.Local;

import android.arch.persistence.room.*;

import com.example.alex.emptyapp.Domain.Cursa;
import com.example.alex.emptyapp.Domain.Motociclist;
import com.example.alex.emptyapp.Domain.Participare;
import com.example.alex.emptyapp.Domain.User;

/**
 * Created by Alex on 27.12.2017.
 */

@Database( entities = { User.class, Motociclist.class, Cursa.class, Participare.class }, version = 1 )
@android.arch.persistence.room.TypeConverters( { TypeConverters.class } )

public abstract class AppDB extends RoomDatabase
{
    public abstract DBCursaRepository cursaRepository();

    public abstract DBMotociclistRepository motociclistRepository();

    public abstract DBUserRepository userRepository();

    public abstract DBParticipareRepository participareRepository();

    public abstract DBStaticsRepository loggedInUser();

}