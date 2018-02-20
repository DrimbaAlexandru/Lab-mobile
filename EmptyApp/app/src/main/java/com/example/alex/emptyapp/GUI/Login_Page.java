package com.example.alex.emptyapp.GUI;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.alex.emptyapp.Controller.MesajeController;
import com.example.alex.emptyapp.Controller.MesajeControllerSingleton;
import com.example.alex.emptyapp.Controller.ObserverMessage;
import com.example.alex.emptyapp.GUI.Dialogs.Login_Dialog;
import com.example.alex.emptyapp.R;

import java.util.Observable;
import java.util.Observer;

public class Login_Page extends Activity implements Observer
{
    private MesajeController controller = null;

    private void on_logged_in()
    {
        if( controller.isLoggedIn() );
        Intent intent = new Intent( this, MainActivity.class );
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME );
        startActivity( intent );
        //finish();
    }

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login_page );

        MesajeControllerSingleton.setContext( getApplicationContext() );
        controller = MesajeControllerSingleton.getInstance();

        if( controller.isLoggedIn() )
        {
            on_logged_in();
        }
        else
        {
            Button btn_login = findViewById( R.id.btn_login );
            btn_login.setOnClickListener( view ->
                                          {
                                              Login_Dialog dialog = new Login_Dialog();
                                              dialog.setController( controller );
                                              dialog.show( getFragmentManager(), "Login" );
                                              controller.login( ( ( EditText )findViewById( R.id.txt_username ) ).getText().toString() );
                                          } );
        }
    }

    @Override
    public void update( Observable observable, Object o )
    {
        if( o instanceof ObserverMessage )
        {
            switch( ( ObserverMessage )o )
            {

                case Login_Completed:
                    runOnUiThread( () ->
                                   {
                                       on_logged_in();
                                   } );
                    break;
            }
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
}
