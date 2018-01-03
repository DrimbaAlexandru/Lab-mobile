package com.awesomeproject.Service;

import com.awesomeproject.Domain.ClassSchedule;
import com.awesomeproject.Domain.Feedback;
import com.awesomeproject.Domain.GymClass;
import com.awesomeproject.Domain.Role;
import com.awesomeproject.Domain.User;
import com.awesomeproject.Repository.Rest.ClassScheduleDTO;
import com.awesomeproject.Repository.Rest.RestClassRepository;
import com.awesomeproject.Repository.Rest.RestClassScheduleRepository;
import com.awesomeproject.Repository.Rest.RestFeedbackRepository;
import com.awesomeproject.Repository.Rest.RestUserRepository;
import com.awesomeproject.Repository.Rest.UserDTO;
import com.awesomeproject.Repository.Rest.UserLoginResponseDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Alex on 28.12.2017.
 */

public class RestGymService
{
    RestClassRepository classRepo;
    RestClassScheduleRepository classScheduleRepo;
    RestUserRepository userRepo;
    RestFeedbackRepository feedbackRepo;

    public RestGymService( RestClassRepository cR, RestClassScheduleRepository csR, RestUserRepository uR, RestFeedbackRepository fR )
    {
        classRepo = cR;
        classScheduleRepo = csR;
        userRepo = uR;
        feedbackRepo = fR;
    }

    public int login( String username, String password ) throws Exception
    {
        Call< UserLoginResponseDTO > call = userRepo.login( new UserDTO( username, password ) );
        Response< UserLoginResponseDTO > resp = call.execute();
        try
        {
            return resp.body().Id;
        }
        catch( Exception e )
        {
            return 0;
        }
    }

    public GymClass addClass( GymClass c ) throws IOException
    {
        Call< GymClass > call = classRepo.add( c );
        Response< GymClass > resp = call.execute();
        return resp.body();
    }

    public List<ClassSchedule > getClassSchedules() throws IOException
    {
        return classScheduleRepo.getAll().execute().body();

    }

    public ClassSchedule addClassSchedule( ClassSchedule cs ) throws IOException
    {
        return classScheduleRepo.add( ClassScheduleDTO.fromClassSchedule( cs ) ).execute().body();
    }

    public void updateClassSchedule( ClassSchedule cs ) throws Exception
    {
        classScheduleRepo.update( ClassScheduleDTO.fromClassSchedule( cs ), cs.getId() ).execute();
    }

    public Collection<GymClass> getClasses() throws IOException
    {
        return classRepo.getAll().execute().body();
    }

    public Collection<User> getUsers() throws IOException
    {
        List< User > users = new ArrayList<>();

        for( UserLoginResponseDTO u : userRepo.getAll().execute().body() )
        {
            users.add( u.toUser() );
        }
        return users;
    }

    public GymClass getClassById( int id ) throws IOException
    {
        return classRepo.getById( id ).execute().body();
    }

    public void updateClass( GymClass gymClass ) throws IOException
    {
        classRepo.update( gymClass, gymClass.getId() ).execute();
    }

    public void deleteClassSchedule( ClassSchedule cs ) throws Exception
    {
        classScheduleRepo.remove( cs.getId() ).execute();
    }

    public Feedback giveFeedback( Feedback feedback ) throws IOException
    {
        return feedbackRepo.give( feedback ).execute().body();
    }

    public List< Feedback > getFeedback( ) throws IOException
    {
        return feedbackRepo.getAll().execute().body();
    }

}
