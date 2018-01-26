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

    private int maxUpdated = 0;

    public int getMaxUpdated() {
        return maxUpdated;
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

    public DBStatics setMaxUpdated(int maxUpdated) {
        this.maxUpdated = maxUpdated;
        return this;
    }
}
