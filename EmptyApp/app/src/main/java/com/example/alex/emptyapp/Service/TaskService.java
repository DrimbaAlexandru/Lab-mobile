package com.example.alex.emptyapp.Service;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.StrictMode;
import android.util.Pair;

import com.example.alex.emptyapp.Domain.MyTask;
import com.example.alex.emptyapp.Repository.Local.AppDB;
import com.example.alex.emptyapp.Repository.Rest.RestTaskRepository;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alex on 28.12.2017.
 */

public class TaskService
{
    private final static String host = "http://192.168.0.100:3000";
    private DBTaskService db_srv;
    private RestTaskService rest_srv;
    private int tasks_downloaded = 0;
    private int tasks_uploaded = 0;

    public boolean isOnline()
    {
        return true;
    }

    public TaskService( Context context )
    {
        AppDB db = Room.databaseBuilder( context,
                                         AppDB.class, "Local-DB" ).allowMainThreadQueries().build();
        db_srv = new DBTaskService( db.taskRepository(), db.DBStaticsRepository() );

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( host + "/" )
                .addConverterFactory( GsonConverterFactory.create( new GsonBuilder().create() ) )
                .build();

        RestTaskRepository restClassRepository = retrofit.create( RestTaskRepository.class );

        rest_srv = new RestTaskService( restClassRepository );

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy( policy );
    }

    public boolean updateLocal()
    {
        List< MyTask > data = rest_srv.getTasks( db_srv.getMaxUpdated() );

        // get new max updated
        long maxUpdated = 0;
        for( MyTask t : data )
        {
            if( t.getUpdated() > maxUpdated )
            {
                maxUpdated = t.getUpdated();
            }
        }

        // save to db
        if( maxUpdated > 0 )
        {
            db_srv.setMaxUpdated( maxUpdated );
        }

        // if any existing tasks are updated, update in the database
        // insert any new tasks
        db_srv.batchInsert( data );
        tasks_downloaded += data.size();
        return data.size() > 0;
    }

    public List< MyTask > getTasks()
    {
        // get the final tasks state
        return db_srv.getAll();
    }

    public UpdateStatus updateTask( MyTask task )
    {
        Pair< MyTask, RemoteUpdateStatus > result = rest_srv.updateTask( task );
        MyTask resTask = result.first;
        RemoteUpdateStatus status = result.second;

        switch( status )
        {
            case CONFLICT:
                tasks_uploaded++;
                return UpdateStatus.CONFLICT;
            case NETWORK_ERROR:
                return UpdateStatus.NETWORK_ERROR;
            case ALREADY_DELETED:
                tasks_uploaded++;
                MyTask oldVersion = db_srv.getById( task.getId() );
                oldVersion.setStatus( "deleted" );
                db_srv.updateElement( oldVersion );
                return UpdateStatus.OK;
            default: // CASE OK
                tasks_uploaded++;
                db_srv.updateElement( resTask );
                return UpdateStatus.OK;
        }
    }

    public int getTasks_uploaded()
    {
        int v = tasks_uploaded;
        tasks_uploaded = 0;
        return v;
    }

    public int getTasks_downloaded()
    {
        int v = tasks_downloaded;
        tasks_downloaded = 0;
        return v;
    }

    public MyTask getTaskById( int Id )
    {
        return db_srv.getById( Id );
    }

    public void deleteTaskLocal( int Id )
    {
        db_srv.deleteById( Id );
    }
}
