package com.example.alex.emptyapp.GUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.alex.emptyapp.Controller.TaskController;
import com.example.alex.emptyapp.Controller.TaskControllerSingleton;
import com.example.alex.emptyapp.Domain.MyTask;
import com.example.alex.emptyapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends Activity implements Observer
{
    private TaskController controller = null;
    private ListView list;
    private ArrayList< Integer > ids_list = new ArrayList<>();
    private List< String > tasks_list = new ArrayList<>();
    private ArrayAdapter listAdapter = null;

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

        TaskControllerSingleton.setContext( getApplicationContext() );
        controller = TaskControllerSingleton.getInstance();

        list = ( ListView )findViewById( R.id.list_tasks );

        listAdapter = new ArrayAdapter< String >( MainActivity.this, android.R.layout.simple_list_item_1, tasks_list );
        list.setAdapter( listAdapter );
        list.setOnItemClickListener( new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick( AdapterView< ? > adapterView, View view, int i, long l )
            {
                MyTask task = controller.getTaskById( ids_list.get( i ) );
                MyTask conflict = controller.getConflictOldValue( ids_list.get( i ) );
                //Do stuff with these values.
                Log.i( "Clicked item", task.getText() + task.getId() );
            }
        } );

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        TaskControllerSingleton.getInstance().addObserver( this );
    }

    @Override
    public void update( Observable observable, Object o )
    {
        runOnUiThread( ()->
                       {
                           populate_list();
                       });

    }

    private void populate_list()
    {
        tasks_list.clear();
        ids_list.clear();
        String prefix;
        for( MyTask t : controller.getAllTasks() )
        {
            prefix = "";
            if( t.getStatus().equals( "deleted" ) )
            {
                prefix += "[deleted] ";
            }
            if( controller.getConflictOldValue( t.getId() ) != null )
            {
                prefix += "[conflict] ";
            }
            tasks_list.add( prefix + t.getText() );
            ids_list.add( t.getId() );
        }
        Log.i( "Count", tasks_list.size()+"" );
        listAdapter.notifyDataSetChanged();

    }
}
