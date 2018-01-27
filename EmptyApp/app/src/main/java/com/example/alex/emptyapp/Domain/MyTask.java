package com.example.alex.emptyapp.Domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Alex on 26.01.2018.
 */
@Entity(tableName = "MyTask")
public class MyTask extends BaseModel
{
    private String text;
    private String status;
    private long updated;
    private int version = 1;

    public long getUpdated()
    {
        return updated;
    }

    public int getVersion()
    {
        return version;
    }

    public String getStatus()
    {
        return status;
    }

    public String getText()
    {
        return text;
    }

    public void setStatus( String status )
    {
        this.status = status;
    }

    public void setText( String text )
    {
        this.text = text;
    }

    public void setUpdated( long updated )
    {
        this.updated = updated;
    }

    public void setVersion( int version )
    {
        this.version = version;
    }
}
