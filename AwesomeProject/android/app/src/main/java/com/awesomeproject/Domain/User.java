package com.awesomeproject.Domain;

import android.arch.persistence.room.Entity;

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
