package com.example.alex.emptyapp.Repository.Rest;

import com.example.alex.emptyapp.Domain.InregistrareProdus;
import com.example.alex.emptyapp.Domain.ProductDescription;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Alex on 26.11.2017.
 */

public interface RestProductRepository
{
    @POST( "Product" )
    Call< Void > registerProduct( @Body InregistrareProdus produs );

    @GET( "ProductDescription" )
    Call< ProductDescriptionResponse > getProductsPage( @Query( "q" ) String text, @Query( "page" ) int page );

    @GET( "Product/report" )
    Call< ReportResponse > getRaport( @Query( "location" ) String location );


}
