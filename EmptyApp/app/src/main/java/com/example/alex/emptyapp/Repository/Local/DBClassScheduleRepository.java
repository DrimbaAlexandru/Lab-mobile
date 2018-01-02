package com.example.alex.emptyapp.Repository.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.alex.emptyapp.Domain.ClassSchedule;
import com.example.alex.emptyapp.Repository.Interfaces.IClassScheduleRepository;

import java.util.List;

/**
 * Created by Alex on 26.11.2017.
 */

@Dao
public interface DBClassScheduleRepository extends IClassScheduleRepository
{
    @Override
    @Query( "SELECT * FROM ClassSchedule WHERE Id == :Id" )
    ClassSchedule getById( int Id );

    @Override
    @Query( "SELECT * FROM ClassSchedule" )
    List< ClassSchedule > getAll();

    @Override
    @Insert
    void add( ClassSchedule elem );

    @Override
    @Update
    void update( ClassSchedule elem );

    @Override
    @Delete
    void remove( ClassSchedule elem );
}
