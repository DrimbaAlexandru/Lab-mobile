package com.example.alex.emptyapp.Controller;

import android.content.Context;

import com.example.alex.emptyapp.Service.TaskService;

/**
 * Created by Alex on 27.01.2018.
 */

public class TaskControllerSingleton
{
    private static TaskController controller = null;
    private static Context context = null;

    public static TaskController getInstance()
    {
        if( controller == null )
        {
            controller = new TaskController( new TaskService( context ) );
        }
        return controller;
    }

    public static void setContext( Context context )
    {
        TaskControllerSingleton.context = context;
    }
}
