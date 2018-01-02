package com.example.alex.emptyapp.GUI;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alex.emptyapp.Domain.User;
import com.example.alex.emptyapp.R;
import com.example.alex.emptyapp.Service.DBGymService;

public class EditUserFragment extends Fragment {

    private User loggedUser;
    private View fuckingView;

    private void initialize()
    {
        ( ( TextView )fuckingView.findViewById( R.id.txt_name ) ).setText( loggedUser.getName() );
        ( ( TextView )fuckingView.findViewById( R.id.txt_mail ) ).setText( loggedUser.getEmail() );
    }

    private void submit()
    {

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        loggedUser = ( User )getArguments().getSerializable( "loggedUser" );
        // Inflate the layout for this fragment
        fuckingView = inflater.inflate( R.layout.fragment_edit_user, container, false );
        initialize();
        return fuckingView;
    }

}
