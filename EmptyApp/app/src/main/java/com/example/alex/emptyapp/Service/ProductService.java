package com.example.alex.emptyapp.Service;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.util.Pair;

import com.example.alex.emptyapp.Domain.InregistrareProdus;
import com.example.alex.emptyapp.Domain.ProductDescription;
import com.example.alex.emptyapp.Repository.Local.AppDB;
import com.example.alex.emptyapp.Repository.Rest.ProductDescriptionResponse;
import com.example.alex.emptyapp.Repository.Rest.RestProductRepository;
import com.example.alex.emptyapp.R;
import com.google.gson.GsonBuilder;

import java.util.Collections;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alex on 28.12.2017.
 */

public class ProductService
{
    //private String host = "http://192.168.0.100:3000";
    private DBProductService db_srv;
    private RestProductService rest_srv;
    private final int page_size = 10;

    public boolean isOnline()
    {
        return true;
    }

    public ProductService( Context context )
    {
        AppDB db = Room.databaseBuilder( context,
                                         AppDB.class, "Local-DB" ).allowMainThreadQueries().build();
        db_srv = new DBProductService( db.inregistrareProdusRepository(), db.productDescriptionRepository(), db.DBStaticsRepository() );

        String host = context.getString( R.string.drimba_host );

        Log.d( "TASK-SERVICE", "HOST IS: " + host );

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( host + "/" )
                .addConverterFactory( GsonConverterFactory.create( new GsonBuilder().create() ) )
                .build();

        RestProductRepository restProductRepository = retrofit.create( RestProductRepository.class );

        rest_srv = new RestProductService( restProductRepository );

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy( policy );
    }

    public ResponseStatus updateLocalPDPage( int page )
    {
        Pair< ResponseStatus, ProductDescriptionResponse > resp = rest_srv.getProductDescriptionPage( "", page );
        if( resp.second != null )
        {
            db_srv.setLastPageDownloaded( resp.second.getPage() );
            db_srv.insertProductDescriptions( resp.second.getItems() );
        }
        return resp.first;
    }

    public List< ProductDescription > getPDPageLocal( int page, String text )
    {
        return db_srv.getPagePD( page_size, ( page - 1 ) * page_size, text );
    }

    public Pair< ResponseStatus, ProductDescriptionResponse >  getPDPageServer( int page, String text )
    {
        return rest_srv.getProductDescriptionPage( text, page );
    }

    public void addInregistrareProdus( InregistrareProdus inregistrareProdus )
    {
        db_srv.insertInregistrareProdus( inregistrareProdus );
    }

    public List< InregistrareProdus > getInregistrari()
    {
        return db_srv.getAllIP();
    }

    public void deleteInregistrareProdus( InregistrareProdus inregistrareProdus )
    {
        db_srv.deleteIP( inregistrareProdus );
    }

    public Pair< ResponseStatus, String > getRaport( String location )
    {
        return rest_srv.getRaport( location );
    }

    public ResponseStatus sendInregistrareProdus( InregistrareProdus ip )
    {
        return rest_srv.sendInregistrareProdus( ip );
    }

    public int getTotalElementsCount()
    {
        return db_srv.getTotalElemCount();
    }

    /*public List< ProductDescription > getAllPD()
    {
        return db_srv.getAllPD();
    }*/

    public ProductDescription getPDById( String id )
    {
        return db_srv.getByID( id );
    }

    public int getPage_size()
    {
        return page_size;
    }

    public int get_last_downloaded_page()
    {
        return db_srv.getLastPageDownloaded();
    }
}
