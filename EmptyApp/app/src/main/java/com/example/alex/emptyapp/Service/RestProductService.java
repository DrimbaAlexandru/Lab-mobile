package com.example.alex.emptyapp.Service;

import android.util.Pair;

import com.example.alex.emptyapp.Domain.InregistrareProdus;
import com.example.alex.emptyapp.Repository.Rest.ProductDescriptionResponse;
import com.example.alex.emptyapp.Repository.Rest.ReportResponse;
import com.example.alex.emptyapp.Repository.Rest.RestProductRepository;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by Alex on 28.12.2017.
 */

public class RestProductService
{
    RestProductRepository productRepository;

    public RestProductService( RestProductRepository rip )
    {
        productRepository = rip;
    }

    Pair< ResponseStatus, ProductDescriptionResponse > getProductDescriptionPage( String text, int page )
    {
        try
        {
            Response< ProductDescriptionResponse > resp = productRepository.getProductsPage( text, page ).execute();

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

    ResponseStatus sendInregistrareProdus( InregistrareProdus rec )
    {
        try
        {
            Response< Void > response = productRepository.registerProduct( rec ).execute();
            if( response.code() == 201 )
            {
                return ResponseStatus.OK;
            }
            else
            {
                return ResponseStatus.REFUSED_BY_SERVER;
            }
        }
        catch( Exception e )
        {
            return ResponseStatus.NETWORK_ERROR;
        }
    }

    Pair< ResponseStatus, String > getRaport( String location )
    {
        try
        {
            Response< ReportResponse > response = productRepository.getRaport( location ).execute();
            if( response.code() == 200 )
            {
                return new Pair<>( ResponseStatus.OK, response.body().report );
            }
            else
            {
                return new Pair<>( ResponseStatus.REFUSED_BY_SERVER, null );
            }
        }
        catch( Exception e )
        {
            return new Pair<>( ResponseStatus.NETWORK_ERROR, null );
        }
    }


}
