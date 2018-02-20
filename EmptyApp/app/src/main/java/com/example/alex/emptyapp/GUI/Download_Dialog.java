package com.example.alex.emptyapp.GUI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.alex.emptyapp.Controller.ObserverMessage;
import com.example.alex.emptyapp.Controller.MesajeController;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alex on 19.02.2018.
 */

public class Download_Dialog extends DialogFragment implements Observer
{
    private MesajeController controller = null;
    private Handler mHandler = new Handler();

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState )
    {
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        builder.setMessage( "" ).setNegativeButton( "Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick( DialogInterface dialog, int id )
            {
                controller.stop_download();
                dismiss();
            }
        } ).setTitle( "Downloading " + ( controller.getPage_in_download() ) + "/" + ( controller.getTotalElementsCount() + controller.get_page_size() - 1 ) / controller.get_page_size() );
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onDismiss( DialogInterface dialog )
    {
        super.onDismiss( dialog );
        if( controller != null )
        {
            controller.stop_download();
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if( controller != null )
        {
            controller.deleteObserver( this );
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if( controller != null )
        {
            controller.addObserver( this );
        }
    }

    public void setController( MesajeController controller )
    {
        this.controller = controller;
    }

    @Override
    public void update( Observable observable, Object o )
    {
        if( controller != null && this.getDialog() instanceof AlertDialog )
        {
            Log.i( "Download Dialog", "UPDATE" );
            mHandler.post( () ->
                           {
                               Log.i( "Download Dialog", "POST" );

                               AlertDialog this_dialog = ( AlertDialog )( this.getDialog() );

                               this_dialog.setTitle( "Downloading " + ( controller.getPage_in_download() ) + "/" + ( controller.getTotalElementsCount() + controller.get_page_size() - 1 ) / controller.get_page_size() );
                               if( o instanceof ObserverMessage )
                               {
                                   switch( ( ObserverMessage )o )
                                   {
                                       case Download_Completed:
                                           this_dialog.setMessage( "Download Complete!" );
                                           break;
                                       case Download_Page_Downloaded:
                                           this_dialog.setMessage( "Downloaded page " + controller.get_last_downloaded_page() );
                                           break;
                                       case Download_Failed:
                                           this_dialog.setMessage( "Failed downloading page " + controller.getPage_in_download() );
                                           break;
                                   }
                               }
                           } );

        }
    }
}