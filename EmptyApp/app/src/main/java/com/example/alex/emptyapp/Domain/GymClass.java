package com.example.alex.emptyapp.Domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import java.util.HashSet;
import java.util.Set;

@Entity(tableName = "GymClass")
public  class GymClass extends BaseModel
{
    private String Name;

    @Ignore
    private Set< Integer > ClassTrainers = new HashSet<>();

    public Set<Integer> getClassTrainers() {
        return ClassTrainers;
    }

    public String getName() {
        return Name;
    }

    public void setClassTrainers(Set<Integer> classTrainers) {
        ClassTrainers = classTrainers;
    }

    public void setName(String name) {
        Name = name;
    }

}

