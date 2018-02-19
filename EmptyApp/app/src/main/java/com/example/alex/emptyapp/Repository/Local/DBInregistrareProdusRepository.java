package com.example.alex.emptyapp.Repository.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.alex.emptyapp.Domain.InregistrareProdus;
import com.example.alex.emptyapp.Domain.ProductDescription;

import java.util.List;

/**
 * Created by Alex on 26.11.2017.
 */

@Dao
public interface DBInregistrareProdusRepository
{
    @Query( "SELECT * FROM InregistrareProdus" )
    List< InregistrareProdus > getAll();

    @Query( "DELETE FROM InregistrareProdus" )
    void deleteAll();

    @Query( "DELETE FROM InregistrareProdus WHERE code = :code" )
    void deleteById( String code );

    @Query( "SELECT * FROM InregistrareProdus WHERE code = :code" )
    InregistrareProdus getById( String code );

    @Insert
    void insert( InregistrareProdus tasks );

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    void batchInsert( List< InregistrareProdus > tasks );

    @Update
    void batchUpdate( List< InregistrareProdus > tasks );

    @Update
    void update( InregistrareProdus elem );

}
