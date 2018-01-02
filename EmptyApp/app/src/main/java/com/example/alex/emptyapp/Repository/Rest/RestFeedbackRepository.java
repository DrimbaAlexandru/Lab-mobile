package com.example.alex.emptyapp.Repository.Rest;

import com.example.alex.emptyapp.Domain.ClassSchedule;
import com.example.alex.emptyapp.Domain.Feedback;
import com.example.alex.emptyapp.Repository.Interfaces.IFeedbackRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Alex on 26.12.2017.
 */

public interface RestFeedbackRepository
{

    final String basePath = "";

    @GET( basePath + "{id}" )
    Call< Feedback > getById( @Path( "id" ) int Id );

    @GET( basePath )
    Call< List< Feedback > > getAll();

    @POST( basePath )
    Call< Feedback > add( @Body Feedback elem );

    @PUT( basePath )
    public void update( @Body Feedback elem );

    @DELETE( basePath + "{id}" )
    public void remove( @Path( "id" ) int Id );
}
