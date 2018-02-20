package com.example.alex.emptyapp.Service;

import android.util.Pair;

import com.example.alex.emptyapp.DTO.LoginDTO;
import com.example.alex.emptyapp.DTO.LoginResponseDTO;
import com.example.alex.emptyapp.DTO.PostMessageDTO;
import com.example.alex.emptyapp.Domain.Mesaj;
import com.example.alex.emptyapp.Repository.Rest.RestMesajeRepository;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

/**
 * Created by Alex on 28.12.2017.
 */

public class RestMesajeService
{
    RestMesajeRepository mesajeRepository;

    public RestMesajeService( RestMesajeRepository rip )
    {
        mesajeRepository = rip;
    }

    Pair< ResponseStatus, LoginResponseDTO > login( String username )
    {
        try
        {
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setUsername( username );
            Response< LoginResponseDTO > resp = mesajeRepository.login( loginDTO ).execute();

            if( resp.code() == 201 )
            {
                return new Pair<>( ResponseStatus.OK, resp.body() );
            }
            else
            {
                return new Pair<>( ResponseStatus.REFUSED_BY_SERVER, null );
            }
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
        return new Pair<>( ResponseStatus.NETWORK_ERROR, null );
    }

    Pair< ResponseStatus, List< Mesaj > > getMesaje( String token, long created )
    {
        try
        {
            Response< List< Mesaj > > resp = mesajeRepository.getMesaje( token, created ).execute();

            if( resp.code() == 200 )
            {
                return new Pair<>( ResponseStatus.OK, resp.body() );
            }
            else
            {
                return new Pair<>( ResponseStatus.REFUSED_BY_SERVER, null );
            }
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
        return new Pair<>( ResponseStatus.NETWORK_ERROR, null );
    }

    ResponseStatus putMesage( String token, String text )
    {
        try
        {
            PostMessageDTO dto = new PostMessageDTO();
            dto.setText( text );

            Response< Void > resp = mesajeRepository.postMessage( token, dto ).execute();

            if( resp.code() == 200 )
            {
                return ResponseStatus.OK;
            }
            else
            {
                return ResponseStatus.REFUSED_BY_SERVER;
            }
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
        return ResponseStatus.NETWORK_ERROR;
    }

    ResponseStatus logout( String token )
    {
        try
        {
            Response< Void > resp = mesajeRepository.logout( token ).execute();

            if( resp.code() == 200 )
            {
                return ResponseStatus.OK;
            }
            else
            {
                return ResponseStatus.REFUSED_BY_SERVER;
            }
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
        return ResponseStatus.NETWORK_ERROR;
    }

}
