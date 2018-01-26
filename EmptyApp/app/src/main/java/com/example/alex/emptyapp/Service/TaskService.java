package com.example.alex.emptyapp.Service;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.StrictMode;

import com.example.alex.emptyapp.Repository.Local.AppDB;
import com.example.alex.emptyapp.Repository.Rest.RestTaskRepository;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alex on 28.12.2017.
 */

public class TaskService
{
    private final static String host = "http://192.168.0.101:63288";
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( host + "/api/" )
                .addConverterFactory( GsonConverterFactory.create( new GsonBuilder().create() ) )
                .build();

        RestTaskRepository restClassRepository = retrofit.create( RestTaskRepository.class );

        rest_srv = new RestTaskService( restClassRepository );

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy( policy );

    }

}
