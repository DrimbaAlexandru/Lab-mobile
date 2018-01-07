package com.awesomeproject.Service;

import com.awesomeproject.Domain.Cursa;
import com.awesomeproject.Domain.Motociclist;
import com.awesomeproject.Domain.Participare;
import com.awesomeproject.Domain.User;
import com.awesomeproject.Repository.Interfaces.ICursaRepository;
import com.awesomeproject.Repository.Interfaces.IMotociclistRepository;
import com.awesomeproject.Repository.Interfaces.IUserRepository;
import com.awesomeproject.Repository.Local.DBParticipareRepository;
import com.awesomeproject.Repository.Local.DBStaticsRepository;
import com.awesomeproject.Repository.Local.DBStatics;

import java.util.List;

/**
 * Created by Alex on 26.11.2017.
 */

public class DBCurseService
{
    ICursaRepository cursaRepo;
    IMotociclistRepository motociclistRepo;
    IUserRepository userRepo;
    DBStaticsRepository dbStaticsRepo;
    DBParticipareRepository partRepo;

    User loggedUser = null;

    public DBCurseService( ICursaRepository cR, IMotociclistRepository csR, IUserRepository uR, DBStaticsRepository liu, DBParticipareRepository pR )
    {
        cursaRepo = cR;
        motociclistRepo = csR;
        userRepo = uR;
        dbStaticsRepo = liu;
        partRepo = pR;

        if( dbStaticsRepo.getDBStatics() == null )
        {
            dbStaticsRepo.clear();
            DBStatics dbs = new DBStatics();
            dbStaticsRepo.add( dbs );
        }
    }

    public User addUser( User u )
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

    public Cursa addCursa( Cursa c ) throws IllegalArgumentException
    {
        boolean ok = true;
        String error = "";

        error = validateCursa( c );
        ok = error.equals( "" );

        if( ok )
        {
            if( c.getId() == 0 )
            {
                c.setId( createNewId() );
            }
            cursaRepo.add( c );
            return c;
        }
        else
        {
            throw new IllegalArgumentException( error );
        }
    }

    public Participare addParticipare( Participare p ) throws IllegalArgumentException
    {
        boolean ok = true;
        String error = "";

        error = validateParticipare( p );
        ok = error.equals( "" );

        if( ok )
        {
            if( p.getId() == 0 )
            {
                p.setId( createNewId() );
            }
            partRepo.add( p );
            Cursa c = cursaRepo.getById( p.getCursaId() );
            c.setLoc_ramas( c.getLoc_ramas() - 1 );
            updateCursa( c );
            return p;
        }
        else
        {
            throw new IllegalArgumentException( error );
        }
    }

    public Motociclist addMotociclist( Motociclist m ) throws IllegalArgumentException
    {
        boolean ok = true;
        String error = "";

        error = validateMotociclist( m );
        ok = error.equals( "" );

        if( ok )
        {
            if( m.getId() == 0 )
            {
                m.setId( createNewId() );
            }
            motociclistRepo.add( m );
            return m;
        }
        else
        {
            throw new IllegalArgumentException( error );
        }
    }


    private String validateMotociclist( Motociclist m )
    {
        return "";
    }

    private String validateParticipare( Participare p )
    {
        String err = "";
        Cursa c = cursaRepo.getById( p.getCursaId() );
        Motociclist m = motociclistRepo.getById( p.getMotociclistId() );

        if( m == null )
        {
            err += "Motociclist id is not valid\n";
        }
        if( c == null )
        {
            err += "Cursa id is not valid\n";
        }
        else
        {
            if( c.getLoc_ramas() < 1 )
            {
                err += "No more available places\n";
            }
        }
        return err;
    }

    private String validateCursa( Cursa c )
    {
        String err = "";
        return err;
    }


    public void updateCursa( Cursa c ) throws IllegalArgumentException
    {
        if( cursaRepo.getById( c.getId() ) != null )
        {
            cursaRepo.update( c );
        }
        else
        {
            throw new IllegalArgumentException( "Id not valid" );
        }
    }

    public void updateMotociclist( Motociclist m ) throws IllegalArgumentException
    {
        if( motociclistRepo.getById( m.getId() ) != null )
        {
            motociclistRepo.update( m );
        }
        else
        {
            throw new IllegalArgumentException( "Id not valid" );
        }
    }


    public Cursa getCursa( int cursa_id )
    {
        return cursaRepo.getById( cursa_id );
    }

    public Motociclist getMotociclist( int id )
    {
        return motociclistRepo.getById( id );
    }


    public List<Participare> getParticipanti( int id_cursa )
    {
        return partRepo.getByCursaId( id_cursa );
    }

    public List<Cursa> getCurse()
    {
        return cursaRepo.getAll();
    }

    public List<Motociclist> getMotociclisti()
    {
        return motociclistRepo.getAll();
    }

    public void deleteCursa( Cursa c )
    {
        for( Participare p : getParticipanti( c.getId() ) )
        {
            deleteParticipare( p );
        }
        cursaRepo.remove( c );
    }

    public void deleteParticipare( Participare p )
    {
        if( null != partRepo.getById( p.getId() ) )
        {
            Cursa c = cursaRepo.getById( p.getCursaId() );
            c.setLoc_ramas( c.getLoc_ramas() + 1 );
            updateCursa( c );
            partRepo.remove( p );
        }
    }

    public void deleteMotociclist( Motociclist m )
    {
        for( Participare p : partRepo.getAll() )
        {
            if( p.getMotociclistId() == m.getId() )
            {
                deleteParticipare( p );
            }
        }
        motociclistRepo.remove( m );
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
        for( Participare p : partRepo.getAll() )
        {
            partRepo.remove( p );
        }
        for( Cursa c : cursaRepo.getAll() )
        {
            cursaRepo.remove( c );
        }

        for( Motociclist m : motociclistRepo.getAll() )
        {
            motociclistRepo.remove( m );
        }

    }
}
