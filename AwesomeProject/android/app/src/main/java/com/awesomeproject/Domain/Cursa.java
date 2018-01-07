package com.awesomeproject.Domain;

import android.arch.persistence.room.Entity;

@Entity(tableName = "Cursa")
public  class Cursa extends BaseModel
{
    private int capacitate;
    private int loc_ramas;
    private String nume;

    public int getCapacitate()
    {
        return capacitate;
    }

    public int getLoc_ramas()
    {
        return loc_ramas;
    }

    public void setCapacitate( int capacitate )
    {
        this.capacitate = capacitate;
    }

    public void setLoc_ramas( int loc_ramas )
    {
        this.loc_ramas = loc_ramas;
    }

    public String getNume()
    {
        return nume;
    }

    public void setNume( String nume )
    {
        this.nume = nume;
    }
}

