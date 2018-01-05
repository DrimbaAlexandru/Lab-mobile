package com.example.alex.emptyapp.Service;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.alex.emptyapp.Domain.Cursa;
import com.example.alex.emptyapp.Domain.Motociclist;
import com.example.alex.emptyapp.Domain.Participare;
import com.example.alex.emptyapp.Domain.User;
import com.example.alex.emptyapp.Repository.Local.AppDB;
import com.example.alex.emptyapp.Repository.Rest.RestCursaRepository;
import com.example.alex.emptyapp.Repository.Rest.RestMotociclistRepository;
import com.example.alex.emptyapp.Repository.Rest.RestUserRepository;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import retrofit2.Retrofit;

/**
 * Created by Alex on 28.12.2017.
 */

public class CurseService
{
    private final static String host = "http://192.168.0.101:63288/";
    private DBCurseService db_srv;
    private RestCurseService rest_srv;

    public boolean isOnline()
    {
        try
        {
            URL url = new URL( host );
            HttpURLConnection connection = ( HttpURLConnection )url.openConnection();
            connection.setRequestProperty( "Connection", "close" );
            connection.setConnectTimeout( 2000 ); // Timeout 2 second
            connection.connect();

            // If the web service is available
            if( connection.getResponseCode() == 200 )
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch( IOException e )
        {
            return false;
        }
    }

    public CurseService( Context context )
    {
        AppDB db = Room.databaseBuilder( context,
                                         AppDB.class, "PC-Local-DB" ).allowMainThreadQueries().build();
        db_srv = new DBCurseService( db.cursaRepository(), db.motociclistRepository(), db.userRepository(), db.loggedInUser(), db.participareRepository() );

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( host + "api/" )
                .build();

        RestCursaRepository restCursaRepository = retrofit.create( RestCursaRepository.class );
        RestMotociclistRepository restMotociclistRepository = retrofit.create( RestMotociclistRepository.class );
        RestUserRepository restUserRepository = retrofit.create( RestUserRepository.class );

        rest_srv = new RestCurseService(restCursaRepository,restMotociclistRepository,restUserRepository );

    }

    public void update_local()
    {
        if( !isOnline() )
        {
            return;
        }
        try
        {
            db_srv.clear_all();
            for( User u : rest_srv.getUsers() )
            {
                db_srv.addUser( u );
            }
            for( Motociclist m : rest_srv.getMotociclisti() )
            {
                db_srv.addMotociclist( m );
            }
            for( Cursa c : rest_srv.getCurse() )
            {
                db_srv.addCursa( c );
                for( Motociclist m : rest_srv.getParticipanti( c.getId() ) )
                {
                    Participare p = new Participare();
                    p.setCursaId( c.getId() );
                    p.setMotociclistId( m.getId() );
                    db_srv.addParticipare( p );
                }
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }

    }

    public User registerUser( User u ) throws Exception
    {
        if( isOnline() )
        {
            u = rest_srv.registerUser( u );
            return db_srv.addUser( u );
        }
        else
        {
            throw new IllegalStateException( "Action not possible offline. " );
        }
    }

    public User login( String username, String password ) throws Exception
    {
        if( isOnline() )
        {
            User u = rest_srv.login( username, password );
            db_srv.login( u.getId() );
            return u;
        }
        else
        {
            throw new IllegalStateException( "Action not possible offline. " );
        }

    }

    public User getLoggedUser()
    {
        return db_srv.getLoggedUser();
    }

    public List<Cursa> getCurse()
    {
        return db_srv.getCurse();
    }

    public List<Motociclist> getMotociclisti()
    {
        return db_srv.getMotociclisti();
    }

    public List< Participare > getParticipanti( int cursa_id )
    {
        return db_srv.getParticipanti( cursa_id );
    }


    public Cursa getCursa( int id )
    {
        return db_srv.getCursa( id );
    }

    public Motociclist getMotociclist( int id )
    {
        return db_srv.getMotociclist( id );
    }

    public Cursa addCursa( Cursa cursa ) throws Exception
    {
        if( isOnline() )
        {
            cursa = rest_srv.addCursa( cursa );
            return db_srv.addCursa( cursa );
        }
        else
        {
            throw new IllegalStateException( "Action not possible offline. " );
        }
    }

    public Motociclist addMotociclist( Motociclist m ) throws Exception
    {
        if( isOnline() )
        {
            m = rest_srv.addMotociclist( m );
            return db_srv.addMotociclist( m );
        }
        else
        {
            throw new IllegalStateException( "Action not possible offline. " );
        }
    }

    public Participare addParticipare( Participare p ) throws Exception
    {
        if( isOnline() )
        {
            p = rest_srv.addParticipare( p );
            return db_srv.addParticipare( p );
        }
        else
        {
            throw new IllegalStateException( "Action not possible offline. " );
        }
    }


    public void deleteCursa( Cursa el ) throws Exception
    {
        if( isOnline() )
        {
            rest_srv.deleteCursa( el );
            db_srv.deleteCursa( el );
        }
        else
        {
            throw new IllegalStateException( "Action not possible offline. " );
        }
    }

    public void deleteMotociclist( Motociclist el ) throws Exception
    {
        if( isOnline() )
        {
            rest_srv.deleteMotociclist( el );
            db_srv.deleteMotociclist( el );
        }
        else
        {
            throw new IllegalStateException( "Action not possible offline. " );
        }
    }

    public void logout()
    {
        db_srv.logout();
    }

    public void updateCursa( Cursa cursa ) throws Exception
    {
        if( isOnline() )
        {
            rest_srv.updateCursa( cursa );
            db_srv.updateCursa( cursa );
        }
        else
        {
            throw new IllegalStateException( "Action not possible offline. " );
        }
    }

    public void updateMotociclist( Motociclist m ) throws Exception
    {
        if( isOnline() )
        {
            rest_srv.updateMotociclist( m );
            db_srv.updateMotociclist( m );
        }
        else
        {
            throw new IllegalStateException( "Action not possible offline. " );
        }
    }
}
