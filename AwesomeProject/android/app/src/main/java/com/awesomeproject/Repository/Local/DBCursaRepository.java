package com.awesomeproject.Repository.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.awesomeproject.Domain.Cursa;
import com.awesomeproject.Repository.Interfaces.ICursaRepository;

import java.util.List;

/**
 * Created by Alex on 26.11.2017.
 */

@Dao
public interface DBCursaRepository extends ICursaRepository
{
    @Override
    @Query( "SELECT * FROM Cursa WHERE Id == :Id" )
    Cursa getById( int Id );

    @Override
    @Query( "SELECT * FROM Cursa" )
    List<Cursa> getAll();

    @Override
    @Insert
    void add( Cursa elem );

    @Override
    @Update
    void update( Cursa elem );

    @Override
    @Delete
    void remove( Cursa elem );
}
