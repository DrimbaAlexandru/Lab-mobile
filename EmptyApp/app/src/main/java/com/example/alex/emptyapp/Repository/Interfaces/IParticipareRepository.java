package com.example.alex.emptyapp.Repository.Interfaces;

import com.example.alex.emptyapp.Domain.Participare;

import java.util.List;

/**
 * Created by Alex on 05.01.2018.
 */

public interface IParticipareRepository extends IRepository<Participare >
{
    List<Participare> getByCursaId( int cId );
}
