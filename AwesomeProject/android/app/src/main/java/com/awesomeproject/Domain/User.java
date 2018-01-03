package com.awesomeproject.Domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import java.util.HashSet;
import java.util.Set;

@Entity(tableName = "GymUser")
public class User extends BaseModel
{
    public User()
    {
    }

    private String Username;
    private Role Role;
    private String Name;

    private String Email;

    public Role getRole() {
        return Role;
    }

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

    public void setRole(Role role) {
        Role = role;
    }

    public void setUsername(String username) {
        Username = username;
    }

}
