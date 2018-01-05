package com.example.noonecares.curses.Repository.Rest;

import com.example.noonecares.curses.Domain.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Alex on 26.11.2017.
 */

public interface RestUserRepository
{
    final String basePath = "";

    @GET( basePath + "{id}" )
    Call< User > getById( @Path( "id" ) int Id );

    @GET( basePath )
    Call< List< User > > getAll();

    @POST( basePath )
    Call< User > add( @Body User elem );

    @PUT( basePath )
    public void update( @Body User elem );

    @POST( basePath )
    Call< UserLoginResponseDTO > login( @Body UserDTO elem );
}
