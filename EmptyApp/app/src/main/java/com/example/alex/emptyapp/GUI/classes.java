package com.example.alex.emptyapp.GUI;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alex.emptyapp.Domain.GymClass;
import com.example.alex.emptyapp.Domain.User;
import com.example.alex.emptyapp.R;
import com.example.alex.emptyapp.Service.DBGymService;

import java.util.ArrayList;

public class classes extends Activity
{
    private User user;
    private DBGymService srv;
    private boolean is_edit_user_expanded = false;

    private void on_click_expand()
    {
        if( !is_edit_user_expanded )
        {
            findViewById( R.id.fragment_edit_user ).setVisibility( View.VISIBLE );
            is_edit_user_expanded = true;
        }
        else
        {
            findViewById( R.id.fragment_edit_user ).setVisibility( View.GONE );
            is_edit_user_expanded = false;
        }
    }

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_classes );
        ListView list = ( ListView )findViewById( R.id.list );
        ArrayList< String > array = new ArrayList<>();
        ArrayAdapter< String > adapter = new ArrayAdapter< String >( classes.this, android.R.layout.simple_list_item_1, array );
        list.setAdapter( adapter );

        user = ( User )getIntent().getSerializableExtra( "loggedUser" );
        srv = ( DBGymService )getIntent().getSerializableExtra( "DBGymService" );
        String ceva = "Logged in as " + user.getName();

        ( ( TextView )findViewById( R.id.txt_name ) ).setText( ceva );

        for( GymClass c : srv.getClasses() )
        {
            array.add( c.getName() );
            adapter.notifyDataSetChanged();
        }

        ( ( Button )findViewById( R.id.btn_expand ) ).setOnClickListener( e ->
                                                                          {
                                                                              on_click_expand();
                                                                          } );
    }
}
