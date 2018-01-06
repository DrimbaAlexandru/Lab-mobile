package com.example.alex.emptyapp.GUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.alex.emptyapp.Domain.User;
import com.example.alex.emptyapp.GUI.Admin.AdminDashboard;
import com.example.alex.emptyapp.GUI.Trainer.TrainerDashboard;
import com.example.alex.emptyapp.GUI.User.UserDashboard;
import com.example.alex.emptyapp.R;
import com.example.alex.emptyapp.Repository.Local.AppDB;
import com.example.alex.emptyapp.Repository.Mock.MockClassRepository;
import com.example.alex.emptyapp.Repository.Mock.MockClassScheduleRepository;
import com.example.alex.emptyapp.Repository.Mock.MockFeedbackRepository;
import com.example.alex.emptyapp.Repository.Mock.MockUserRepository;
import com.example.alex.emptyapp.Service.DBGymService;
import com.example.alex.emptyapp.Service.GymService;

public class MainActivity extends Activity
{
    private GymService srv;

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
        switch( user.getRole() )
        {
            case ADMIN:
            {
                Intent intent = new Intent( this, AdminDashboard.class );
                intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME );
                startActivity( intent );
                break;
            }
            case USER:
            {
                Intent intent = new Intent( this, UserDashboard.class );
                intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME );
                startActivity( intent );
                break;
            }
            case TRAINER:
            {
                Intent intent = new Intent( this, TrainerDashboard.class );
                intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME );
                startActivity( intent );
                break;
            }
            default:
                break;
        }
    }


    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        srv = new GymService( getApplicationContext() );

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
/*
    @Override
    protected void onResume()
    {
        super.onResume();
        if( srv.getLoggedUser() != null )
        {
            if( !started )
            {
                start_app( srv.getLoggedUser() );
            }
            else
            {
                this.finish();
            }
        }
    }
    */
}

/*      TODOs:
*  _______________________________________________________________________________Priority_____|__Status__
* | - Login user and redirect to corresponding dashboard                     | +H  |     |     | 100
* | - Edit account ( animation when displaying this ):                       |     | +M  |     | 50
* | - Register user:                                                         |     |     |  L  | 0
* | - Admin dashboard: - set user as regular user / trainer                  |     | +M  |     | 50
* |                    - manage classes and class schedules                  | +H  |     |     | 100
* |                    - manage subscription types                           |     |  M  |     | 0
* | - Trainer dashboard: - Manage personal schedules                         |     |     |  L  | 0
* |                      - see feedback chart                                |     | +M  |     | 100
* | - Client dashboard: - See class schedules, enroll/unenroll from schedule | +H  |     |     | 50
* |                     - see personal schedule                              |     |     |  L  | 0
* |                     - give rating to trainer                             |     | +M  |     | 100
* |                     - manage its subscriptions                           |     |  M  |     | 0
* |                     - send email to trainer                              |     |     |  L  | 0
* |
*/