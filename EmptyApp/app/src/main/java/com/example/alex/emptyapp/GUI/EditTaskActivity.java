package com.example.alex.emptyapp.GUI;

import android.app.Activity;
import android.os.Bundle;

import com.example.alex.emptyapp.Controller.TaskController;
import com.example.alex.emptyapp.Controller.TaskControllerSingleton;
import com.example.alex.emptyapp.Domain.MyTask;
import com.example.alex.emptyapp.R;
import com.example.alex.emptyapp.Service.TaskService;

import java.util.Observer;

/**
 * Created by AlexandruD on 26-Jan-18.
 */

public class EditTaskActivity extends Activity {

    private TaskController controller;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.edit_task);

        MyTask baseTask = (MyTask)this.getIntent().getSerializableExtra("base-task");
        MyTask conflictTask = (MyTask)this.getIntent().getSerializableExtra("conflict-task");

        controller = TaskControllerSingleton.getInstance();

        if(baseTask.getVersion() == conflictTask.getVersion()) {
            // setup layout for normal updates
            setupUINormal();
        } else {
            // setup layout for merging
            setupUIConflict();
        }

        setupHandlers();
    }

    public void setupUINormal() {

    }

    public void setupUIConflict() {

    }

    public void setupHandlers() {

    }
}
