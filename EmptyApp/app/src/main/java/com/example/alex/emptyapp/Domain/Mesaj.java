package com.example.alex.emptyapp.Domain;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

/**
 * Created by Alex on 20.02.2018.
 */
@Entity(tableName = "Mesaje")
public class Mesaj extends BaseModel
{
    private String text;
    private long created;
    private String username;

    public long getCreated()
    {
        return created;
    }

    public String getText()
    {
        return text;
    }

    public String getUsername()
    {
        return username;
    }

    public void setCreated( long created )
    {
        this.created = created;
    }

    public void setText( String text )
    {
        this.text = text;
    }

    public void setUsername( String username )
    {
        this.username = username;
    }
}
