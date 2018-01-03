package com.awesomeproject.Domain;

import java.util.Date;

public class Subcription extends BaseModel
    {
        private int UserId;
        private int TypeId;
        private Date StartDate;
        private Date EndDate;

        public int getUserId() {
            return UserId;
        }

        public Date getEndDate() {
            return EndDate;
        }

        public Date getStartDate() {
            return StartDate;
        }

        public int getTypeId() {
            return TypeId;
        }

        public void setUserId(int userId) {
            UserId = userId;
        }

        public void setEndDate(Date endDate) {
            EndDate = endDate;
        }

        public void setStartDate(Date startDate) {
            StartDate = startDate;
        }

        public void setTypeId(int typeId) {
            TypeId = typeId;
        }
    }
