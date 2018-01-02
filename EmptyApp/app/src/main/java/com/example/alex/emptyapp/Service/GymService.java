package com.example.alex.emptyapp.Service;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.alex.emptyapp.Domain.ClassSchedule;
import com.example.alex.emptyapp.Domain.Difficulty;
import com.example.alex.emptyapp.Domain.Feedback;
import com.example.alex.emptyapp.Domain.GymClass;
import com.example.alex.emptyapp.Domain.Role;
import com.example.alex.emptyapp.Domain.User;
import com.example.alex.emptyapp.Repository.Local.AppDB;
import com.example.alex.emptyapp.Repository.Local.DBStatics;
import com.example.alex.emptyapp.Repository.Rest.RestClassRepository;
import com.example.alex.emptyapp.Repository.Rest.RestClassScheduleRepository;
import com.example.alex.emptyapp.Repository.Rest.RestFeedbackRepository;
import com.example.alex.emptyapp.Repository.Rest.RestUserRepository;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import retrofit2.Retrofit;

/**
 * Created by Alex on 28.12.2017.
 */

public class GymService
{
    private final static String host = "http://192.168.0.101:63288/";
    private DBGymService db_srv;
    private RestGymService rest_srv;

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

    public GymService( Context context )
    {
        AppDB db = Room.databaseBuilder( context,
                                         AppDB.class, "PC-Local-DB" ).allowMainThreadQueries().build();
        db_srv = new DBGymService( db.classRepository(), db.classScheduleRepository(), db.userRepository(), db.feedbackRepository(), db.loggedInUser() );

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( host + "api/" )
                .build();

        RestClassRepository restClassRepository = retrofit.create( RestClassRepository.class );
        RestClassScheduleRepository restClassScheduleRepository = retrofit.create( RestClassScheduleRepository.class );
        RestFeedbackRepository restFeedbackRepository = retrofit.create( RestFeedbackRepository.class );
        RestUserRepository restUserRepository = retrofit.create( RestUserRepository.class );

        rest_srv = new RestGymService( restClassRepository, restClassScheduleRepository, restUserRepository, restFeedbackRepository );

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
            for( GymClass c : rest_srv.getClasses() )
            {
                db_srv.addClass( c );
            }
            for( ClassSchedule cs : rest_srv.getClassSchedules() )
            {
                db_srv.addClassSchedule( cs );
            }
            for( User u : rest_srv.getUsers() )
            {
                db_srv.registerUser( u );
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
            return db_srv.registerUser( u );
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

    public GymClass addClass( GymClass c ) throws Exception
    {
        if( isOnline() )
        {
            c = rest_srv.addClass( c );
            return db_srv.addClass( c );
        }
        else
        {
            throw new IllegalStateException( "Action not possible offline. " );
        }
    }

    public List<ClassSchedule> getClassSchedule( int class_id )
    {
        return db_srv.getClassSchedule( class_id );
    }

    public ClassSchedule addClassSchedule( ClassSchedule cs ) throws Exception
    {
        if( isOnline() )
        {
            cs = rest_srv.addClassSchedule( cs );
            return db_srv.addClassSchedule( cs );
        }
        else
        {
            throw new IllegalStateException( "Action not possible offline. " );
        }
    }

    public void updateClassSchedule( ClassSchedule cs ) throws Exception
    {
        if( isOnline() )
        {
            rest_srv.updateClassSchedule( cs );
            db_srv.updateClassSchedule( cs );
        }
        else
        {
            throw new IllegalStateException( "Action not possible offline. " );
        }
    }

    public Collection<GymClass> getClasses()
    {
        return db_srv.getClasses();
    }

    public Collection<User> getUsers()
    {
        return db_srv.getUsers();
    }

    public GymClass getClassById( int id )
    {
        return db_srv.getClassById( id );
    }

    public User getUserById( int id )
    {
        return db_srv.getUserById( id );
    }

    public ClassSchedule getClassScheduleById( int id )
    {
        return db_srv.getClassScheduleById( id );
    }

    public void updateClass( GymClass gymClass )
    {
        if( isOnline() )
        {
            rest_srv.updateClass( gymClass );
            db_srv.updateClass( gymClass );

        }
        else
        {
            throw new IllegalStateException( "Action not possible offline. " );
        }

    }

    public void deleteClassSchedule( ClassSchedule cs )
    {
        if( isOnline() )
        {
            rest_srv.deleteClassSchedule( cs );
            db_srv.deleteClassSchedule( cs );
        }
        else
        {
            throw new IllegalStateException( "Action not possible offline. " );
        }
    }

    public Feedback giveFeedback( Feedback feedback )
    {
        return db_srv.giveFeedback( feedback );
    }

    public List< Feedback > getFeedback( int trainer_id )
    {
        return db_srv.getFeedback( trainer_id );
    }

    public void logout()
    {
        db_srv.logout();
    }
}
