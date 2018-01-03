package com.awesomeproject.Repository.Rest;

import com.awesomeproject.Domain.ClassSchedule;
import com.awesomeproject.Domain.GymClass;
import com.awesomeproject.Repository.Interfaces.IClassScheduleRepository;

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
    Call< ClassSchedule > add( @Body ClassScheduleDTO elem );

    @PUT( basePath + "{id}" )
    public Call< Void > update( @Body ClassScheduleDTO elem, @Path( "id" ) int Id );

    @DELETE( basePath + "{id}" )
    public Call<Void> remove( @Path( "id" ) int Id );
}
