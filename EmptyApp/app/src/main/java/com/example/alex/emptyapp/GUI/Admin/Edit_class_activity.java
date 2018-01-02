package com.example.alex.emptyapp.GUI.Admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.ArraySet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.alex.emptyapp.Domain.ClassSchedule;
import com.example.alex.emptyapp.Domain.Difficulty;
import com.example.alex.emptyapp.Domain.GymClass;
import com.example.alex.emptyapp.Domain.Role;
import com.example.alex.emptyapp.Domain.User;
import com.example.alex.emptyapp.R;
import com.example.alex.emptyapp.Repository.Local.AppDB;
import com.example.alex.emptyapp.Service.DBGymService;
import com.example.alex.emptyapp.Service.GymService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class Edit_class_activity extends Activity
{
    private GymService srv;
    private GymClass gymClass;
    private ClassSchedule editing_cs = null;

    ArrayList< String > class_schedules_list;
    ArrayList< Integer > class_schedules_ids;
    ArrayAdapter< String > class_schedules_adapter;
    ArrayList< String > diff_list;
    ArrayAdapter< String > diff_adapter;
    ArrayList< String > trainers_list;
    ArrayList< Integer > trainers_ids;
    ArrayAdapter< String > trainers_adapter;

    private void onAddUpdateClass()
    {
        try
        {
            String newName = ( ( EditText )findViewById( R.id.txt_class_name ) ).getText().toString();
            if( gymClass != null )
            {
                gymClass.setName( newName );
                srv.updateClass( gymClass );
            }
            else
            {
                gymClass = new GymClass();
                gymClass.setName( newName );
                Set< Integer > trainers = new ArraySet<>();
                for( User u : srv.getUsers() )
                {
                    if( u.getRole() == Role.TRAINER )
                    {
                        trainers.add( u.getId() );
                    }
                }
                gymClass.setClassTrainers( trainers );
                gymClass = srv.addClass( gymClass );

            }
        }
        catch( Exception e )
        {
            show_error_dialog( e.getMessage(), "Error!" );
        }
        populate();
    }

    private void populate_cs_details( ClassSchedule classSchedule )
    {
        try
        {
            if( classSchedule != null )
            {
                ( ( EditText )findViewById( R.id.txt_cs_date ) ).setText( new SimpleDateFormat( "yyyy-MM-dd HH:mm" ).format( classSchedule.getDate() ) );
                ( ( EditText )findViewById( R.id.txt_cs_capacity ) ).setText( "" + classSchedule.getCapacity() );
                ( ( Spinner )findViewById( R.id.spinner_cs_difficulty ) ).setSelection( classSchedule.getDifficulty().ordinal() );
                ( ( EditText )findViewById( R.id.txt_cs_room ) ).setText( classSchedule.getRoom() );
                ( ( Spinner )findViewById( R.id.spinner_cs_trainer ) ).setSelection( trainers_ids.indexOf( classSchedule.getTrainerId() ) );
                editing_cs = classSchedule;
            }
            else
            {
                ( ( EditText )findViewById( R.id.txt_cs_date ) ).setText( "" );
                ( ( EditText )findViewById( R.id.txt_cs_capacity ) ).setText( 0 );
                ( ( Spinner )findViewById( R.id.spinner_cs_difficulty ) ).setSelection( 0 );
                ( ( EditText )findViewById( R.id.txt_cs_room ) ).setText( "" );
                ( ( Spinner )findViewById( R.id.spinner_cs_trainer ) ).setSelection( 0 );
                editing_cs = null;
            }

        }
        catch( Exception e )
        {
            show_error_dialog( e.getMessage(), "Error!" );
            editing_cs = null;
        }
    }

    private void populate()
    {
        class_schedules_list.clear();
        class_schedules_ids.clear();
        trainers_ids.clear();
        trainers_list.clear();

        editing_cs = null;

        //------------
        if( gymClass != null )
        {
            ( ( EditText )findViewById( R.id.txt_class_name ) ).setText( gymClass.getName() );
            ( ( Button )findViewById( R.id.btn_update_class_name ) ).setText( "Update" );
        }
        else
        {
            ( ( EditText )findViewById( R.id.txt_class_name ) ).setText( "" );
            ( ( Button )findViewById( R.id.btn_update_class_name ) ).setText( "Add" );
        }

        //------------

        if( gymClass != null )
        {
            for( ClassSchedule cs : srv.getClassSchedule( gymClass.getId() ) )
            {
                class_schedules_list.add( new SimpleDateFormat( "yyyy-MM-dd HH:mm" ).format( cs.getDate() ) + " - " + cs.getRoom() );
                class_schedules_ids.add( cs.getId() );
            }
            class_schedules_adapter.notifyDataSetChanged();
        }

        //------------
        ( ( Spinner )findViewById( R.id.spinner_cs_trainer ) ).setAdapter( trainers_adapter );

        for( User u:srv.getUsers() )
        {
            if( u.getRole() == Role.TRAINER )
            {
                trainers_list.add( u.getName() );
                trainers_ids.add( u.getId() );
            }
        }
        trainers_adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.edit_class );

        srv = new GymService( getApplicationContext() );

        gymClass = srv.getClassById( ( int )getIntent().getSerializableExtra( "GymClassId" ) );

        final ListView class_schedules = ( ListView )findViewById( R.id.list_class_schedule );

        class_schedules_list = new ArrayList<>();
        class_schedules_ids = new ArrayList<>();
        class_schedules_adapter = new ArrayAdapter< String >( Edit_class_activity.this, android.R.layout.simple_list_item_1, class_schedules_list );

        diff_list = new ArrayList<>();
        diff_adapter = new ArrayAdapter< String >( Edit_class_activity.this, android.R.layout.simple_list_item_1, diff_list );

        trainers_list = new ArrayList<>();
        trainers_ids = new ArrayList<>();
        trainers_adapter = new ArrayAdapter< String >( Edit_class_activity.this, android.R.layout.simple_list_item_1, trainers_list );

        class_schedules.setAdapter( class_schedules_adapter );
        class_schedules.setOnItemClickListener( ( AdapterView< ? > adapterView, View view, int i, long l )->
                                                {
                                                    populate_cs_details( srv.getClassScheduleById( class_schedules_ids.get( i ) ) );
                                                }
                                              );

        ( ( Spinner )findViewById( R.id.spinner_cs_difficulty ) ).setAdapter( diff_adapter );

        for( Difficulty d : Difficulty.values() )
        {
            diff_list.add( d.name() );
        }
        diff_adapter.notifyDataSetChanged();

        ( ( Button )findViewById( R.id.btn_update_class_name ) ).setOnClickListener( e->{
            onAddUpdateClass();
        } );
        ( ( Button )findViewById( R.id.btn_cs_add ) ).setOnClickListener( e->{
            onAddCS();
        } );
        ( ( Button )findViewById( R.id.btn_cs_update ) ).setOnClickListener( e->{
            onUpdateCS();
        } );
        ( ( Button )findViewById( R.id.btn_cs_delete ) ).setOnClickListener( e->{
            onDeleteCS();
        } );

    }

    private void onAddCS()
    {
        ClassSchedule cs = new ClassSchedule();

        try
        {
            cs.setCapacity( Integer.parseInt( ( ( EditText )findViewById( R.id.txt_cs_capacity ) ).getText().toString() ) );
            cs.setClassId( gymClass.getId() );
            cs.setDifficulty( Difficulty.valueOf( diff_list.get( ( ( Spinner )findViewById( R.id.spinner_cs_difficulty ) ).getSelectedItemPosition() ) ) );
            cs.setRoom( ( ( EditText )findViewById( R.id.txt_cs_room ) ).getText().toString() );
            cs.setTrainerId( trainers_ids.get( ( ( Spinner )findViewById( R.id.spinner_cs_trainer ) ).getSelectedItemPosition() ) );
            DateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
            Date date = df.parse( ( ( EditText )findViewById( R.id.txt_cs_date ) ).getText().toString() );
            cs.setDate( date );

            srv.addClassSchedule( cs );
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
        ClassSchedule cs = new ClassSchedule();

        try
        {
            if( editing_cs != null )
            {
                cs.setCapacity( Integer.parseInt( ( ( EditText )findViewById( R.id.txt_cs_capacity ) ).getText().toString() ) );
                cs.setClassId( gymClass.getId() );
                cs.setDifficulty( Difficulty.valueOf( diff_list.get( ( ( Spinner )findViewById( R.id.spinner_cs_difficulty ) ).getSelectedItemPosition() ) ) );
                cs.setRoom( ( ( EditText )findViewById( R.id.txt_cs_room ) ).getText().toString() );
                cs.setTrainerId( trainers_ids.get( ( ( Spinner )findViewById( R.id.spinner_cs_trainer ) ).getSelectedItemPosition() ) );
                DateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
                Date date = df.parse( ( ( EditText )findViewById( R.id.txt_cs_date ) ).getText().toString() );
                cs.setDate( date );
                cs.setId( editing_cs.getId() );

                srv.updateClassSchedule( cs );
                populate();
            }
            else
            {
                throw new IllegalStateException( "No class schedule is selected" );
            }
        }
        catch( Exception e )
        {
            show_error_dialog( e.getMessage(), "Error!" );
        }

    }

    private void onDeleteCS()
    {
        try
        {
            if( editing_cs != null )
            {
                srv.deleteClassSchedule( editing_cs );
            }
            else
            {
                throw new IllegalStateException( "No class schedule is selected" );
            }
            populate();
        }
        catch( Exception e )
        {
            show_error_dialog( e.getMessage(), "Error!" );
        }
    }

    private void show_error_dialog( String msg, String title )
    {
        AlertDialog alertDialog = new AlertDialog.Builder( Edit_class_activity.this ).create();
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
