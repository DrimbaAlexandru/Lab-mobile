package com.example.alex.emptyapp.Domain;

import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

public abstract class BaseModel implements Serializable
{
    @PrimaryKey
    @NonNull
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode( String code )
    {
        this.code = code;
    }

    @Override
    public boolean equals( Object obj )
    {
        return ( obj instanceof BaseModel ) && ( this.getCode().equals( ( ( BaseModel )obj ).getCode() ) );
    }
}
