package com.awesomeproject.Domain;

import android.arch.persistence.room.Entity;

import java.io.Serializable;

@Entity(tableName = "Feedback")
public class Feedback extends BaseModel implements Serializable
    {
        private int TrainerId;
        private int UserId;
        private String Text;
        private short Rating;

        public int getTrainerId() {
            return TrainerId;
        }

        public int getUserId() {
            return UserId;
        }

        public short getRating() {
            return Rating;
        }

        public String getText() {
            return Text;
        }

        public void setTrainerId(int trainerId) {
            TrainerId = trainerId;
        }

        public void setRating(short rating) {
            Rating = rating;
        }

        public void setText(String text) {
            Text = text;
        }

        public void setUserId(int userId) {
            UserId = userId;
        }

        @Override
        public boolean equals(Object obj) {
            return (obj instanceof Feedback) && (this.getTrainerId() == ((Feedback) obj).getTrainerId()) && (getUserId() == ((Feedback) obj).getUserId());
        }
    }

