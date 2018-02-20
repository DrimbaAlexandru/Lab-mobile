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

    private String token = "";

    private int lastId = 1;

    private long last_created = 0;

    public long getLast_created()
    {
        return last_created;
    }

    public void setLast_created( long last_created )
    {
        this.last_created = last_created;
    }

    public void setToken( String token )
    {
        this.token = token;
    }

    public String getToken()
    {
        return token;
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
}
