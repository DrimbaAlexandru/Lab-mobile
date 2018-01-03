package com.awesomeproject.Repository.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.awesomeproject.Domain.GymClass;
import com.awesomeproject.Repository.Interfaces.IClassRepository;

import java.util.List;

/**
 * Created by Alex on 26.11.2017.
 */

@Dao
public interface DBClassRepository extends IClassRepository
{
    @Override
    @Query( "SELECT * FROM GymClass WHERE Id == :Id" )
    GymClass getById( int Id );

    @Override
    @Query( "SELECT * FROM GymClass" )
    List<GymClass> getAll();

    @Override
    @Insert
    void add( GymClass elem );

    @Override
    @Update
    void update( GymClass elem );

    @Override
    @Delete
    void remove( GymClass elem );
}
