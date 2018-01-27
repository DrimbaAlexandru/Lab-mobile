package com.example.alex.emptyapp.GUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alex.emptyapp.Controller.TaskController;
import com.example.alex.emptyapp.Controller.TaskControllerSingleton;
import com.example.alex.emptyapp.Domain.MyTask;
import com.example.alex.emptyapp.R;
import com.example.alex.emptyapp.Service.TaskService;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by AlexandruD on 26-Jan-18.
 */

public class EditTaskActivity extends Activity implements Observer
{
    private TaskController controller;

    private void show_text_dialog( String msg, String title )
    {
        AlertDialog alertDialog = new AlertDialog.Builder( EditTaskActivity.this ).create();
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
        setContentView( R.layout.edit_task );

        MyTask baseTask = ( MyTask )this.getIntent().getSerializableExtra( "base-task" );
        MyTask conflictTask = ( MyTask )this.getIntent().getSerializableExtra( "conflict-task" );

        controller = TaskControllerSingleton.getInstance();

        setupUI( baseTask, conflictTask );
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        controller.addObserver( this );
    }

    public void setupUI( MyTask baseTask, MyTask conflictTask )
    {
        Button okButton = findViewById( R.id.confirmButton );
        EditText etNewText = findViewById( R.id.txt_new );
        EditText etOrigText = findViewById( R.id.txt_orig );

        etOrigText.setText( baseTask.getText() );

        if( conflictTask.getVersion() != baseTask.getVersion() )
        {
            etNewText.setText( conflictTask.getText() );
            etNewText.setVisibility( View.VISIBLE );
        }
        else
        {
            etNewText.setText( "" );
            etNewText.setVisibility( View.INVISIBLE );
        }

        okButton.setOnClickListener( e ->
                                     {
                                         String newText = etNewText.getText().toString();
                                         if( newText.length() != 0 )
                                         {
                                             conflictTask.setText( newText );
                                             controller.resolveConflict( conflictTask );

                                             // ends the activity
                                             finish();
                                         }
                                     } );
    }

    @Override
    public void update( Observable observable, Object o )
    {
        runOnUiThread( () ->
                       {
                           final int d = controller.getTasks_downloaded(), u = controller.getTasks_uploaded();
                           if( d + u > 0 )
                           {
                               show_text_dialog( "Tasks downloaded: " + d + "\nTasks uploaded: " + u, "Summary" );
                           }
                       } );

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        controller.deleteObserver( this );
    }
}
