package com.example.alex.emptyapp.Repository.Rest;

import com.example.alex.emptyapp.Domain.ClassSchedule;
import com.example.alex.emptyapp.Domain.GymClass;
import com.example.alex.emptyapp.Repository.Interfaces.IClassScheduleRepository;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Alex on 26.11.2017.
 */

public interface RestClassScheduleRepository
{
    final String basePath = "ClassSchedules/";

    @GET( basePath + "{id}" )
    Call< ClassSchedule > getById( @Path( "id" ) int Id );

    @GET( basePath )
    Call< List< ClassSchedule > > getAll();

    @POST( basePath )
    Call< ClassSchedule > add( @Body ClassSchedule elem );

    @PUT( basePath )
    public void update( @Body ClassSchedule elem );

    @DELETE( basePath + "{id}" )
    public void remove( @Path( "id" ) int Id );
}
