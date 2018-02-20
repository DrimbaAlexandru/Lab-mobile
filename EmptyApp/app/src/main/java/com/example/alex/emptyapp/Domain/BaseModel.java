package com.example.alex.emptyapp.Domain;

import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

public abstract class BaseModel implements Serializable
{
    @PrimaryKey
    @NonNull
    private int id;

    @NonNull
    public int getId()
    {
        return id;
    }

    public void setId( @NonNull int id )
    {
        this.id = id;
    }

    @Override
    public boolean equals( Object obj )
    {
        return ( obj instanceof BaseModel ) && ( this.getId() == ( ( ( BaseModel )obj ).getId() ) );
    }
}
