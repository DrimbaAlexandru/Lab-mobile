package com.example.alex.emptyapp.GUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.alex.emptyapp.Controller.ObserverMessage;
import com.example.alex.emptyapp.Controller.MesajeController;
import com.example.alex.emptyapp.Controller.MesajeControllerSingleton;
import com.example.alex.emptyapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends Activity implements Observer
{
    private MesajeController controller = null;
    private ListView list;
    private ArrayList< String > ids_list = new ArrayList<>();
    private final List< String > products_list = new ArrayList<>();
    private CustomArrayAdapter listAdapter = null;

    private int selected_item = -1;

    private EditText txt_location;
    private EditText txt_quantity;
    private EditText txt_text;

    private void show_text_dialog( String msg, String title )
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

        MesajeControllerSingleton.setContext( getApplicationContext() );
        controller = MesajeControllerSingleton.getInstance();

        txt_location = findViewById( R.id.txt_location );
        txt_quantity = findViewById( R.id.txt_mesaj );
        txt_text = findViewById( R.id.txt_text );

        list = ( ListView )findViewById( R.id.list_tasks );

        listAdapter = new CustomArrayAdapter( MainActivity.this, android.R.layout.simple_list_item_1, products_list );
        list.setAdapter( listAdapter );
        list.setOnItemClickListener( ( AdapterView< ? > adapterView, View view, int i, long l ) ->
                                     {
                                         listAdapter.setSelected_position( i );
                                         selected_item = i;
                                     } );

        Button btn_raport = findViewById( R.id.btn_report );
        Button btn_download_all = findViewById( R.id.btn_download_all );
        Button btn_submit_inregistrare = findViewById( R.id.btn_submit );

        btn_submit_inregistrare.setOnClickListener( ev ->
                                                    {
                                                        try
                                                        {
                                                            if( selected_item < 0 || selected_item >= controller.getCache().size() )
                                                            {
                                                                throw new Exception( "No item selected" );
                                                            }
                                                            ProductDescription pd = controller.getCache().get( selected_item );
                                                            InregistrareProdus ip = new InregistrareProdus();
                                                            ip.setLocation( txt_location.getText().toString() );
                                                            ip.setQuantity( Integer.parseInt( txt_quantity.getText().toString() ) );
                                                            ip.setCode( pd.getCode() );

                                                            Inregistrare_Produs_Dialog dialog = new Inregistrare_Produs_Dialog();
                                                            dialog.setController( controller );
                                                            dialog.show( getFragmentManager(), "Inregistrare dialog" );
                                                            controller.InregistrareProdus( ip );
                                                        }
                                                        catch( Exception e )
                                                        {
                                                            show_text_dialog( e.getMessage(), "Error!" );
                                                            Log.e( "Error", e.getMessage() );
                                                            e.printStackTrace();
                                                        }
                                                    } );

        btn_raport.setOnClickListener( ev ->
                                       {
                                           try
                                           {
                                               Intent intent = new Intent( this, Raport_Activity.class );
                                               intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME );
                                               startActivity( intent );
                                               controller.getRaport( txt_location.getText().toString() );
                                           }
                                           catch( Exception e )
                                           {
                                               show_text_dialog( e.getMessage(), "Error!" );
                                               Log.e( "Error", e.getMessage() );
                                               e.printStackTrace();
                                           }
                                       } );
        btn_download_all.setOnClickListener( ev ->
                                             {
                                                 try
                                                 {
                                                     Progress_Download_Dialog dialog = new Progress_Download_Dialog();
                                                     dialog.setController( controller );
                                                     dialog.show( getFragmentManager(), "Download dialog" );
                                                     controller.start_downloader();
                                                 }
                                                 catch( Exception e )
                                                 {
                                                     show_text_dialog( e.getMessage(), "Error!" );
                                                     Log.e( "Error", e.getMessage() );
                                                     e.printStackTrace();
                                                 }
                                             } );

        txt_text.addTextChangedListener( new TextWatcher()
        {
            @Override
            public void beforeTextChanged( CharSequence charSequence, int i, int i1, int i2 )
            {

            }

            @Override
            public void onTextChanged( CharSequence charSequence, int i, int i1, int i2 )
            {
                controller.setDescription_filter( txt_text.getText().toString() );
            }

            @Override
            public void afterTextChanged( Editable editable )
            {

            }
        } );
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        controller.addObserver( this );
    }

    @Override
    public void update( Observable observable, Object o )
    {
        if( o instanceof ObserverMessage && o == ObserverMessage.Refresh_Main_UI )
        {
            runOnUiThread( () ->
                           {
                               populate_list();
                           } );
        }
    }

    private void populate_list()
    {
        synchronized( controller.getCache() )
        {
            products_list.clear();
            ids_list.clear();
            controller.setCurrent_PD_page( 1 );
            controller.setDescription_filter( txt_text.getText().toString() );

            for( ProductDescription pd : controller.getCache() )
            {
                ids_list.add( pd.getCode() );
                products_list.add( pd.getDescription() );
            }
            Log.i( "Count", products_list.size() + "" );
            listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        controller.deleteObserver( this );
    }
}
