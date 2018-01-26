package com.example.alex.emptyapp.Repository.Local;

import android.arch.persistence.room.*;

import com.example.alex.emptyapp.Domain.MyTask;

/**
 * Created by Alex on 27.12.2017.
 */

@Database( entities = { MyTask.class, DBStatics.class }, version = 1 )
public abstract class AppDB extends RoomDatabase
{
    public abstract DBTaskRepository taskRepository();

    public abstract DBStaticsRepository DBStaticsRepository();

}