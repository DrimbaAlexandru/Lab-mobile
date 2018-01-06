package com.example.alex.emptyapp.Repository.Rest;

import com.example.alex.emptyapp.Domain.Feedback;
import com.example.alex.emptyapp.Domain.User;
import com.example.alex.emptyapp.Repository.Interfaces.IUserRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by Alex on 26.11.2017.
 */

public interface RestUserRepository
{
    final String basePath = "users";

    @GET( basePath )
    Call< List< UserLoginResponseDTO > > getAll();

    @PUT( basePath )
    public void update( @Body User elem );

    @POST( basePath + "/login" )
    Call< UserLoginResponseDTO > login( @Body UserDTO elem );
}
