package com.example.alex.emptyapp.Repository.Mock;

import com.example.alex.emptyapp.Domain.ClassSchedule;
import com.example.alex.emptyapp.Repository.Interfaces.IClassScheduleRepository;

import java.util.Set;

/**
 * Created by Alex on 26.11.2017.
 */

public class MockClassScheduleRepository extends MockRepository< ClassSchedule > implements IClassScheduleRepository
{

    //@Override
    public Set< Integer > getParticipants( int csId ) throws Exception
    {
        if( values.containsKey( csId ) )
        {
            return values.get( csId ).getClassParticipants();
        }
        else
        {
            throw new Exception( "Class Schedule id doesn't exist!" );
        }
    }

    //@Override
    public void enrollUser( int userId, int csId ) throws Exception
    {
        ClassSchedule cs = values.get( csId );
        if( cs != null )
        {
            if( cs.getClassParticipants().size() < cs.getCapacity() )
            {
                cs.getClassParticipants().add( userId );
                values.put( cs.getId(), cs );
            }
            else
            {
                throw new Exception( "No more available places!" );
            }
        }
        else
        {
            throw new Exception( "Class Schedule id doesn't exist!" );
        }
    }

    //@Override
    public void unenrollUser( int userId, int csId ) throws Exception
    {
        ClassSchedule cs = values.get( csId );
        if( cs != null )
        {
            if( cs.getClassParticipants().contains( userId ) )
            {
                cs.getClassParticipants().remove( userId );
                values.put( cs.getId(), cs );
            }
            else
            {
                throw new Exception( "User wasn't enrolled to this class schedule!" );
            }
        }
        else
        {
            throw new Exception( "Class Schedule id doesn't exist!" );
        }
    }
}
