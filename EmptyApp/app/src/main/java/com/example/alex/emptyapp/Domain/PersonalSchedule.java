package com.example.alex.emptyapp.Domain;

import java.util.Date;

public class PersonalSchedule extends BaseModel
    {
        private int ParticipantId;
        private int TrainerId;
        private Date Date;
        public String Room;

        public int getTrainerId() {
            return TrainerId;
        }

        public String getRoom() {
            return Room;
        }

        public Date getDate() {
            return Date;
        }

        public int getParticipantId() {
            return ParticipantId;
        }

        public void setRoom(String room) {
            Room = room;
        }

        public void setTrainerId(int trainerId) {
            TrainerId = trainerId;
        }

        public void setDate(java.util.Date date) {
            Date = date;
        }

        public void setParticipantId(int participantId) {
            ParticipantId = participantId;
        }

    }
