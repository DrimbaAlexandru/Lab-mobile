package com.example.alex.emptyapp.Repository.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.alex.emptyapp.Domain.Mesaj;

import java.util.List;

/**
 * Created by Alex on 26.11.2017.
 */

@Dao
public interface DBMesajeRepository
{
    @Query( "SELECT * FROM Mesaje ORDER BY created DESC" )
    List< Mesaj > getAll();

    @Query( "DELETE FROM Mesaje" )
    void deleteAll();

    @Query( "DELETE FROM Mesaje WHERE id = :id" )
    void deleteById( int id );

    @Query( "SELECT * FROM Mesaje WHERE id = :id" )
    Mesaj getById( int id );

    @Insert
    void insert( Mesaj mesaj );

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    void batchInsert( List< Mesaj > mesaje );

    @Update
    void batchUpdate( List< Mesaj > mesaje );

    @Update
    void update( Mesaj elem );

}
