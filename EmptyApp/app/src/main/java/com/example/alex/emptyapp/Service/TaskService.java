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

    public List<MyTask> getTasks() {
        List<MyTask> data = rest_srv.getTasks(db_srv.getMaxUpdated());

        // get new max updated
        int maxUpdated = 0;
        for(MyTask t : data) {
            if(t.getUpdated() > maxUpdated)
                maxUpdated = t.getUpdated();
        }

        // save to db
        db_srv.setMaxUpdated(maxUpdated);

        // THIS CODE IS SHIT FAIR WARNING

        // if any existing tasks are updated, update in the database
        // insert any new tasks
        db_srv.batchInsert(data);

        // get the final tasks state
        return db_srv.getAll();
    }

    public UpdateStatus updateTask(MyTask task) {

        Pair<MyTask, RemoteUpdateStatus> result = rest_srv.updateTask(task);
        MyTask resTask = result.first;
        RemoteUpdateStatus status = result.second;

        switch(status) {
            case CONFLICT:
                return UpdateStatus.CONFLICT;
            case NETWORK_ERROR:
                return UpdateStatus.NETWORK_ERROR;
            case ALREADY_DELETED:
                MyTask oldVersion = db_srv.getById(task.getId());
                oldVersion.setStatus("deleted");
                db_srv.updateElement(oldVersion);
                return UpdateStatus.OK;
            default: // CASE OK
                db_srv.updateElement(resTask);
                return UpdateStatus.OK;
        }
    }
}
