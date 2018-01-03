package com.awesomeproject.Repository.Local;

import android.arch.persistence.room.*;

import com.awesomeproject.Domain.ClassSchedule;
import com.awesomeproject.Domain.Feedback;
import com.awesomeproject.Domain.GymClass;
import com.awesomeproject.Domain.User;

/**
 * Created by Alex on 27.12.2017.
 */

@Database( entities = { User.class, Feedback.class, GymClass.class, ClassSchedule.class, DBStatics.class }, version = 1 )
@android.arch.persistence.room.TypeConverters( { TypeConverters.class } )

public abstract class AppDB extends RoomDatabase
{
    public abstract DBClassRepository classRepository();

    public abstract DBClassScheduleRepository classScheduleRepository();

    public abstract DBFeedbackRepository feedbackRepository();

    public abstract DBUserRepository userRepository();

    public abstract DBStaticsRepository loggedInUser();

}