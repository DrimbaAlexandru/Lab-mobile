package com.example.alex.emptyapp.Controller;



import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.alex.emptyapp.Domain.MyTask;
import com.example.alex.emptyapp.Service.TaskService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/**
 * Created by Alex on 26.01.2018.
 */

public class TaskController extends Observable
{
    private boolean run_updater = true;
    private Thread updater = null;
    private TaskService service = null;
    private Set< TaskControllerStatus > status = new LinkedHashSet<>();

    private final int tick_time = 1000;

    private final List< Integer > page_request_queue = new ArrayList<>();

    private boolean isConnected()
    {
        ConnectivityManager cm = ( ConnectivityManager )service.getContext().getSystemService( Context.CONNECTIVITY_SERVICE );

        return cm.getActiveNetworkInfo() != null;
    }

    public TaskController( TaskService srv )
    {
        service = srv;
        updater = new Thread( new Runnable()
        {
            @Override
            public void run()
            {
                updater_thread();
            }
        } );
        updater.start();
    }

    private void updater_thread()
    {
        int ticks = 0;
        while( run_updater )
        {
            status.remove( TaskControllerStatus.LOADING );
            status.remove( TaskControllerStatus.FAILED_LOADING_FIRST_PAGE );
            status.remove( TaskControllerStatus.FAILED_LOADING_PAGE );
            if( ticks % 1 == 0 ) // 1 sec processing
            {
                synchronized( page_request_queue )
                {
                    for( int i = 0; i < page_request_queue.size(); i++ )
                    {
                        if( service.downloadPage( page_request_queue.get( i ) ) )
                        {
                            page_request_queue.remove( i );
                            i--;
                            setChanged();
                        }
                        else
                        {
                            status.add( TaskControllerStatus.FAILED_LOADING_PAGE );
                            if( page_request_queue.get( i ) == 0 )
                            {
                                status.add( TaskControllerStatus.FAILED_LOADING_FIRST_PAGE );
                            }
                            page_request_queue.remove( i );
                            i--;
                        }
                    }
                }
            }
            if( ticks % 5 == 0 ) // 5 sec processing
            {
                try
                {
                    if( isConnected() )
                    {
                        if( !status.contains( TaskControllerStatus.CONNECTED_TO_INTERNET ) )
                        {
                            status.add( TaskControllerStatus.CONNECTED_TO_INTERNET );
                        }
                    }//Update internet status

                    if( service.updateLocal() )
                    {
                        setChanged();
                    }
                }
                catch( Exception e )
                {
                    Log.wtf( "WTF", e.getMessage() );
                }

            }

            notifyObservers();
            ticks++;
            try
            {
                Thread.sleep( tick_time );
            }
            catch( InterruptedException e )
            {
                e.printStackTrace();
                run_updater = false;
            }
        }
    }

    @Override
    public synchronized void addObserver( Observer o )
    {
        super.addObserver( o );
        setChanged();
        notifyObservers();
    }

    public List< MyTask > getPage( int page )
    {
        return service.getPage( page );
    }

    public MyTask getTaskById( int Id )
    {
        return service.getTaskById( Id );
    }

    public boolean deleteTask( int Id )
    {
        return service.deleteTask( Id );
    }

    public void requestPageReload( int page )
    {
        synchronized( page_request_queue )
        {
            if( !page_request_queue.contains( page ) )
            {
                page_request_queue.add( page );
            }
        }
    }

    public int getTotalTaskCount()
    {
        return service.getTotalTaskCount();
    }

    public Set< TaskControllerStatus > getStatus()
    {
        return status;
    }
}
