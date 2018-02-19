package com.example.alex.emptyapp.Domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Alex on 29.01.2018.
 */

@Entity
public class InregistrareProdus
{
    @PrimaryKey
    private int Id;
    private String code;
    private int quantity;
    private String location;

    public int getQuantity()
    {
        return quantity;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation( String location )
    {
        this.location = location;
    }

    public void setQuantity( int quantity )
    {
        this.quantity = quantity;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode( String code )
    {
        this.code = code;
    }

    public int getId()
    {
        return Id;
    }

    public void setId( int id )
    {
        Id = id;
    }
}
