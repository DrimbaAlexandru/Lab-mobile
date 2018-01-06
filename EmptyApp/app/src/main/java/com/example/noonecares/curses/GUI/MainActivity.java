package com.example.noonecares.curses.GUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.noonecares.curses.Domain.User;
import com.example.noonecares.curses.GUI.Admin.AdminDashboard;
import com.example.noonecares.curses.R;
import com.example.noonecares.curses.Service.CurseService;

public class MainActivity extends Activity
{
    private CurseService srv;// = new DBCurseService( new MockCursaRepository(), new MockClassScheduleRepository(), new MockUserRepository(), new MockFeedbackRepository() );

    private EditText txt_username;
    private EditText txt_password;

    private boolean started = false;

    private void show_error_dialog( String msg, String title )
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

    private void on_login()
    {
        ProgressBar progressBar = findViewById( R.id.loginProgressBar );
        progressBar.setVisibility( View.VISIBLE );

        try
        {
            User user = srv.login( txt_username.getText().toString(), txt_password.getText().toString() );
            start_app( user );
        }
        catch( Exception e )
        {
            show_error_dialog( e.getMessage(), "Authentication error" );
        }
        progressBar.setVisibility( View.INVISIBLE );

    }

    private void start_app( User user )
    {
        started = true;

        Intent intent = new Intent( this, AdminDashboard.class );
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME );
        startActivity( intent );
    }


    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        srv = new CurseService( getApplicationContext() );

        Button btn_login = ( Button )findViewById( R.id.btn_login );
        txt_username = ( EditText )findViewById( R.id.txt_username );
        txt_password = ( EditText )findViewById( R.id.txt_password );

        try
        {
            if( srv.isOnline() )
            {
                srv.update_local();
            }
            else
            {
                show_error_dialog( "You are offline.", "" );
            }
        }
        catch( Exception e )
        {
            show_error_dialog( e.getMessage(), "Error" );
        }

        btn_login.setOnClickListener( view -> on_login() );

    }
}