package com.example.alex.emptyapp.Repository.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import android.arch.persistence.room.Update;

/**
 * Created by Alex on 27.12.2017.
 */

@Dao
public interface DBStaticsRepository
{
    @Query( "Select * from DBStatics LIMIT 1" )
    public DBStatics getDBStatics();

    @Update
    public void setDBStatics( DBStatics DBStatics );

    @Query( "Delete from DBStatics" )
    public void clear();

    @Insert
    public void add( DBStatics dbStatics );
}
