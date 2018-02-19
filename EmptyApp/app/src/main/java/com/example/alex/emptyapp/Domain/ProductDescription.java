package com.example.alex.emptyapp.Domain;

import android.arch.persistence.room.Entity;

/**
 * Created by Alex on 29.01.2018.
 */

@Entity
public class ProductDescription extends BaseModel
{
    private String description;

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }
}
