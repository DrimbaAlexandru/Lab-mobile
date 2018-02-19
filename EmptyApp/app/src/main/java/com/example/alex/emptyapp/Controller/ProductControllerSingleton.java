package com.example.alex.emptyapp.Controller;

import android.content.Context;

import com.example.alex.emptyapp.Service.ProductService;

/**
 * Created by Alex on 27.01.2018.
 */

public class ProductControllerSingleton
{
    private static ProductController controller = null;
    private static Context context = null;

    public static ProductController getInstance()
    {
        if( controller == null )
        {
            controller = new ProductController( new ProductService( context ) );
        }
        return controller;
    }

    public static void setContext( Context context )
    {
        ProductControllerSingleton.context = context;
    }
}
