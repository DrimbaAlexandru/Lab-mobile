package com.example.noonecares.curses.Repository.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.noonecares.curses.Domain.User;
import com.example.noonecares.curses.Repository.Interfaces.IUserRepository;

import java.util.List;

/**
 * Created by Alex on 26.11.2017.
 */

@Dao
public interface DBUserRepository extends IUserRepository
{
    @Override
    @Query( "SELECT * FROM AppUser WHERE Id == :Id" )
    User getById( int Id );

    @Override
    @Query( "SELECT * FROM AppUser" )
    List< User > getAll();

    @Override
    @Insert
    void add( User elem );

    @Override
    @Update
    void update( User elem );

    @Override
    @Delete
    void remove( User elem );
}