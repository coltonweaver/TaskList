package com.coltonweaver.tasklist.Controllers;

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
 * Created by Colton Weaver on 2/25/2017.
 * This activity handles creation of new realm objects.
 */

public class AddActivity extends AppCompatActivity {

    private Realm realm;

    // Set up buttons and click listeners
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        realm = Realm.getDefaultInstance();

        final Button ok_button = (Button) findViewById(R.id.add_ok_button);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extractGivenInfo();
            }
        });

        final Button cancel_button = (Button) findViewById(R.id.add_cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // Grabs text within editable fields and passes to addTaskItem
    private void extractGivenInfo() {
        final EditText title = (EditText) findViewById(R.id.add_title);
        final EditText description = (EditText) findViewById(R.id.add_description);
        addTaskItem(title.getText().toString(), description.getText().toString());
    }

    // Creates a realm transaction with given information provided the information is given
    private void addTaskItem(String taskItemTitle, String taskItemDescription) {
        if (taskItemTitle == null || taskItemTitle.length() == 0) {
            Toast.makeText(this, "The task entered is empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        realm.beginTransaction();
        TaskItem taskItem = realm.createObject(TaskItem.class, System.currentTimeMillis());
        taskItem.setTitle(taskItemTitle);
        taskItem.setDescription(taskItemDescription);
        realm.commitTransaction();

        finish();
    }

}
