package com.example.alex.emptyapp.Repository.Local;

import android.arch.persistence.room.*;

import com.example.alex.emptyapp.Domain.Mesaj;

/**
 * Created by Alex on 27.12.2017.
 */

@Database( entities = { DBStatics.class, Mesaj.class }, version = 1 )
public abstract class AppDB extends RoomDatabase
{

    public abstract DBMesajeRepository mesajeRepository();

    public abstract DBStaticsRepository DBStaticsRepository();
}
