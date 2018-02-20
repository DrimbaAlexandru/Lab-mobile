package com.example.alex.emptyapp.GUI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;

import com.example.alex.emptyapp.Controller.ObserverMessage;
import com.example.alex.emptyapp.Controller.MesajeController;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alex on 19.02.2018.
 */

public class Inregistrare_Produs_Dialog extends DialogFragment implements Observer
{
    private MesajeController controller = null;
    private Handler mHandler = new Handler();

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState )
    {
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        builder.setMessage( "" ).setNegativeButton( "Close", new DialogInterface.OnClickListener()
        {
            public void onClick( DialogInterface dialog, int id )
            {
                dismiss();
            }
        } );
        // Create the AlertDialog object and return it
        return builder.create();
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
            mHandler.post( () ->
                           {
                               AlertDialog this_dialog = ( AlertDialog )( this.getDialog() );
                               if( o instanceof ObserverMessage )
                               {
                                   switch( ( ObserverMessage )o )
                                   {
                                       case Inregistrare_Succes:
                                           this_dialog.setMessage( "Inregistrat cu succes!" );
                                           break;
                                       case Inregistrare_Refuzata:
                                           this_dialog.setMessage( "Inregistrare refuzata de server!" );
                                           break;
                                       case Inregistrare_Eroare_Retea:
                                           this_dialog.setMessage( "Eroare de retea!" );
                                           break;
                                   }
                               }
                           } );
        }
    }
}