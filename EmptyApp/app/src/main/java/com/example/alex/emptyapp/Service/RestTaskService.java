package com.example.alex.emptyapp.Service;

import android.arch.persistence.room.Update;
import android.util.Pair;

import com.example.alex.emptyapp.Domain.MyTask;
import com.example.alex.emptyapp.Repository.Rest.RestTaskRepository;

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

    List<MyTask> getTasks(Long lastModified) {
        try {
            Response<List<MyTask>> resp = taskRepo.getTasks(lastModified).execute();

            if(resp.code() == 200)
                return resp.body();

        }catch(IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    Pair<MyTask, RemoteUpdateStatus> updateTask(MyTask task) {

        try {
            Response<MyTask> resp = taskRepo.updateTask(task.getId(), task).execute();

            if(resp.code() ==  412) {
                return new Pair<>(null, RemoteUpdateStatus.ALREADY_DELETED);
            }

            if(resp.code() == 409) {
                return new Pair<>(null, RemoteUpdateStatus.CONFLICT);
            }

            if(resp.code() == 200) {
                return new Pair<>(resp.body(), RemoteUpdateStatus.OK);
            }

        }catch(IOException e) {
            e.printStackTrace();
        }

        return new Pair<>(null, RemoteUpdateStatus.NETWORK_ERROR);
    }

}
