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

    private int last_page_downloaded = 0;

    private int total_element_count = 0;

    public int getTotal_element_count()
    {
        return total_element_count;
    }

    public void setTotal_element_count( int total_element_count )
    {
        this.total_element_count = total_element_count;
    }

    public int getLast_page_downloaded()
    {
        return last_page_downloaded;
    }

    public void setLast_page_downloaded( int last_page_downloaded )
    {
        this.last_page_downloaded = last_page_downloaded;
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
