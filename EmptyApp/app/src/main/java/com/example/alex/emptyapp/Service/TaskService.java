package com.example.alex.emptyapp.Service;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.util.Pair;

import com.example.alex.emptyapp.Domain.MyTask;
import com.example.alex.emptyapp.Repository.Local.AppDB;
import com.example.alex.emptyapp.Repository.Rest.RestTaskRepository;
import com.example.alex.emptyapp.R;
import com.example.alex.emptyapp.Repository.Rest.TaskServerResponse;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alex on 28.12.2017.
 */

public class TaskService
{
    //private String host = "http://192.168.0.100:3000";
    private DBTaskService db_srv;
    private RestTaskService rest_srv;

    public boolean isOnline()
    {
        return true;
    }

    public TaskService( Context context )
    {
        AppDB db = Room.databaseBuilder( context,
                                         AppDB.class, "Local-DB" ).allowMainThreadQueries().build();
        db_srv = new DBTaskService( db.taskRepository(), db.DBStaticsRepository() );

        String host = context.getString( R.string.drimba_host );

        Log.d( "TASK-SERVICE", "HOST IS: " + host );

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( host + "/" )
                .addConverterFactory( GsonConverterFactory.create( new GsonBuilder().create() ) )
                .build();

        RestTaskRepository restClassRepository = retrofit.create( RestTaskRepository.class );

        rest_srv = new RestTaskService( restClassRepository );

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy( policy );
    }

    public boolean downloadPage( int page )
    {
        try
        {
            long lastModified = db_srv.getLastModified();
            TaskServerResponse response = rest_srv.getTasks( page, lastModified );
            if( response != null && response.lastModified > lastModified )
            {
                db_srv.setLastModified( response.lastModified );
                db_srv.setElemsCount( response.count );
                db_srv.deletePage( page );
                for( MyTask t : response.tasks )
                {
                    t.setPage( response.page );
                    db_srv.insertTask( t );
                }
                return true;
            }
            return response != null;
        }
        catch( IOException e )
        {
            return false;
        }
    }

    public boolean updateLocal()
    {
        boolean success = true;
        for( int page = 0; page < ( db_srv.getElemsCount() + 9 ) / 10; page++ )
        {
            success = success && downloadPage( page );
        }
        return success;
    }

    public List< MyTask > getPage( int page )
    {
        return db_srv.getByPage( page );
    }

    public boolean deleteTask( int Id )
    {
        try
        {
            if( rest_srv.deleteTask( Id ) )
            {
                db_srv.deleteById( Id );
                return true;
            }
            return false;
        }
        catch( IOException e )
        {
            return false;
        }
    }

    public MyTask getTaskById( int Id )
    {
        return db_srv.getById( Id );
    }

}
