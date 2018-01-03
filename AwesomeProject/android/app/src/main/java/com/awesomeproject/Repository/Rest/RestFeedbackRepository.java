package com.awesomeproject.Repository.Rest;

import com.awesomeproject.Domain.ClassSchedule;
import com.awesomeproject.Domain.Feedback;
import com.awesomeproject.Repository.Interfaces.IFeedbackRepository;

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

    final String basePath = "feedback";

    @GET( basePath + "/{id}" )
    Call< List< Feedback > > getByTrainerId( @Path( "id" ) int Id );

    @GET( basePath )
    Call< List< Feedback > > getAll();

    @POST( basePath )
    Call< Feedback > give( @Body Feedback elem );

}
