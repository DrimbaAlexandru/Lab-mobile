package com.example.alex.emptyapp.GUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.alex.emptyapp.Controller.ObserverMessage;
import com.example.alex.emptyapp.Controller.MesajeController;
import com.example.alex.emptyapp.Controller.MesajeControllerSingleton;
import com.example.alex.emptyapp.Domain.Mesaj;
import com.example.alex.emptyapp.GUI.Dialogs.Logout_Dialog;
import com.example.alex.emptyapp.GUI.Dialogs.Put_Message_Dialog;
import com.example.alex.emptyapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends Activity implements Observer
{
    private MesajeController controller = null;
    private ListView list;
    private final List< String > lista_mesaje = new ArrayList<>();
    private CustomArrayAdapter listAdapter = null;

    private int selected_item = -1;

    private void show_text_dialog( String title, String msg )
    {
        AlertDialog alertDialog = new AlertDialog.Builder( MainActivity.this ).create();
        alertDialog.setTitle( title );
        alertDialog.setMessage( msg );
        alertDialog.setButton( AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener()
        {
            public void onClick( DialogInterface dialog, int which )
            {
                dialog.dismiss();
            }
        } );
        alertDialog.show();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        controller = MesajeControllerSingleton.getInstance();

        list = ( ListView )findViewById( R.id.list_tasks );

        listAdapter = new CustomArrayAdapter( MainActivity.this, android.R.layout.simple_list_item_1, lista_mesaje );
        list.setAdapter( listAdapter );
        list.setOnItemClickListener( ( AdapterView< ? > adapterView, View view, int i, long l ) ->
                                     {
                                         listAdapter.setSelected_position( i );
                                         selected_item = i;
                                     } );

        Button btn_submit_mesaj = findViewById( R.id.btn_submit );
        Button btn_logout = findViewById( R.id.btn_logout );

        btn_submit_mesaj.setOnClickListener( ev ->
                                                    {
                                                        try
                                                        {
                                                            Put_Message_Dialog dialog = new Put_Message_Dialog();
                                                            dialog.setController( controller );
                                                            dialog.show( getFragmentManager(), "Dialog" );
                                                            controller.postMesaj( ( ( EditText )findViewById( R.id.txt_mesaj ) ).getText().toString() );
                                                        }
                                                        catch( Exception e )
                                                        {
                                                            show_text_dialog( "Error!", e.getMessage() );
                                                            Log.e( "Error", e.getMessage() );
                                                            e.printStackTrace();
                                                        }
                                                    } );

        btn_logout.setOnClickListener( ev ->
                                             {
                                                 try
                                                 {
                                                     Logout_Dialog dialog = new Logout_Dialog();
                                                     dialog.setController( controller );
                                                     dialog.show( getFragmentManager(), "Dialog" );
                                                     controller.logout();
                                                 }
                                                 catch( Exception e )
                                                 {
                                                     show_text_dialog( "Error!", e.getMessage() );
                                                     Log.e( "Error", e.getMessage() );
                                                     e.printStackTrace();
                                                 }
                                             } );
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        controller.addObserver( this );
        controller.start_updater_thread();
    }

    @Override
    public void update( Observable observable, Object o )
    {
        if( o instanceof ObserverMessage )
        {
            runOnUiThread( () ->
                           {
                               switch( ( ObserverMessage )o )
                               {

                                   case Get_Mesaje_Completed:
                                       populate_list();
                                       break;
                                   case Get_Mesaje_Network_Error:
                                       show_text_dialog( "Error!", "Failed updating message list from server due to a conectivity error!" );
                                       break;
                                   case Get_Mesaje_Refused:
                                       show_text_dialog( "Error!", "Failed updating message list from server. The servers has denied the request. Check your privilege." );
                                       break;
                                   case Logout_Completed:
                                   case Logout_Network_Error:
                                   case Logout_Refused:
                                       finish();
                               }
                           } );
        }
    }

    private void populate_list()
    {
        lista_mesaje.clear();
        for( Mesaj m : controller.get_All_Mesaje() )
        {
            lista_mesaje.add( m.getUsername() + ": " + m.getText() );
        }
        listAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        controller.deleteObserver( this );
        controller.stop_updater_thread();
    }
}
