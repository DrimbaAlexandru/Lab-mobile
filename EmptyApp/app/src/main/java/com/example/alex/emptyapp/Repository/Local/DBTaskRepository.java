package com.example.alex.emptyapp.Repository.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.alex.emptyapp.Domain.MyTask;
import com.example.alex.emptyapp.Repository.Interfaces.ITaskRepository;

import java.util.List;

/**
 * Created by Alex on 26.11.2017.
 */

@Dao
public interface DBTaskRepository
{
    @Query( "SELECT * FROM MyTask" )
    List< MyTask > getAll();

    @Query( "DELETE FROM MyTask" )
    void deleteAll();

    @Query( "DELETE FROM MyTask WHERE ID = :id" )
    void deleteById( int id );

    @Query( "SELECT * FROM MyTask WHERE ID = :id" )
    MyTask getById( int id );

    @Insert
    void insert( MyTask tasks );

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    void batchInsert( List< MyTask > tasks );

    @Update
    void batchUpdate( List< MyTask > tasks );

    @Update
    void update( MyTask elem );

}
