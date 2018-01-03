package com.awesomeproject.Service;

import com.awesomeproject.Domain.ClassSchedule;
import com.awesomeproject.Domain.Difficulty;
import com.awesomeproject.Domain.Feedback;
import com.awesomeproject.Domain.GymClass;
import com.awesomeproject.Domain.Role;
import com.awesomeproject.Domain.User;
import com.awesomeproject.Repository.Interfaces.IClassRepository;
import com.awesomeproject.Repository.Interfaces.IClassScheduleRepository;
import com.awesomeproject.Repository.Interfaces.IFeedbackRepository;
import com.awesomeproject.Repository.Interfaces.IUserRepository;
import com.awesomeproject.Repository.Local.DBStaticsRepository;
import com.awesomeproject.Repository.Local.DBStatics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 26.11.2017.
 */

public class DBGymService implements Serializable
{
    IClassRepository classRepo;
    IClassScheduleRepository classScheduleRepo;
    IUserRepository userRepo;
    IFeedbackRepository feedbackRepo;
    DBStaticsRepository dbStaticsRepo;

    User loggedUser = null;

    public DBGymService( IClassRepository cR, IClassScheduleRepository csR, IUserRepository uR, IFeedbackRepository fR, DBStaticsRepository liu )
    {
        classRepo = cR;
        classScheduleRepo = csR;
        userRepo = uR;
        feedbackRepo = fR;
        dbStaticsRepo = liu;

        if( dbStaticsRepo.getDBStatics() == null )
        {
            dbStaticsRepo.clear();
            DBStatics dbs = new DBStatics();
            dbStaticsRepo.add( dbs );
/*
            User trainer = new User();
            trainer.setName( "Trainer 1" );
            trainer.setPassword( "qwertyuiop" );
            trainer.setRole( Role.TRAINER );
            trainer.setUsername( "trainer1" );
            trainer = registerUser( trainer );

            User user = new User();
            user.setName( "User_NAME" );
            user.setPassword( "passwd" );
            user.setRole( Role.USER );
            user.setUsername( "user" );
            user = registerUser( user );

            GymClass c1 = new GymClass();
            c1.setName( "Class 1" );
            c1.getClassTrainers().add( trainer.getId() );
            c1 = addClass( c1 );

            GymClass c2 = new GymClass();
            c2.setName( "Class 2" );
            c2.getClassTrainers().add( trainer.getId() );
            c1 = addClass( c2 );

            ClassSchedule cs = new ClassSchedule();
            cs.setCapacity( 5 );
            cs.setClassId( c1.getId() );
            cs.setDate( new Date() );
            cs.setDifficulty( Difficulty.ADVANCED );
            cs.setRoom( "A3" );
            cs.setTrainerId( trainer.getId() );
            addClassSchedule( cs );

            User admin = new User();
            admin.setName( "ADMIN" );
            admin.setPassword( "" );
            admin.setRole( Role.ADMIN );
            admin.setUsername( "admin" );
            admin = registerUser( admin );

            Feedback f = new Feedback();
            f.setText( "YASSS!" );
            f.setRating( ( short )4 );
            f.setUserId( user.getId() );
            f.setTrainerId( trainer.getId() );
            f.setId( createNewId() );
            feedbackRepo.add( f );*/
        }
    }

    public User registerUser( User u )
    {
        if( u.getId() == 0 )
        {
            u.setId( createNewId() );
        }
        userRepo.add( u );
        return u;
    }

    public User login( int userId ) throws Exception
    {
        dbStaticsRepo.logout();
        DBStatics liu = dbStaticsRepo.getDBStatics();
        liu.setLoggedUserID( userId );
        dbStaticsRepo.setDBStatics( liu );
        return userRepo.getById( userId );
    }

    public User getLoggedUser()
    {
        try
        {
            int id = dbStaticsRepo.getDBStatics().getLoggedUserID();
            loggedUser = userRepo.getById( id );
        }
        catch( Exception e )
        {
            loggedUser = null;
        }
        return loggedUser;
    }

    public GymClass addClass( GymClass c ) throws IllegalArgumentException
    {
        boolean ok = true;
        String error = "";
        if( ok )
        {
            if( c.getId() == 0 )
            {
                c.setId( createNewId() );
            }
            classRepo.add( c );
            return c;
        }
        else
        {
            throw new IllegalArgumentException( error );
        }
    }

    public List<ClassSchedule> getClassSchedule( int class_id )
    {
        ArrayList< ClassSchedule > css = new ArrayList<>();
        for( ClassSchedule cs : classScheduleRepo.getAll() )
        {
            if( cs.getClassId() == class_id )
            {
                css.add( cs );
            }
        }
        return css;

    }

    public ClassSchedule addClassSchedule( ClassSchedule cs ) throws IllegalArgumentException
    {
        boolean ok = true;
        String error = "";
        if( cs.getCapacity() <= 0 )
        {
            ok = false;
            error += "Capacity must be positive\n";
        }
        if( ( userRepo.getById( cs.getTrainerId() ) == null ) || ( userRepo.getById( cs.getTrainerId() ).getRole() != Role.TRAINER ) )
        {
            ok = false;
            error += "The trainer id is not valid\n";
        }
        if( ok )
        {
            if( cs.getId() == 0 )
            {
                cs.setId( createNewId() );
            }
            classScheduleRepo.add( cs );
            return cs;
        }
        else
        {
            throw new IllegalArgumentException( error );
        }
    }

    public void updateClassSchedule( ClassSchedule cs ) throws IllegalArgumentException
    {
        boolean ok = true;
        String error = "";

        if( cs.getCapacity() <= 0 )
        {
            ok = false;
            error += "Capacity must be positive\n";
        }
        if( ( userRepo.getById( cs.getTrainerId() ) == null ) || ( userRepo.getById( cs.getTrainerId() ).getRole() != Role.TRAINER ) )
        {
            ok = false;
            error += "The trainer id is not valid\n";
        }
        if( ok )
        {
            classScheduleRepo.update( cs );
        }
        else
        {
            throw new IllegalArgumentException( error );
        }
    }

    public void populate()
    {

    }

    public Collection<GymClass> getClasses()
    {
        return classRepo.getAll();
    }

    public Collection<User> getUsers()
    {
        return userRepo.getAll();
    }

    public GymClass getClassById( int id )
    {
        return classRepo.getById( id );
    }

    public User getUserById( int id )
    {
        return userRepo.getById( id );
    }

    public ClassSchedule getClassScheduleById( int id )
    {
        return classScheduleRepo.getById( id );
    }

    public void updateClass( GymClass gymClass )
    {
        boolean ok = true;
        String error = "";

        if( ok )
        {
            classRepo.update( gymClass );
        }
        else
        {
            throw new IllegalArgumentException( error );
        }

    }

    public void deleteClassSchedule( ClassSchedule cs )
    {
        classScheduleRepo.remove( cs );
    }

    public Feedback giveFeedback( Feedback feedback )
    {
        if( userRepo.getById( feedback.getUserId() ) != null )
        {
            if( userRepo.getById( feedback.getUserId() ).getRole() != Role.USER )
            {
                throw new IllegalArgumentException( "Only users can give feedback" );
            }
        }
        else
        {
            throw new IllegalArgumentException( "Invalid user ID" );
        }

        if( userRepo.getById( feedback.getTrainerId() ) != null )
        {
            if( userRepo.getById( feedback.getTrainerId() ).getRole() != Role.TRAINER )
            {
                throw new IllegalArgumentException( "Feedback can only be given to trainers" );
            }
        }
        else
        {
            throw new IllegalArgumentException( "Invalid trainer ID" );
        }

        Feedback before = null;
        for( Feedback f : feedbackRepo.getAll() )
        {
            if( f.getTrainerId() == feedback.getTrainerId() && f.getUserId() == feedback.getUserId() )
            {
                before = f;
                break;
            }
        }
        if( before == null )
        {
            if( feedback.getId() == 0 )
            {
                feedback.setId( createNewId() );
            }
            feedbackRepo.add( feedback );
            return feedback;
        }
        else
        {
            before.setRating( feedback.getRating() );
            before.setText( feedback.getText() );
            feedbackRepo.update( before );
            return before;
        }

    }

    public List< Feedback > getFeedback( int trainer_id )
    {
        return feedbackRepo.getTrainerFeedback( trainer_id );
    }

    public void logout()
    {
        loggedUser = null;
        dbStaticsRepo.logout();
    }

    private int createNewId()
    {
        DBStatics dbs = dbStaticsRepo.getDBStatics();
        int id = dbs.getLastId();
        dbs.incrementId();
        dbStaticsRepo.setDBStatics( dbs );
        return id;
    }

    public void clear_all()
    {
        for( User u : userRepo.getAll() )
        {
            userRepo.remove( u );
        }
        for( ClassSchedule cs : classScheduleRepo.getAll() )
        {
            classScheduleRepo.remove( cs );
        }
        for( GymClass c : classRepo.getAll() )
        {
            classRepo.remove( c );
        }

    }
}
