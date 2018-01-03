package com.awesomeproject.Domain;

import java.util.HashSet;
import java.util.Set;

public class SubscriptionType extends BaseModel
    {
        public SubscriptionType()
        {
            Subcriptions = new HashSet<>();
        }

        private String Name;

        private double Price;

        private Set<Integer> Subcriptions;
    }
