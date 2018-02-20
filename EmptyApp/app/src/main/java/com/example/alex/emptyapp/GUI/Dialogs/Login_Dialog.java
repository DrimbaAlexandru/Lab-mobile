package com.example.alex.emptyapp.GUI.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.alex.emptyapp.Controller.ObserverMessage;
import com.example.alex.emptyapp.Controller.MesajeController;
import com.example.alex.emptyapp.R;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alex on 19.02.2018.
 */

public class Login_Dialog extends DialogFragment implements Observer
{
    private MesajeController controller = null;
    private Handler mHandler = new Handler();
    private View thisView = null;

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState )
    {
        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
        builder.setMessage( "" ).setTitle( "Logging in..." ).setNegativeButton( "  .  ", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick( DialogInterface dialogInterface, int i )
            {
                Log.i( "", "Da nu cumva sa te inchizi" );
            }
        } );

        thisView = getActivity().getLayoutInflater().inflate( R.layout.progress_bar_dialog, null );
        builder.setView( thisView );
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
        //( ( ProgressBar )( thisView.findViewById( R.id.dialog_progress_Bar ) ) ).setVisibility( View.VISIBLE );
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
            Log.i( "Login Dialog", "UPDATE" );
            mHandler.post( () ->
                           {
                               AlertDialog this_dialog = ( AlertDialog )( this.getDialog() );

                               if( o instanceof ObserverMessage )
                               {
                                   switch( ( ObserverMessage )o )
                                   {
                                       case Login_Completed:
                                           this_dialog.dismiss();
                                           break;
                                       case Login_Network_Error:
                                           ( ( TextView )( thisView.findViewById( R.id.dialog_txt ) ) ).setText( "Login error! A network error occured." );
                                           this_dialog.setButton( DialogInterface.BUTTON_NEGATIVE, "Close", ( DialogInterface dialog, int id ) ->
                                           {
                                               dismiss();
                                           } );
                                           ( ( ProgressBar )( thisView.findViewById( R.id.dialog_progress_Bar ) ) ).setVisibility( View.INVISIBLE );
                                           break;
                                       case Login_Refused:
                                           ( ( TextView )( thisView.findViewById( R.id.dialog_txt ) ) ).setText( "Login failed! The server refused the credentials" );
                                           this_dialog.setButton( DialogInterface.BUTTON_NEGATIVE, "Close", ( DialogInterface dialog, int id ) ->
                                           {
                                               dismiss();
                                           } );
                                           ( ( ProgressBar )( thisView.findViewById( R.id.dialog_progress_Bar ) ) ).setVisibility( View.INVISIBLE );
                                           break;
                                   }
                               }
                           } );

        }
    }
}