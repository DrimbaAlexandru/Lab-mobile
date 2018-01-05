package com.example.noonecares.curses.Repository.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.noonecares.curses.Domain.Participare;
import com.example.noonecares.curses.Repository.Interfaces.IParticipareRepository;

import java.util.List;

/**
 * Created by Alex on 26.11.2017.
 */

@Dao
public interface DBParticipareRepository extends IParticipareRepository
{
    @Override
    @Query( "SELECT * FROM Participare WHERE Id == :Id" )
    Participare getById( int Id );

    @Override
    @Query( "SELECT * FROM Participare WHERE cursaId == :cId" )
    List< Participare > getByCursaId( int cId );

    @Override
    @Query( "SELECT * FROM Participare" )
    List< Participare > getAll();

    @Override
    @Insert
    void add( Participare elem );

    @Override
    @Update
    void update( Participare elem );

    @Override
    @Delete
    void remove( Participare elem );
}
