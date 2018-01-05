package com.example.alex.emptyapp.Domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import java.util.HashSet;
import java.util.Set;

@Entity(tableName = "AppUser")
public class User extends BaseModel
{
    public User()
    {
    }

    private String Username;
    private String Name;
    private String Email;

    public String getEmail() {
        return Email;
    }

    public String getName() {
        return Name;
    }

    public String getUsername() {
        return Username;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setUsername(String username) {
        Username = username;
    }

}
