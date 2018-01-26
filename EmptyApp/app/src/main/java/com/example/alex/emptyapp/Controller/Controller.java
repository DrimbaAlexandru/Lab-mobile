package com.example.alex.emptyapp.Controller;



import com.example.alex.emptyapp.Domain.MyTask;
import com.example.alex.emptyapp.Service.TaskService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Alex on 26.01.2018.
 */

public class Controller extends Observable
{
    private boolean run_updater = true;
    private Thread updater = null;
    private TaskService service = null;

    private int tasks_downloaded = 0;
    private int tasks_uploaded = 0;

    private final int tick_time = 1000;

    private final List< MyTask > upload_queue = new ArrayList<>();
    private final Map< Integer, MyTask > conflict_list = new HashMap<>();

    public Controller( TaskService srv )
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
            if( ticks % 1 == 0 ) // 1 sec processing
            {
                synchronized( upload_queue )
                {
                    for( int i = 0; i < upload_queue.size(); i++ )
                    {
                        Object response = service.put_task( upload_queue.get( i ) );
                        switch( response ) // if task upload succeeds
                        {
                            case Ok:
                            case Conflict:
                                conflict_list.put( upload_queue.get( i ).getId(), upload_queue.get( i ) );
                            default:
                                setChanged();
                                upload_queue.remove( i ); // remove task from the upload queue
                                i--;
                                break;
                        }
                    }
                }
            }
            if( ticks % 5 == 0 ) // 5 sec processing
            {
                if( service.updateLocal() )
                {
                    tasks_downloaded = service.get_tasks_downloaded();
                    tasks_uploaded = service.get_tasks_uploaded();
                    setChanged();
                }
            }

            if( hasChanged() )
            {
                service.updateLocal();
            }

            notifyObservers();
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
        notifyObservers();
    }

    public List<MyTask> getAllTasks()
    {
        return null;
    }

    public MyTask geyById( int Id )
    {
        return null;
    }

    public void deleteTaskFromLocal( int Id )
    {

    }

    public void updateTask( MyTask task )
    {
        synchronized( upload_queue )
        {
            upload_queue.add( task );
        }
    }

    public int getTasks_downloaded()
    {
        return tasks_downloaded;
    }

    public int getTasks_uploaded()
    {
        return tasks_uploaded;
    }

    public MyTask getConflictOldValue( int Id )
    {
        return conflict_list.get( Id );
    }
}
