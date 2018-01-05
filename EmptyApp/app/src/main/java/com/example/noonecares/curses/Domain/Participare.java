package com.example.noonecares.curses.Domain;

import android.arch.persistence.room.Entity;

/**
 * Created by Alex on 05.01.2018.
 */

@Entity(tableName = "Participare")
public class Participare extends BaseModel
{
    private int cursaId;
    private int motociclistId;

    public int getCursaId()
    {
        return cursaId;
    }

    public int getMotociclistId()
    {
        return motociclistId;
    }

    public void setMotociclistId( int motociclistId )
    {
        this.motociclistId = motociclistId;
    }

    public void setCursaId( int cursaId )
    {
        this.cursaId = cursaId;
    }
}
