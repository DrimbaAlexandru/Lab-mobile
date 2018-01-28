package com.example.alex.emptyapp.GUI;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alex.emptyapp.Controller.TaskController;
import com.example.alex.emptyapp.Controller.TaskControllerSingleton;
import com.example.alex.emptyapp.R;

public class ActivityInfoComponent extends Fragment
{
    private TaskController controller = null;

    public ActivityInfoComponent()
    {
        // Required empty public constructor
        controller = TaskControllerSingleton.getInstance();
    }

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_activity_info_component, container, false );
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }
}
