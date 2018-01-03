package com.awesomeproject.Repository.Interfaces;

import com.awesomeproject.Domain.Feedback;

import java.util.List;

/**
 * Created by Alex on 26.12.2017.
 */

public interface IFeedbackRepository extends IRepository<Feedback>
{
    List< Feedback > getTrainerFeedback( int trainer_id );
}
