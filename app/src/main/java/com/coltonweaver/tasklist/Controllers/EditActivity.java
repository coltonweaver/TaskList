package com.coltonweaver.tasklist.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coltonweaver.tasklist.Models.TaskItem;
import com.coltonweaver.tasklist.R;

import io.realm.Realm;

/**
 * Created by Colton Weaver on 2/26/2017.
 * This activity allows for editting current tasks in the realm database.
 */

public class EditActivity extends AppCompatActivity {

    private Realm realm;

    // Grab current realm instance, pull task information from database, set information in view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        realm = Realm.getDefaultInstance();
        long task_id = intent.getLongExtra("task_id", 0);
        final TaskItem taskItem = realm.where(TaskItem.class).equalTo("id", task_id).findFirst();

        final EditText title = (EditText) findViewById(R.id.edit_title);
        final EditText description = (EditText) findViewById(R.id.edit_description);
        title.setText(taskItem.getTitle());
        description.setText(taskItem.getDescription());

        final Button ok_button = (Button) findViewById(R.id.edit_ok_button);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extractGivenInfo(title, description, taskItem);
            }
        });

        final Button cancel_button = (Button) findViewById(R.id.edit_cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // Helper function to grab information and pass to editTaskItem
    private void extractGivenInfo(EditText editTitle, EditText editText, TaskItem taskItem) {
        editTaskItem(editTitle.getText().toString(), editText.getText().toString(), taskItem);
    }

    // takes the task title, description and item after the fields have been editted and updates the
    // realm database
    public void editTaskItem(String taskItemTitle, String taskItemText, final TaskItem taskItem) {
        if (taskItemTitle == null || taskItemText.length() == 0) {
            Toast.makeText(this, "The task entered is empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }

        realm.beginTransaction();
        taskItem.setTitle(taskItemTitle);
        taskItem.setDescription(taskItemText);
        realm.copyToRealmOrUpdate(taskItem);
        realm.commitTransaction();

        finish();
    }

}
