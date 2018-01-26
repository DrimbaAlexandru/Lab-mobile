package com.example.alex.emptyapp.Service;

import com.example.alex.emptyapp.Repository.Interfaces.ITaskRepository;
import com.example.alex.emptyapp.Repository.Local.DBStaticsRepository;
import com.example.alex.emptyapp.Repository.Local.DBStatics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Alex on 26.11.2017.
 */

public class DBTaskService implements Serializable
{
    ITaskRepository taskRepo;
    DBStaticsRepository dbStaticsRepo;

    public DBTaskService( ITaskRepository cR, DBStaticsRepository liu )
    {
        taskRepo = cR;
        dbStaticsRepo = liu;

        if( dbStaticsRepo.getDBStatics() == null )
        {
            dbStaticsRepo.clear();
            DBStatics dbs = new DBStatics();
            dbStaticsRepo.add( dbs );
        }
    }

}
