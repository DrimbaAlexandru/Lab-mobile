package com.example.noonecares.curses.Repository.Interfaces;

import com.example.noonecares.curses.Domain.Participare;

import java.util.List;

/**
 * Created by Alex on 05.01.2018.
 */

public interface IParticipareRepository extends IRepository<Participare >
{
    List<Participare> getByCursaId( int cId );
}
