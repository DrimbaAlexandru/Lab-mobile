package com.example.alex.emptyapp.Domain;

import android.arch.persistence.room.Entity;

/**
 * Created by Alex on 29.01.2018.
 */

@Entity
public class InregistrareProdus extends BaseModel
{
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
}
