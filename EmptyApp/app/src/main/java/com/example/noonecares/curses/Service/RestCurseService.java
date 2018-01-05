package com.example.noonecares.curses.Service;

import com.example.noonecares.curses.Domain.Cursa;
import com.example.noonecares.curses.Domain.Motociclist;
import com.example.noonecares.curses.Domain.Participare;
import com.example.noonecares.curses.Domain.User;
import com.example.noonecares.curses.Repository.Rest.RestCursaRepository;
import com.example.noonecares.curses.Repository.Rest.RestMotociclistRepository;
import com.example.noonecares.curses.Repository.Rest.RestUserRepository;
import com.example.noonecares.curses.Repository.Rest.UserDTO;
import com.example.noonecares.curses.Repository.Rest.UserLoginResponseDTO;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Alex on 28.12.2017.
 */


public class RestCurseService
{
    private RestCursaRepository cursaRepo;
    private RestMotociclistRepository motociclistRepo;
    private RestUserRepository userRepo;

    public RestCurseService( RestCursaRepository cR, RestMotociclistRepository mr, RestUserRepository uR )
    {
        cursaRepo = cR;
        motociclistRepo = mr;
        userRepo = uR;
    }

    public User registerUser( User u ) throws Exception
    {
        Call< User > call = userRepo.add( u );
        Response< User > resp = call.execute();
        return resp.body();
    }

    public User login( String username, String password ) throws Exception
    {
        Call< UserLoginResponseDTO > call = userRepo.login( new UserDTO( username, password ) );
        Response< UserLoginResponseDTO > resp = call.execute();
        try
        {
            return getUserById( resp.body().Id );
        }
        catch( Exception e )
        {
            return null;
        }
    }

    private User getUserById( int id ) throws IOException
    {
        return userRepo.getById( id ).execute().body();
    }


    public Cursa addCursa( Cursa c ) throws IOException
    {
        return cursaRepo.add( c ).execute().body();
    }

    public Participare addParticipare( Participare p ) throws IOException
    {
        return cursaRepo.addParticipant( p.getCursaId(), p.getMotociclistId() ).execute().body();
    }

    public Motociclist addMotociclist( Motociclist m ) throws IOException
    {
        return motociclistRepo.add( m ).execute().body();
    }


    public void updateCursa( Cursa c ) throws IOException
    {
        cursaRepo.update( c ).execute();
    }

    public void updateMotociclist( Motociclist m ) throws IOException
    {
        motociclistRepo.update( m ).execute();
    }


    public Cursa getCursa( int cursa_id ) throws IOException
    {
        return cursaRepo.getById( cursa_id ).execute().body();
    }

    public Motociclist getMotociclist( int id ) throws IOException
    {
        return motociclistRepo.getById( id ).execute().body();
    }


    public List< Motociclist > getParticipanti( int id_cursa ) throws IOException
    {
        return cursaRepo.getParticipanti( id_cursa ).execute().body();
    }

    public List<Cursa> getCurse() throws IOException
    {
        return cursaRepo.getAll().execute().body();
    }

    public List<Motociclist> getMotociclisti() throws IOException
    {
        return motociclistRepo.getAll().execute().body();
    }


    public void deleteCursa( Cursa c ) throws IOException
    {
        cursaRepo.remove( c.getId() ).execute();
    }

    public void deleteMotociclist( Motociclist m ) throws IOException
    {
        motociclistRepo.remove( m.getId() ).execute();
    }

    public List<User> getUsers() throws IOException
    {
        return userRepo.getAll().execute().body();
    }
}
