package com.example.noonecares.curses.Repository.Rest;

import com.example.noonecares.curses.Domain.Motociclist;

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

public interface RestMotociclistRepository
{
    final String basePath = "Motociclist/";

    @GET( basePath + "{id}" )
    Call< Motociclist > getById( @Path( "id" ) int Id );

    @GET( basePath )
    Call< List< Motociclist > > getAll();

    @POST( basePath )
    Call< Motociclist > add( @Body Motociclist elem );

    @PUT( basePath )
    public Call< Void > update( @Body Motociclist elem );

    @DELETE( basePath + "{id}" )
    public Call< Void > remove( @Path( "id" ) int Id );
}

