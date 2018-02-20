package com.example.alex.emptyapp.Controller;

import android.content.Context;

import com.example.alex.emptyapp.Service.MesajeService;

/**
 * Created by Alex on 27.01.2018.
 */

public class MesajeControllerSingleton
{
    private static MesajeController controller = null;
    private static Context context = null;

    public static MesajeController getInstance()
    {
        if( controller == null )
        {
            controller = new MesajeController( new MesajeService( context ) );
        }
        return controller;
    }

    public static void setContext( Context context )
    {
        MesajeControllerSingleton.context = context;
    }
}
