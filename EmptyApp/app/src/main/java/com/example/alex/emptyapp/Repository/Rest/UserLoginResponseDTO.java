package com.example.alex.emptyapp.Repository.Rest;

import com.example.alex.emptyapp.Domain.Role;
import com.example.alex.emptyapp.Domain.User;

/**
 * Created by Alex on 28.12.2017.
 */

public class UserLoginResponseDTO
{
    public int Id;
    public String Username;
    public String Name;
    public int Role;

    public User toUser()
    {
        User u = new User();
        u.setId( Id );
        u.setUsername( Username );
        u.setName( Name );
        if( Role >= 0 && Role < com.example.alex.emptyapp.Domain.Role.values().length )
        {
            u.setRole( com.example.alex.emptyapp.Domain.Role.values()[ Role ] );
        }
        else
        {
            u.setRole( null );
        }
        u.setEmail( "" );
        return u;

    }
}
