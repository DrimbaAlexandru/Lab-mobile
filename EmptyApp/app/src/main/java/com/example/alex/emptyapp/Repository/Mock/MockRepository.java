package com.example.alex.emptyapp.Repository.Mock;

import com.example.alex.emptyapp.Domain.BaseModel;
import com.example.alex.emptyapp.Repository.Interfaces.IRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Alex on 26.11.2017.
 */

public class MockRepository<T extends BaseModel> implements IRepository<T>{

    protected TreeMap< Integer, T > values = new TreeMap< Integer, T >();
    protected Integer Id = 1;

    @Override
    public T getById( int Id )
    {
        return values.get( Id );
    }

    @Override
    public List< T > getAll()
    {
        return new ArrayList< T >( values.values() );
    }

    @Override
    public void add( T elem )
    {
        elem.setId( Id++ );
        values.put( elem.getId(), elem );
    }

    @Override
    public void update( T elem )
    {
        values.put( elem.getId(), elem );
    }

    @Override
    public void remove( T elem )
    {
        values.remove( elem.getId() );
    }
}
