package com.awesomeproject.Repository.Interfaces;

import com.awesomeproject.Domain.Participare;

import java.util.List;

/**
 * Created by Alex on 05.01.2018.
 */

public interface IParticipareRepository extends IRepository<Participare >
{
    List<Participare> getByCursaId( int cId );
}
