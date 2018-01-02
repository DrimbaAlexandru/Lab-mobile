package com.example.alex.emptyapp.Domain;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(tableName = "ClassSchedule")
public class ClassSchedule extends BaseModel
    {
        private int ClassId;

        private Date Date;
        private int Capacity ;
        private String Room;
        private Difficulty Difficulty;
        private int TrainerId;
        @Ignore
        private Set<Integer> ClassParticipants;

        public ClassSchedule()
        {
            ClassParticipants = new HashSet<>();
        }

        public Set<Integer> getClassParticipants() {
            return ClassParticipants;
        }

        public java.util.Date getDate() {
            return Date;
        }

        public Difficulty getDifficulty() {
            return Difficulty;
        }

        public int getCapacity() {
            return Capacity;
        }

        public int getClassId() {
            return ClassId;
        }

        public int getTrainerId() {
            return TrainerId;
        }

        public String getRoom() {
            return Room;
        }

        public void setCapacity(int capacity) {
            Capacity = capacity;
        }

        public void setClassId(int classId) {
            ClassId = classId;
        }

        public void setClassParticipants(Set<Integer> classParticipants) {
            ClassParticipants = classParticipants;
        }

        public void setDate(java.util.Date date) {
            Date = date;
        }

        public void setDifficulty(Difficulty difficulty) {
            Difficulty = difficulty;
        }

        public void setRoom(String room) {
            Room = room;
        }

        public void setTrainerId(int trainerId) {
            TrainerId = trainerId;
        }

    }
