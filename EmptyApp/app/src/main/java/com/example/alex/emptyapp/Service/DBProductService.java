package com.example.alex.emptyapp.Service;

import com.example.alex.emptyapp.Domain.InregistrareProdus;
import com.example.alex.emptyapp.Domain.ProductDescription;
import com.example.alex.emptyapp.Repository.Local.DBInregistrareProdusRepository;
import com.example.alex.emptyapp.Repository.Local.DBProductDescriptionRepository;
import com.example.alex.emptyapp.Repository.Local.DBStaticsRepository;
import com.example.alex.emptyapp.Repository.Local.DBStatics;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alex on 26.11.2017.
 */

public class DBProductService
{
    DBInregistrareProdusRepository inregistrareProdusRepository;
    DBProductDescriptionRepository productDescriptionRepository;
    DBStaticsRepository dbStaticsRepo;

    public DBProductService( DBInregistrareProdusRepository ipr, DBProductDescriptionRepository pdr, DBStaticsRepository dbsr )
    {
        inregistrareProdusRepository = ipr;
        productDescriptionRepository = pdr;
        dbStaticsRepo = dbsr;

        if( dbStaticsRepo.getDBStatics() == null )
        {
            dbStaticsRepo.clear();
            DBStatics dbs = new DBStatics();
            dbs.setLast_page_downloaded( 0 );
            dbStaticsRepo.add( dbs );
        }
    }

    public void resetProductDescriptions()
    {
        productDescriptionRepository.deleteAll();
    }

    public void resetInregistrareProdus()
    {
        inregistrareProdusRepository.deleteAll();
    }

    public void insertProductDescriptions( List<ProductDescription> elems )
    {
        productDescriptionRepository.batchInsert( elems );
    }

    public void insertInregistrareProdus( InregistrareProdus rec )
    {
        DBStatics dbStatics = dbStaticsRepo.getDBStatics();
        dbStatics.incrementId();
        rec.setId( dbStatics.getLastId() );
        dbStaticsRepo.setDBStatics( dbStatics );
        inregistrareProdusRepository.insert( rec );
    }

    public List< ProductDescription > getAllPD()
    {
        return productDescriptionRepository.getAll();
    }

    public List< ProductDescription > getPagePD( int count, int offset, String descr )
    {
        return productDescriptionRepository.getPage( count, offset, descr );
    }

    public List< InregistrareProdus > getAllIP()
    {
        return inregistrareProdusRepository.getAll();
    }

    public void deleteIP( InregistrareProdus inregistrareProdus )
    {
        inregistrareProdusRepository.deleteById( inregistrareProdus.getCode() );
    }

    public void setLastPageDownloaded( int page )
    {
        DBStatics dbs = dbStaticsRepo.getDBStatics();
        dbs.setLast_page_downloaded( page );
        dbStaticsRepo.setDBStatics( dbs );
    }

    public int getLastPageDownloaded()
    {
        return dbStaticsRepo.getDBStatics().getLast_page_downloaded();
    }

    public void setTotalElemCount( int elemCount )
    {
        DBStatics dbs = dbStaticsRepo.getDBStatics();
        dbs.setTotal_element_count( elemCount );
        dbStaticsRepo.setDBStatics( dbs );
    }

    public int getTotalElemCount()
    {
        return dbStaticsRepo.getDBStatics().getTotal_element_count();
    }

    public void deletePD( ProductDescription pd )
    {
        productDescriptionRepository.deleteById( pd.getCode() );
    }

    public ProductDescription getByID( String id )
    {
        return productDescriptionRepository.getById( id );
    }
}
