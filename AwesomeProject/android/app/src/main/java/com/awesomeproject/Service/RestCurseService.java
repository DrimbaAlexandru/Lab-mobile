package com.awesomeproject.Service;

import com.awesomeproject.Domain.Cursa;
import com.awesomeproject.Domain.Motociclist;
import com.awesomeproject.Domain.Participare;
import com.awesomeproject.Domain.User;
import com.awesomeproject.Repository.Rest.RestCursaRepository;
import com.awesomeproject.Repository.Rest.RestMotociclistRepository;
import com.awesomeproject.Repository.Rest.RestUserRepository;
import com.awesomeproject.Repository.Rest.UserDTO;
import com.awesomeproject.Repository.Rest.UserLoginResponseDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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

    public int login( String username, String password ) throws Exception
    {
        Call< UserLoginResponseDTO > call = userRepo.login( new UserDTO( username, password ) );
        Response< UserLoginResponseDTO > resp = call.execute();
        try
        {
            return resp.body().Id;
        }
        catch( Exception e )
        {
            return 0;
        }
    }

    public Cursa addCursa( Cursa c ) throws IOException
    {
        return cursaRepo.add( c ).execute().body();
    }

    public void addParticipare( Participare p ) throws IOException
    {
        cursaRepo.addParticipant( p.getCursaId(), p.getMotociclistId() ).execute();
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

    public Collection<User> getUsers() throws IOException
    {
        List< User > users = new ArrayList<>();

        for( UserLoginResponseDTO u : userRepo.getAll().execute().body() )
        {
            users.add( u.toUser() );
        }
        return users;
    }
}
