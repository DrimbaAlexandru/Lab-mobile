package com.awesomeproject.Service;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.StrictMode;

import com.awesomeproject.Domain.ClassSchedule;
import com.awesomeproject.Domain.Difficulty;
import com.awesomeproject.Domain.Feedback;
import com.awesomeproject.Domain.GymClass;
import com.awesomeproject.Domain.Role;
import com.awesomeproject.Domain.User;
import com.awesomeproject.Repository.Local.AppDB;
import com.awesomeproject.Repository.Local.DBStatics;
import com.awesomeproject.Repository.Rest.RestClassRepository;
import com.awesomeproject.Repository.Rest.RestClassScheduleRepository;
import com.awesomeproject.Repository.Rest.RestFeedbackRepository;
import com.awesomeproject.Repository.Rest.RestUserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alex on 28.12.2017.
 */

public class GymService
{
    private final static String host = "http://192.168.43.59:63288";
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
        catch( Exception e )
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
                .baseUrl( host + "/api/" )
                .addConverterFactory( GsonConverterFactory.create( new GsonBuilder().setDateFormat( "yyyy-MM-dd HH:mm:ss" ).create() ) )
                .build();

        RestClassRepository restClassRepository = retrofit.create( RestClassRepository.class );
        RestClassScheduleRepository restClassScheduleRepository = retrofit.create( RestClassScheduleRepository.class );
        RestFeedbackRepository restFeedbackRepository = retrofit.create( RestFeedbackRepository.class );
        RestUserRepository restUserRepository = retrofit.create( RestUserRepository.class );

        rest_srv = new RestGymService( restClassRepository, restClassScheduleRepository, restUserRepository, restFeedbackRepository );

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

    }

    public void update_local() throws Exception
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
            for( User u : rest_srv.getUsers() )
            {
                db_srv.registerUser( u );
            }
            for( ClassSchedule cs : rest_srv.getClassSchedules() )
            {
                db_srv.addClassSchedule( cs );
            }

        }
        catch( Exception e )
        {
            throw e;
        }

    }

    public User login( String username, String password ) throws Exception
    {
        if( isOnline() )
        {
            int id = rest_srv.login( username, password );
            if( id != 0 )
            {
                return db_srv.login( id );
            }
            else
            {
                throw new IllegalArgumentException( "User or password is incorrect" );
            }

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
            c.setId( 0 );
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
            cs.setId( 0 );
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

    public void updateClass( GymClass gymClass ) throws IOException
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

    public void deleteClassSchedule( ClassSchedule cs ) throws Exception
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

    public Feedback giveFeedback( Feedback feedback ) throws Exception
    {
        if( isOnline() )
        {
            feedback = rest_srv.giveFeedback( feedback );
            return db_srv.giveFeedback( feedback );
        }
        else
        {
            throw new IllegalStateException( "Action not possible offline. " );
        }
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
