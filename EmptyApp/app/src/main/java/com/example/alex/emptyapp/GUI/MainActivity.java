package com.example.alex.emptyapp.GUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.alex.emptyapp.GUI.Trainer.TrainerDashboard;
import com.example.alex.emptyapp.GUI.User.UserDashboard;
import com.example.alex.emptyapp.R;
import com.example.alex.emptyapp.Service.TaskService;

public class MainActivity extends Activity
{
    private TaskService srv;

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

        srv = new TaskService( getApplicationContext() );

    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

}
