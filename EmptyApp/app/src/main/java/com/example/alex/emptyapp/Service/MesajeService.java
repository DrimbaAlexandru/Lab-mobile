package com.example.alex.emptyapp.Service;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.util.Pair;

import com.example.alex.emptyapp.DTO.LoginResponseDTO;
import com.example.alex.emptyapp.Domain.Mesaj;
import com.example.alex.emptyapp.Repository.Local.AppDB;
import com.example.alex.emptyapp.Repository.Rest.RestMesajeRepository;
import com.example.alex.emptyapp.R;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alex on 28.12.2017.
 */

public class MesajeService
{
    //private String host = "http://192.168.0.100:3000";
    private DBMesajeService db_srv;
    private RestMesajeService rest_srv;

    public boolean isOnline()
    {
        return true;
    }

    public MesajeService( Context context )
    {
        AppDB db = Room.databaseBuilder( context,
                                         AppDB.class, "Local-DB" ).allowMainThreadQueries().build();
        db_srv = new DBMesajeService( db.mesajeRepository(), db.DBStaticsRepository() );

        String host = context.getString( R.string.drimba_host );

        Log.d( "TASK-SERVICE", "HOST IS: " + host );

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( host + "/" )
                .addConverterFactory( GsonConverterFactory.create( new GsonBuilder().create() ) )
                .build();

        RestMesajeRepository restProductRepository = retrofit.create( RestMesajeRepository.class );

        rest_srv = new RestMesajeService( restProductRepository );

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy( policy );
    }

    public ResponseStatus login( String username )
    {
        Pair< ResponseStatus, LoginResponseDTO > response = rest_srv.login( username );
        if( response.first == ResponseStatus.OK )
        {
            db_srv.set_token( response.second.getToken() );
            return response.first;
        }
        else
        {
            db_srv.set_token( "" );
            return response.first;
        }
    }

    public ResponseStatus downloadMesajeFromServer()
    {
        Pair< ResponseStatus, List< Mesaj > > response = rest_srv.getMesaje( db_srv.get_token(), db_srv.get_last_created() );
        if( response.first == ResponseStatus.OK )
        {
            db_srv.insertMesaje( response.second );
        }
        return response.first;
    }

    public List<Mesaj> getAllMesaje_local()
    {
        return db_srv.getAllMesaje();
    }

    public ResponseStatus postMesaj( String text )
    {
        return rest_srv.putMesage( db_srv.get_token(), text );
    }

    public ResponseStatus logout()
    {
        ResponseStatus responseStatus = rest_srv.logout( db_srv.get_token() );
        db_srv.resetMesaje();
        db_srv.set_token( "" );
        return responseStatus;
    }

    public boolean isLoggedIn()
    {
        return !db_srv.get_token().equals( "" );
    }

}
