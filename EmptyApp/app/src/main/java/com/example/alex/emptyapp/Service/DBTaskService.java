package com.example.alex.emptyapp.Service;

import com.example.alex.emptyapp.Domain.MyTask;
import com.example.alex.emptyapp.Repository.Interfaces.ITaskRepository;
import com.example.alex.emptyapp.Repository.Local.DBStaticsRepository;
import com.example.alex.emptyapp.Repository.Local.DBStatics;
import com.example.alex.emptyapp.Repository.Local.DBTaskRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Alex on 26.11.2017.
 */

public class DBTaskService implements Serializable
{
    DBTaskRepository taskRepo;
    DBStaticsRepository dbStaticsRepo;

    public DBTaskService( DBTaskRepository cR, DBStaticsRepository dbsr )
    {
        taskRepo = cR;
        dbStaticsRepo = dbsr;

        if( dbStaticsRepo.getDBStatics() == null )
        {
            dbStaticsRepo.clear();
            DBStatics dbs = new DBStatics();
            dbStaticsRepo.add( dbs );
        }
    }

    public void resetCache( List< MyTask > newState )
    {
        taskRepo.deleteAll();
        taskRepo.batchInsert( newState );
    }

    public void resetElements( List< MyTask > updatedTasks )
    {
        for( MyTask t : updatedTasks )
        {
            taskRepo.update( t );
        }
    }

    public void insertTask( MyTask task )
    {
        taskRepo.insert( task );
    }

    public void batchInsert( List< MyTask > tasks )
    {
        taskRepo.batchInsert( tasks );
    }

    public void updateElement( MyTask task )
    {
        taskRepo.update( task );
    }

    public List< MyTask > getAll()
    {
        return taskRepo.getAll();
    }

    public MyTask getById( int id )
    {
        return taskRepo.getById( id );
    }

    public void setMaxUpdated( int maxUpdated )
    {
        dbStaticsRepo.setDBStatics( dbStaticsRepo.getDBStatics().setMaxUpdated( maxUpdated ) );
    }

    public long getMaxUpdated()
    {
        return dbStaticsRepo.getDBStatics().getMaxUpdated();
    }

    public void deleteById( int id )
    {
        taskRepo.deleteById( id );
    }
}
