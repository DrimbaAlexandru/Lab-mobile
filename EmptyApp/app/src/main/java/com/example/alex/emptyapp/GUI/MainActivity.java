package com.example.alex.emptyapp.GUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.alex.emptyapp.Controller.TaskController;
import com.example.alex.emptyapp.Controller.TaskControllerSingleton;
import com.example.alex.emptyapp.Controller.TaskControllerStatus;
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
    private final List< String > tasks_list = new ArrayList<>();
    private boolean loading_next_page = false;
    private int next_page_to_load = 0;
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

        ActivityInfoComponent fragment = new ActivityInfoComponent();

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add( R.id.StatusFragment, fragment );
        transaction.commit();

        list = ( ListView )findViewById( R.id.list_tasks );

        final Activity activityInstance = this;
        listAdapter = new ArrayAdapter< String >( MainActivity.this, android.R.layout.simple_list_item_1, tasks_list );
        list.setAdapter( listAdapter );
        list.setOnItemClickListener( new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick( AdapterView< ? > adapterView, View view, int i, long l )
            {
                if( controller.deleteTask( ids_list.get( i ) ) )
                {
                    ids_list.remove( i );
                    tasks_list.remove( i );
                }
            }
        } );

        list.setOnScrollListener( new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged( AbsListView absListView, int i )
            {
            }

            @Override
            public void onScroll( AbsListView absListView, int i, int i1, int i2 )
            {
                if( absListView.getLastVisiblePosition() == ids_list.size() - 1
                    && !loading_next_page
                    && ids_list.size() < controller.getTotalTaskCount() ) // Reached end of the list
                {
                    Log.i( "SCROLL", "Reached end of list and Should load more pages" );
                    loading_next_page = true;
                    next_page_to_load = ( ids_list.size() + 9 ) / 10;
                    controller.requestPageReload( next_page_to_load );
                    //request the controller to load next page
                }
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
        runOnUiThread( ()->
                       {
                           populate_list();
                           try
                           {
                               String status = "";
                               boolean show_loading = false;
                               boolean show_retry = false;
                               if( controller.getStatus().contains( TaskControllerStatus.LOADING ) )
                               {
                                   status = "Loading...";
                                   show_loading = true;
                               }
                               if( controller.getStatus().contains( TaskControllerStatus.FAILED_LOADING_PAGE ) )
                               {
                                   show_retry = true;
                               }
                               if( !controller.getStatus().contains( TaskControllerStatus.CONNECTED_TO_INTERNET ) )
                               {
                                   status = "Offline";
                                   show_retry = false;
                               }

                               ( ( TextView )findViewById( R.id.StatusFragment ).findViewById( R.id.txt_status ) ).setText( status );
                               ProgressBar pb = ( ( ProgressBar )findViewById( R.id.StatusFragment ).findViewById( R.id.progressBar ) );
                               Button retry_button = findViewById( R.id.StatusFragment ).findViewById( R.id.btn_retry );
                               if( show_loading )
                               {
                                   pb.setVisibility( View.VISIBLE );
                               }
                               else
                               {
                                   pb.setVisibility( View.INVISIBLE );
                               }
                               if( show_retry )
                               {
                                   retry_button.setVisibility( View.VISIBLE );
                                   retry_button.setOnClickListener( ev ->
                                                                    {
                                                                        loading_next_page = true;
                                                                        next_page_to_load = ( ids_list.size() + 9 ) / 10;
                                                                        controller.requestPageReload( next_page_to_load );
                                                                    } );
                               }
                               else
                               {
                                   retry_button.setVisibility( View.INVISIBLE );
                               }
                           }
                           catch( Exception e )
                           {
                               show_text_dialog( e.getMessage(), "EXCEPTIONE" );
                           }
                       });
    }

    private void populate_list()
    {
        synchronized( tasks_list )
        {
            tasks_list.clear();
            ids_list.clear();
            String prefix;
            int page;
            for( page = 0; page < ( controller.getTotalTaskCount() + 9 ) / 10; page++ )
            {
                for( MyTask t : controller.getPage( page ) )
                {
                    tasks_list.add( t.getText() );
                    ids_list.add( t.getId() );
                }
            }
            if( loading_next_page )
            {
                tasks_list.add( "Loading..." );
            }
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
