package com.example.alex.emptyapp.Service;

import com.example.alex.emptyapp.Repository.Rest.RestTaskRepository;

/**
 * Created by Alex on 28.12.2017.
 */

public class RestTaskService
{
    RestTaskRepository classRepo;

    public RestTaskService( RestTaskRepository tR )
    {
        classRepo = tR;
    }

}
