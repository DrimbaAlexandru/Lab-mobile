package com.example.alex.emptyapp.Repository.Rest;

import com.example.alex.emptyapp.Domain.MyTask;

import java.util.List;

/**
 * Created by Alex on 27.01.2018.
 */

public class TaskServerResponse
{
    public int page = 0;
    public int count = 0;
    public List< MyTask > tasks = null;
    public long lastModified = 0;
}
