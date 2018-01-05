package com.example.alex.emptyapp.Domain;

/**
 * Created by Alex on 05.01.2018.
 */

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

    public Participare setCursaId( int cursaId )
    {
        this.cursaId = cursaId;
        return this;
    }

    public Participare setMotociclistId( int motociclistId )
    {
        this.motociclistId = motociclistId;
        return this;
    }
}
