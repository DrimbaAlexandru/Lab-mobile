package com.example.alex.emptyapp.Repository.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.alex.emptyapp.Domain.ProductDescription;

import java.util.List;

/**
 * Created by Alex on 26.11.2017.
 */

@Dao
public interface DBProductDescriptionRepository
{
    @Query( "SELECT * FROM ProductDescription" )
    List< ProductDescription > getAll();

    @Query( "SELECT * FROM ProductDescription WHERE instr( description, :descr ) > 0 ORDER BY code LIMIT :offset, :count " )
    List< ProductDescription > getPage( int count, int offset, String descr );

    @Query( "DELETE FROM ProductDescription" )
    void deleteAll();

    @Query( "DELETE FROM ProductDescription WHERE code = :code" )
    void deleteById( String code );

    @Query( "SELECT * FROM ProductDescription WHERE code = :code" )
    ProductDescription getById( String code );

    @Insert
    void insert( ProductDescription tasks );

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    void batchInsert( List< ProductDescription > tasks );

    @Update
    void batchUpdate( List< ProductDescription > tasks );

    @Update
    void update( ProductDescription elem );

}
