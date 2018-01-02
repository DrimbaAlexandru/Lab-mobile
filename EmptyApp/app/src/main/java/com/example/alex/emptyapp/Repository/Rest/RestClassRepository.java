package com.example.alex.emptyapp.Repository.Rest;

import com.example.alex.emptyapp.Domain.GymClass;
import com.example.alex.emptyapp.Repository.Interfaces.IClassRepository;

import java.util.List;

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

public interface RestClassRepository
{
    final String basePath = "Class/";

    @GET( basePath + "{id}" )
    Call< GymClass > getById( @Path( "id" ) int Id );

    @GET( "classes/" )
    Call< List< GymClass > > getAll();

    @POST( basePath )
    Call< GymClass > add( @Body GymClass elem );

    @PUT( basePath )
    public void update( @Body GymClass elem );

    @DELETE( basePath + "{id}" )
    public void remove( @Path( "id" ) int Id );

}

