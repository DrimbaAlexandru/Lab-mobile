package com.example.alex.emptyapp.Repository.Rest;

import com.example.alex.emptyapp.Domain.ProductDescription;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 29.01.2018.
 */

public class ProductDescriptionResponse
{
    private int count = 0;
    private int page = 0;
    private List< ProductDescription > items = new ArrayList<>();

    public int getPage()
    {
        return page;
    }

    public int getCount()
    {
        return count;
    }

    public List< ProductDescription > getItems()
    {
        return items;
    }

    public void setPage( int page )
    {
        this.page = page;
    }

    public void setItems( List< ProductDescription > items )
    {
        this.items = items;
    }

    public void setCount( int count )
    {
        this.count = count;
    }
}
