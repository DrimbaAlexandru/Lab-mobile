package com.awesomeproject.Repository.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.awesomeproject.Domain.Feedback;
import com.awesomeproject.Repository.Interfaces.IFeedbackRepository;

import java.util.List;

/**
 * Created by Alex on 26.12.2017.
 */

@Dao
public interface DBFeedbackRepository extends IFeedbackRepository
{
    @Override
    @Query( "SELECT * FROM Feedback WHERE TrainerId == :trainer_id" )
    List< Feedback > getTrainerFeedback( int trainer_id );

    @Override
    @Query( "SELECT * FROM Feedback WHERE Id == :Id" )
    Feedback getById( int Id );

    @Override
    @Query( "SELECT * FROM Feedback" )
    List< Feedback > getAll();

    @Override
    @Insert
    void add( Feedback elem );

    @Override
    @Update
    void update( Feedback elem );

    @Override
    @Delete
    void remove( Feedback elem );
}
