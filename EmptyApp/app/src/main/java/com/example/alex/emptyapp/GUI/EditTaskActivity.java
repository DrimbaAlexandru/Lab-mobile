package com.example.alex.emptyapp.GUI;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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

        setupUI(baseTask, conflictTask);
    }

    public void setupUI(MyTask baseTask, MyTask conflictTask) {
        Button okButton = findViewById(R.id.confirmButton);
        EditText etNewText = findViewById(R.id.txt_new);
        EditText etOrigText = findViewById(R.id.txt_orig);

        etOrigText.setText(baseTask.getText());

        if(conflictTask.getVersion() != baseTask.getVersion()) {
            etNewText.setText(conflictTask.getText());
        } else {
            etNewText.setText("");
        }

        okButton.setOnClickListener(e -> {
            String newText = etNewText.getText().toString();
            if(newText.length() != 0) {
                conflictTask.setText(newText);
                controller.resolveConflict(conflictTask);

                // ends the activity
                finish();
            }
        });
    }
}
