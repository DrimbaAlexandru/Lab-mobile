package com.example.alex.emptyapp.GUI.Admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.alex.emptyapp.Domain.Cursa;
import com.example.alex.emptyapp.Domain.Motociclist;
import com.example.alex.emptyapp.Domain.Participare;
import com.example.alex.emptyapp.R;
import com.example.alex.emptyapp.Service.CurseService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EditCurseActivity extends Activity
{
    private CurseService srv;
    private Cursa cursa;
    private Motociclist editing_moto = null;

    ArrayList< String > motociclisti_list;
    ArrayList< Integer > motociclisti_ids;
    ArrayAdapter< String > motociclisti_adapter;

    ArrayList< String > participanti_list;
    ArrayList< Integer > participanti_ids;
    ArrayAdapter< String > participanti_adapter;

    private void onAddUpdateCursa()
    {
        try
        {
            String newName = ( ( EditText )findViewById( R.id.txt_nume_cursa ) ).getText().toString();
            int newCapacity = Integer.parseInt( ( ( EditText )findViewById( R.id.txt_cap_cursa ) ).getText().toString() );
            if( cursa != null )
            {
                cursa.setNume( newName );
                cursa.setCapacitate( newCapacity );
                srv.updateCursa( cursa );
            }
            else
            {
                cursa = new Cursa();
                cursa.setNume( newName );
                cursa.setCapacitate( newCapacity );
                cursa = srv.addCursa( cursa );
            }
        }
        catch( Exception e )
        {
            show_error_dialog( e.getMessage(), "Error!" );
        }
        populate();
    }

    private void populate_moto_details( Motociclist motociclist )
    {
        try
        {
            if( motociclist != null )
            {
                ( ( EditText )findViewById( R.id.txt_nume_moto ) ).setText( motociclist.getNume() );
                ( ( EditText )findViewById( R.id.txt_echipa_moto ) ).setText( motociclist.getEchipa() );
                ( ( EditText )findViewById( R.id.txt_cap_moto ) ).setText( motociclist.getCapacitate_motor() );
                editing_moto = motociclist;
            }
            else
            {
                ( ( EditText )findViewById( R.id.txt_nume_moto ) ).setText( "" );
                ( ( EditText )findViewById( R.id.txt_echipa_moto ) ).setText( "" );
                ( ( EditText )findViewById( R.id.txt_cap_moto ) ).setText( "" );
                ( ( Spinner )findViewById( R.id.spinner_moto ) ).setSelection( 0 );
                editing_moto = null;
            }

        }
        catch( Exception e )
        {
            show_error_dialog( e.getMessage(), "Error!" );
            editing_moto = null;
        }
    }

    private void populate()
    {
        participanti_list.clear();
        motociclisti_list.clear();
        motociclisti_ids.clear();
        participanti_ids.clear();

        editing_moto = null;

        //------------
        if( cursa != null )
        {
            ( ( EditText )findViewById( R.id.txt_nume_cursa ) ).setText( cursa.getNume() );
            ( ( EditText )findViewById( R.id.txt_cap_cursa ) ).setText( cursa.getCapacitate() );
            ( ( Button )findViewById( R.id.btn_update_cursa ) ).setText( "Update" );
        }
        else
        {
            ( ( EditText )findViewById( R.id.txt_nume_cursa ) ).setText( "" );
            ( ( EditText )findViewById( R.id.txt_cap_cursa ) ).setText( "" );
            ( ( Button )findViewById( R.id.btn_update_cursa ) ).setText( "Add" );
        }

        //------------

        if( cursa != null )
        {
            for( Participare p : srv.getParticipanti( cursa.getId() ) )
            {
                Motociclist m = srv.getMotociclist( p.getMotociclistId() );
                participanti_list.add( m.getNume() + " ( " + m.getEchipa() + " )" );
                participanti_ids.add( m.getId() );
            }
            participanti_adapter.notifyDataSetChanged();
        }

        //------------
        ( ( Spinner )findViewById( R.id.spinner_moto ) ).setAdapter( motociclisti_adapter );

        for( Motociclist m : srv.getMotociclisti() )
        {
            motociclisti_list.add( m.getNume() );
            motociclisti_ids.add( m.getId() );
        }
        motociclisti_adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.edit_cursa );

        srv = new CurseService( getApplicationContext() );

        cursa = srv.getCursa( ( int )getIntent().getSerializableExtra( "cursaId" ) );

        final ListView list_participanti = ( ListView )findViewById( R.id.list_participanti );

        motociclisti_list = new ArrayList<>();
        motociclisti_ids = new ArrayList<>();
        motociclisti_adapter = new ArrayAdapter< String >( EditCurseActivity.this, android.R.layout.simple_list_item_1, motociclisti_list );

        participanti_list = new ArrayList<>();
        participanti_ids = new ArrayList<>();
        participanti_adapter = new ArrayAdapter< String >( EditCurseActivity.this, android.R.layout.simple_list_item_1, participanti_list );

        list_participanti.setAdapter( participanti_adapter );
        list_participanti.setOnItemClickListener( ( AdapterView< ? > adapterView, View view, int i, long l ) ->
                                                  {
                                                      populate_moto_details( srv.getMotociclist( motociclisti_ids.get( i ) ) );
                                                  } );

        ( ( Button )findViewById( R.id.btn_update_cursa ) ).setOnClickListener( e->{
            onAddUpdateCursa();
        } );
        ( ( Button )findViewById( R.id.btn_moto_add ) ).setOnClickListener( e->{
            onAddMotociclist();
        } );
        ( ( Button )findViewById( R.id.btn_moto_update ) ).setOnClickListener( e->{
            onUpdateCS();
        } );
        ( ( Button )findViewById( R.id.btn_participa ) ).setOnClickListener( e->{
            onAddParticipant();
        } );

    }

    private void onAddMotociclist()
    {
        Motociclist m = new Motociclist();

        try
        {
            m.setNume( ( ( EditText )findViewById( R.id.txt_nume_moto ) ).getText().toString() );
            m.setCapacitate_motor( Integer.parseInt( ( ( EditText )findViewById( R.id.txt_cap_moto ) ).getText().toString() ) );
            m.setEchipa( ( ( EditText )findViewById( R.id.txt_echipa_moto ) ).getText().toString() );

            srv.addMotociclist( m );
            populate();
        }
        catch( Exception e )
        {
            show_error_dialog( e.getMessage(), "Error!" );
        }

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        populate();
    }

    private void onUpdateCS()
    {
        Motociclist m = new Motociclist();

        try
        {
            if( editing_moto != null )
            {
                m.setNume( ( ( EditText )findViewById( R.id.txt_nume_moto ) ).getText().toString() );
                m.setCapacitate_motor( Integer.parseInt( ( ( EditText )findViewById( R.id.txt_cap_moto ) ).getText().toString() ) );
                m.setEchipa( ( ( EditText )findViewById( R.id.txt_echipa_moto ) ).getText().toString() );

                m.setId( editing_moto.getId() );

                srv.updateMotociclist( m );
                populate();
            }
            else
            {
                throw new IllegalStateException( "Niciun motociclist selectat" );
            }
        }
        catch( Exception e )
        {
            show_error_dialog( e.getMessage(), "Error!" );
        }

    }

    private void onAddParticipant()
    {
        Participare p = new Participare();

        try
        {
            if( editing_moto != null )
            {
                p.setCursaId( cursa.getId() );
                p.setMotociclistId( editing_moto.getId() );

                srv.addParticipare( p );
                populate();
            }
            else
            {
                throw new IllegalStateException( "Niciun motociclist selectat" );
            }
        }
        catch( Exception e )
        {
            show_error_dialog( e.getMessage(), "Error!" );
        }
    }

    private void show_error_dialog( String msg, String title )
    {
        AlertDialog alertDialog = new AlertDialog.Builder( EditCurseActivity.this ).create();
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
}
