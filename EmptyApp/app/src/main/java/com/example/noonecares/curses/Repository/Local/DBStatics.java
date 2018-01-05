package com.example.noonecares.curses.Repository.Local;

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

    private int loggedUserID = 0;
    private int lastId = 1;

    public int getLoggedUserID()
    {
        return loggedUserID;
    }

    public void setLoggedUserID( int loggedUserID )
    {
        this.loggedUserID = loggedUserID;
    }

    public int getLastId()
    {
        return lastId;
    }

    public void setLastId( int lastId )
    {
        this.lastId = lastId;
    }

    public DBStatics incrementId()
    {
        lastId++;
        return this;
    }
}
