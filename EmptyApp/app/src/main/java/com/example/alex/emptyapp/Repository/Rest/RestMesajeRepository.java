package com.example.alex.emptyapp.Repository.Rest;

import com.example.alex.emptyapp.DTO.LoginDTO;
import com.example.alex.emptyapp.DTO.LoginResponseDTO;
import com.example.alex.emptyapp.DTO.PostMessageDTO;
import com.example.alex.emptyapp.Domain.Mesaj;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Alex on 26.11.2017.
 */

public interface RestMesajeRepository
{
    @POST( "login" )
    Call<LoginResponseDTO> login( @Body LoginDTO body);

    @GET( "message" )
    Call< List< Mesaj > > getMesaje( @Header( "token" ) String token, @Query( "created" ) long created );

    @POST
    Call< Void > postMessage( @Header( "token" ) String token, @Body PostMessageDTO body );

    @POST
    Call< Void > logout( @Header( "token" ) String token );
}
