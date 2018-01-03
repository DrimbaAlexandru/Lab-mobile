package com.awesomeproject.Domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import java.util.HashSet;
import java.util.Set;

@Entity(tableName = "GymClass")
public  class GymClass extends BaseModel
{
    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}

