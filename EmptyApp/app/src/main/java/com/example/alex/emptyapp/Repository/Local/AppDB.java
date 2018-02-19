package com.example.alex.emptyapp.Repository.Local;

import android.arch.persistence.room.*;

import com.example.alex.emptyapp.Domain.InregistrareProdus;
import com.example.alex.emptyapp.Domain.ProductDescription;

/**
 * Created by Alex on 27.12.2017.
 */

@Database( entities = { InregistrareProdus.class, DBStatics.class, ProductDescription.class }, version = 1 )
public abstract class AppDB extends RoomDatabase
{
    public abstract DBProductDescriptionRepository productDescriptionRepository();

    public abstract DBInregistrareProdusRepository inregistrareProdusRepository();

    public abstract DBStaticsRepository DBStaticsRepository();
}
