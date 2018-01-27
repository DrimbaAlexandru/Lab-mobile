package com.example.alex.emptyapp.Service;

import android.arch.persistence.room.Update;
import android.util.Pair;

import com.example.alex.emptyapp.Domain.MyTask;
import com.example.alex.emptyapp.Repository.Rest.RestTaskRepository;
import com.example.alex.emptyapp.Repository.Rest.TaskServerResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Alex on 28.12.2017.
 */

public class RestTaskService
{
    RestTaskRepository taskRepo;

    public RestTaskService( RestTaskRepository tR )
    {
        taskRepo = tR;
    }

    TaskServerResponse getTasks( int page, Long lastModified ) throws IOException
    {
        Response< TaskServerResponse > resp = taskRepo.getTasks( lastModified.toString(), page ).execute();

        if( resp.code() == 200 )
        {
            TaskServerResponse response = resp.body();
            try
            {
                response.lastModified = Long.parseLong( resp.headers().get( "Last-Modified" ) );
            }
            catch( Exception e )
            {
                return null;
            }
            return response;
        }
        if( resp.code() == 304 )
        {
            TaskServerResponse response = new TaskServerResponse();
            response.lastModified = lastModified;
        }

        return null;
    }

    boolean deleteTask( int Id ) throws IOException
    {
        Response< Void > response = taskRepo.deleteTask( Id ).execute();
        return ( response.code() == 204 );
    }

}
