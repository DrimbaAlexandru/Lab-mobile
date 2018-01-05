package com.example.noonecares.curses.Repository.Interfaces;

import com.example.noonecares.curses.Domain.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alex on 26.11.2017.
 */

public interface IRepository<T extends BaseModel> extends Serializable
{
    T getById( int Id );
    List< T > getAll();
    void add( T elem );
    void update( T elem );
    void remove( T elem );
}
