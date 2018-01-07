package com.awesomeproject.Repository.Rest;

import com.awesomeproject.Domain.Cursa;
import com.awesomeproject.Domain.Motociclist;
import com.awesomeproject.Domain.Participare;

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

public interface RestCursaRepository
{
    final String basePath = "Cursa";

    @GET( basePath + "/{id}" )
    Call< Cursa > getById( @Path( "id" ) int Id );

    @GET( basePath )
    Call< List< Cursa > > getAll();

    @POST( basePath )
    Call< Cursa > add( @Body Cursa elem );

    @PUT( basePath )
    public Call< Void > update( @Body Cursa elem );

    @DELETE( basePath + "/{id}" )
    public Call< Void > remove( @Path( "id" ) int Id );

    @GET( basePath + "/{id}/Participants" )
    Call< List< Motociclist > > getParticipanti( @Path( "id" ) int Id );

    @POST( basePath + "/{id_c}/Participants/{id_m}" )
    Call< Void > addParticipant( @Path( "id_c" ) int IdCursa, @Path( "id_m" ) int IdMotociclist );

}

