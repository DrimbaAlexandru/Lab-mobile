package com.example.alex.emptyapp.Repository.Interfaces;

import com.example.alex.emptyapp.Domain.Feedback;

import java.util.List;

/**
 * Created by Alex on 26.12.2017.
 */

public interface IFeedbackRepository extends IRepository<Feedback>
{
    List< Feedback > getTrainerFeedback( int trainer_id );
}
