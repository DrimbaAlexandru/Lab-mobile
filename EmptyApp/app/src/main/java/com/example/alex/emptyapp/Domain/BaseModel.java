package com.example.alex.emptyapp.Domain;

import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

public abstract class BaseModel implements Serializable
{
    @PrimaryKey
    private int Id;

    public int getId() {
        return Id;
    }

    public void setId( int id )
    {
        Id = id;
    }

    @Override
    public boolean equals( Object obj )
    {
        return ( obj instanceof BaseModel ) && ( this.getId() == ( ( BaseModel )obj ).getId() );
    }
}
