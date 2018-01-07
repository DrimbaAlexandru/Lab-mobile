package com.awesomeproject.Repository.Rest;

import com.awesomeproject.Domain.User;

/**
 * Created by Alex on 28.12.2017.
 */

public class UserLoginResponseDTO
{
    public int Id;
    public String Username;
    public String Name;

    public User toUser()
    {
        User u = new User();
        u.setId( Id );
        u.setUsername( Username );
        u.setName( Name );
        u.setEmail( "" );
        return u;

    }
}
