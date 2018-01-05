package com.example.alex.emptyapp.Repository.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.alex.emptyapp.Domain.Motociclist;
import com.example.alex.emptyapp.Repository.Interfaces.IMotociclistRepository;

import java.util.List;

/**
 * Created by Alex on 26.11.2017.
 */

@Dao
public interface DBMotociclistRepository extends IMotociclistRepository
{
    @Override
    @Query( "SELECT * FROM Motociclist WHERE Id == :Id" )
    Motociclist getById( int Id );

    @Override
    @Query( "SELECT * FROM Motociclist" )
    List< Motociclist > getAll();

    @Override
    @Insert
    void add( Motociclist elem );

    @Override
    @Update
    void update( Motociclist elem );

    @Override
    @Delete
    void remove( Motociclist elem );
}
