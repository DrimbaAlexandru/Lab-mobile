package com.example.alex.emptyapp.Domain;

import java.util.Date;

public class Motociclist extends BaseModel
    {
        private String nume;
        private int capacitate_motor;
        private String echipa;

        public int getCapacitate_motor()
        {
            return capacitate_motor;
        }

        public String getEchipa()
        {
            return echipa;
        }

        public String getNume()
        {
            return nume;
        }

        public void setCapacitate_motor( int capacitate_motor )
        {
            this.capacitate_motor = capacitate_motor;
        }

        public void setEchipa( String echipa )
        {
            this.echipa = echipa;
        }

        public void setNume( String nume )
        {
            this.nume = nume;
        }
    }
