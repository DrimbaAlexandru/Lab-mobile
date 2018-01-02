package com.example.alex.emptyapp.Repository.Mock;

import com.example.alex.emptyapp.Domain.Feedback;
import com.example.alex.emptyapp.Repository.Interfaces.IFeedbackRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 26.12.2017.
 */

public class MockFeedbackRepository extends MockRepository< Feedback > implements IFeedbackRepository
{

    @Override
    public List< Feedback > getTrainerFeedback( int trainer_id )
    {
        ArrayList< Feedback > feeds = new ArrayList<>();
        for( Feedback f : getAll() )
        {
            if( f.getTrainerId() == trainer_id )
            {
                feeds.add( f );
            }
        }
        return feeds;
    }
}
