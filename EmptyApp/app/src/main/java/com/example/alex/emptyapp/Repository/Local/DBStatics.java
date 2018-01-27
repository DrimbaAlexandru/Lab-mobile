package com.example.alex.emptyapp.Repository.Local;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Alex on 27.12.2017.
 */

@Entity(tableName = "DBStatics")
public class DBStatics
{
    @PrimaryKey
    public int dummy_key = 1;

    private int lastId = 1;
    private int nr_elemente = 0;

    private long lastModified = 0;

    public long getLastModified() {
        return lastModified;
    }

    public int getLastId()
    {
        return lastId;
    }

    public void setLastId( int lastId )
    {
        this.lastId = lastId;
    }

    public void incrementId()
    {
        lastId++;
    }

    public void setLastModified( long lastModified )
    {
        this.lastModified = lastModified;
    }

    public void setNr_elemente( int nr_elemente )
    {
        this.nr_elemente = nr_elemente;
    }

    public int getNr_elemente()
    {
        return nr_elemente;
    }
}
