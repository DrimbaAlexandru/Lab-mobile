package com.example.alex.emptyapp.Controller;



import android.util.Log;
import android.util.Pair;

import com.example.alex.emptyapp.Domain.InregistrareProdus;
import com.example.alex.emptyapp.Domain.ProductDescription;
import com.example.alex.emptyapp.Repository.Rest.ProductDescriptionResponse;
import com.example.alex.emptyapp.Service.ProductService;
import com.example.alex.emptyapp.Service.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static com.example.alex.emptyapp.Service.ResponseStatus.NETWORK_ERROR;
import static com.example.alex.emptyapp.Service.ResponseStatus.OK;

/**
 * Created by Alex on 26.01.2018.
 */

public class ProductController extends Observable
{
    private boolean run_updater = true;
    private Thread updater = null;
    private ProductService service = null;

    private String report_text = "";

    private String last_mainUI_exception_message = "";

    private int current_PD_page = 1;
    private boolean download_running = false;
    private int page_in_download = 0;

    private final int tick_time = 1000;
    private String description_filter = "";

    private final List< ProductDescription > cache = new ArrayList<>();

    public ProductController( ProductService srv )
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
                List< ProductDescription > pds = get_PD_Page();
                synchronized( cache )
                {
                    cache.clear();
                    cache.addAll( pds );
                    setChanged();
                }
            }
            notifyObservers( ObserverMessage.Refresh_Main_UI );

            if( ticks % 5 == 0 ) // 5 sec processing
            {
                try
                {
                    for( InregistrareProdus ip : service.getInregistrari() )
                    {
                        if( service.sendInregistrareProdus( ip ) == OK )
                        {
                            service.deleteInregistrareProdus( ip );
                        }
                    }
                }
                catch( Exception e )
                {
                    last_mainUI_exception_message += e.getMessage() + "\n";
                    Log.wtf( "WTF", e.getMessage() );
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

    @Override
    public synchronized void addObserver( Observer o )
    {
        super.addObserver( o );
        setChanged();
        notifyObservers();
    }

    private List< ProductDescription > get_PD_Page()
    {
        Pair< ResponseStatus, ProductDescriptionResponse > r = service.getPDPageServer( current_PD_page, description_filter );
        if( r.first == OK )
        {
            return r.second.getItems();
        }
        else
        {
            return service.getPDPageLocal( current_PD_page, description_filter );
        }
    }

    public void InregistrareProdus( InregistrareProdus inregistrareProdus )
    {
        Thread thread = new Thread( () ->
                                    {
                                        ResponseStatus response = service.sendInregistrareProdus( inregistrareProdus );
                                        switch( response )
                                        {
                                            case NETWORK_ERROR:
                                                service.addInregistrareProdus( inregistrareProdus );
                                                setChanged();
                                                notifyObservers( ObserverMessage.Inregistrare_Eroare_Retea );
                                                break;
                                            case OK:
                                                setChanged();
                                                notifyObservers( ObserverMessage.Inregistrare_Succes );
                                                break;
                                            case REFUSED_BY_SERVER:
                                                setChanged();
                                                notifyObservers( ObserverMessage.Inregistrare_Refuzata );
                                                break;
                                        }
                                    } );
        thread.start();
    }

    public void start_downloader()
    {
        Thread downloader = new Thread( () ->
                                        {
                                            int last_page;
                                            ResponseStatus response;

                                            download_running = true;

                                            page_in_download = service.get_last_downloaded_page();
                                            last_page = ( service.getTotalElementsCount() + service.getPage_size() - 1 ) / service.getPage_size();
                                            if( last_page == 0 )
                                            {
                                                last_page = 1;
                                            }
                                            if( page_in_download != last_page )
                                            {
                                                page_in_download++;
                                            }

                                            while( download_running )
                                            {
                                                if( page_in_download > last_page )
                                                {
                                                    download_running = false;
                                                    setChanged();
                                                    notifyObservers( ObserverMessage.Download_Completed );
                                                    break;
                                                }
                                                response = service.updateLocalPDPage( page_in_download );
                                                setChanged();

                                                switch( response )
                                                {
                                                    case OK:
                                                        service.set_last_downloaded_page( page_in_download );
                                                        page_in_download++;
                                                        last_page = ( service.getTotalElementsCount() + service.getPage_size() - 1 ) / service.getPage_size();
                                                        notifyObservers( ObserverMessage.Download_Page_Downloaded );
                                                        break;
                                                    case NETWORK_ERROR:
                                                        notifyObservers( ObserverMessage.Download_Failed );
                                                        break;
                                                    case REFUSED_BY_SERVER:
                                                        notifyObservers( ObserverMessage.Download_Failed );
                                                        break;
                                                }
                                            }
                                        } );
        downloader.start();
    }

    public String getLast_mainUI_exception_message()
    {
        String temp = last_mainUI_exception_message;
        last_mainUI_exception_message = "";
        return temp;
    }

    public ProductDescription getByID( String id )
    {
        return service.getPDById( id );
    }

    public void getRaport( String location )
    {
        Thread thread = new Thread( () ->
                                    {
                                        Pair< ResponseStatus, String > response = service.getRaport( location );
                                        switch( response.first )
                                        {
                                            case NETWORK_ERROR:
                                                setChanged();
                                                notifyObservers( ObserverMessage.Raport_Eroare_Retea );
                                                break;
                                            case OK:
                                                report_text = response.second;
                                                setChanged();
                                                notifyObservers( ObserverMessage.Raport_Succes );
                                                break;
                                            case REFUSED_BY_SERVER:
                                                setChanged();
                                                notifyObservers( ObserverMessage.Raport_Refuzat );
                                                break;
                                        }
                                    } );
        thread.start();
    }

    public List< ProductDescription > getCache()
    {
        synchronized( cache )
        {
            return new ArrayList<>( cache );
        }
    }

    public void setCurrent_PD_page( int current_PD_page )
    {
        this.current_PD_page = current_PD_page;
    }

    public void setDescription_filter( String description_filter )
    {
        this.description_filter = description_filter;
    }

    public void stop_download()
    {
        Log.i( "CONTROLLER", "STOP DOWNLOAD" );
        download_running = false;
    }

    public boolean isDownload_running()
    {
        return download_running;
    }

    public int get_last_downloaded_page()
    {
        return service.get_last_downloaded_page();
    }

    public int get_page_size()
    {
        return service.getPage_size();
    }

    public int getTotalElementsCount()
    {
        return service.getTotalElementsCount();
    }

    public int getPage_in_download()
    {
        return page_in_download;
    }

    public String getReport_text()
    {
        return report_text;
    }
}
