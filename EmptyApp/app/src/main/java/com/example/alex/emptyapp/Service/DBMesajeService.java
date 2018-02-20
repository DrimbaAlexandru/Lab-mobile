package com.example.alex.emptyapp.Service;

import com.example.alex.emptyapp.Domain.Mesaj;
import com.example.alex.emptyapp.Repository.Local.DBMesajeRepository;
import com.example.alex.emptyapp.Repository.Local.DBStaticsRepository;
import com.example.alex.emptyapp.Repository.Local.DBStatics;

import java.util.List;

/**
 * Created by Alex on 26.11.2017.
 */

public class DBMesajeService
{
    DBMesajeRepository mesajeRepository;
    DBStaticsRepository dbStaticsRepo;

    public DBMesajeService( DBMesajeRepository mr, DBStaticsRepository dbsr )
    {
        mesajeRepository = mr;
        dbStaticsRepo = dbsr;

        if( dbStaticsRepo.getDBStatics() == null )
        {
            dbStaticsRepo.clear();
            DBStatics dbs = new DBStatics();
            dbs.setLast_created( 0 );
            dbs.setToken( "" );
            dbStaticsRepo.add( dbs );
        }
    }

    public void resetMesaje()
    {
        mesajeRepository.deleteAll();
    }

    public void insertMesaje( List< Mesaj > mesaje )
    {
        long max_created = dbStaticsRepo.getDBStatics().getLast_created();
        for( Mesaj m : mesaje )
        {
            max_created = Math.max( max_created, m.getCreated() );
        }
        DBStatics statics = dbStaticsRepo.getDBStatics();
        statics.setLast_created( max_created );
        mesajeRepository.batchInsert( mesaje );
        dbStaticsRepo.setDBStatics( statics );
    }

    public List< Mesaj > getAllMesaje()
    {
        return mesajeRepository.getAll();
    }

    public long get_last_created()
    {
        return dbStaticsRepo.getDBStatics().getLast_created();
    }
    public String get_token()
    {
        return dbStaticsRepo.getDBStatics().getToken();
    }
    public void set_token(String token)
    {
        DBStatics statics = dbStaticsRepo.getDBStatics();
        statics.setToken( token );
        dbStaticsRepo.setDBStatics( statics );
    }
}
