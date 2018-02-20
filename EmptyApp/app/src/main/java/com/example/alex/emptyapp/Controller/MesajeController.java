package com.example.alex.emptyapp.Controller;



import android.util.Log;
import android.util.Pair;

import com.example.alex.emptyapp.Domain.Mesaj;
import com.example.alex.emptyapp.Service.MesajeService;
import com.example.alex.emptyapp.Service.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static com.example.alex.emptyapp.Service.ResponseStatus.OK;

/**
 * Created by Alex on 26.01.2018.
 */

public class MesajeController extends Observable
{
    private boolean run_updater = true;
    private Thread updater = null;
    private MesajeService service = null;

    private String last_exception_message = "";

    private final int tick_time = 1000;

    public MesajeController( MesajeService srv )
    {
        service = srv;
    }

    public void start_updater_thread()
    {
        run_updater = true;
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

    public void stop_updater_thread()
    {
        run_updater = false;
    }

    private void updater_thread()
    {
        int ticks = 0;
        while( run_updater )
        {
            if( ticks % 1 == 0 ) // 1 sec processing
            {
                ResponseStatus response = service.downloadMesajeFromServer();
                switch( response )
                {
                    case OK:
                        setChanged();
                        notifyObservers( ObserverMessage.Get_Mesaje_Completed );
                        break;
                    case NETWORK_ERROR:
                        setChanged();
                        notifyObservers( ObserverMessage.Get_Mesaje_Network_Error );
                        try
                        {
                            Thread.sleep( tick_time * 10 );
                        }
                        catch( InterruptedException e )
                        {
                            e.printStackTrace();
                            run_updater = false;
                        }
                        break;
                    case REFUSED_BY_SERVER:
                        setChanged();
                        notifyObservers( ObserverMessage.Get_Mesaje_Refused );
                        run_updater = false;
                        break;
                }
            }

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

    public List< Mesaj > get_All_Mesaje()
    {
        return service.getAllMesaje_local();
    }

    public void postMesaj( String text )
    {
        Thread thread = new Thread( () ->
                                    {
                                        ResponseStatus response = service.postMesaj( text );
                                        switch( response )
                                        {
                                            case NETWORK_ERROR:
                                                setChanged();
                                                notifyObservers( ObserverMessage.Post_Mesaj_Network_Error );
                                                break;
                                            case OK:
                                                setChanged();
                                                notifyObservers( ObserverMessage.Post_Mesaj_Completed );
                                                break;
                                            case REFUSED_BY_SERVER:
                                                setChanged();
                                                notifyObservers( ObserverMessage.Post_Mesaj_Refused );
                                                break;
                                        }
                                    } );
        thread.start();
    }

    public void login( String username )
    {
        {
            Thread thread = new Thread( () ->
                                        {
                                            ResponseStatus response = service.login( username );
                                            switch( response )
                                            {
                                                case NETWORK_ERROR:
                                                    setChanged();
                                                    notifyObservers( ObserverMessage.Login_Network_Error );
                                                    break;
                                                case OK:
                                                    setChanged();
                                                    notifyObservers( ObserverMessage.Login_Completed );
                                                    break;
                                                case REFUSED_BY_SERVER:
                                                    setChanged();
                                                    notifyObservers( ObserverMessage.Login_Refused );
                                                    break;
                                            }
                                        } );
            thread.start();
        }
    }

    public void logout()
    {
        {
            Thread thread = new Thread( () ->
                                        {
                                            ResponseStatus response = service.logout();
                                            switch( response )
                                            {
                                                case NETWORK_ERROR:
                                                    setChanged();
                                                    notifyObservers( ObserverMessage.Logout_Network_Error );
                                                    break;
                                                case OK:
                                                    setChanged();
                                                    notifyObservers( ObserverMessage.Logout_Completed );
                                                    break;
                                                case REFUSED_BY_SERVER:
                                                    setChanged();
                                                    notifyObservers( ObserverMessage.Logout_Refused );
                                                    break;
                                            }
                                        } );
            thread.start();
        }
    }

    public boolean isLoggedIn()
    {
        return service.isLoggedIn();
    }
}
