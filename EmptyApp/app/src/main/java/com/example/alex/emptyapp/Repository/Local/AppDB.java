package com.example.alex.emptyapp.Repository.Local;

import android.arch.persistence.room.*;

import com.example.alex.emptyapp.Domain.ClassSchedule;
import com.example.alex.emptyapp.Domain.Feedback;
import com.example.alex.emptyapp.Domain.GymClass;
import com.example.alex.emptyapp.Domain.User;

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