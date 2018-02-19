package com.example.alex.emptyapp.GUI;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.alex.emptyapp.R;

import java.util.List;

/**
 * Created by Alex on 18.02.2018.
 */

public class CustomArrayAdapter extends ArrayAdapter
{
    private int selected_position = -1;

    public CustomArrayAdapter( @NonNull Context context, @LayoutRes int resource )
    {
        super( context, resource );
    }

    public CustomArrayAdapter( @NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId )
    {
        super( context, resource, textViewResourceId );
    }

    public CustomArrayAdapter( @NonNull Context context, @LayoutRes int resource, @NonNull Object[] objects )
    {
        super( context, resource, objects );
    }

    public CustomArrayAdapter( @NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull Object[] objects )
    {
        super( context, resource, textViewResourceId, objects );
    }

    public CustomArrayAdapter( @NonNull Context context, @LayoutRes int resource, @NonNull List objects )
    {
        super( context, resource, objects );
    }

    public CustomArrayAdapter( @NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List objects )
    {
        super( context, resource, textViewResourceId, objects );
    }

    @NonNull
    @Override
    public View getView( int position, @Nullable View convertView, @NonNull ViewGroup parent )
    {
        View returnedView = super.getView( position, convertView, parent );

        if( returnedView instanceof TextView )
        {
            if( position == selected_position )
            {
                ( ( TextView )returnedView ).setTypeface( null, Typeface.BOLD );
            }
            else
            {
                ( ( TextView )returnedView ).setTypeface( null, Typeface.NORMAL );
            }
        }
        return returnedView;
    }

    public void setSelected_position( int selected_position )
    {
        this.selected_position = selected_position;
        notifyDataSetChanged();
    }
}
