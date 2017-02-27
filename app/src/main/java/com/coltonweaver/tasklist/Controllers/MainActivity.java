package com.coltonweaver.tasklist.Controllers;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.coltonweaver.tasklist.Adapters.TaskRealmAdapter;
import com.coltonweaver.tasklist.Models.TaskItem;
import com.coltonweaver.tasklist.R;
import com.coltonweaver.tasklist.Utils.RealmInitializer;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity {

    private Realm realm;
    private TaskRealmAdapter taskRealmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(intent);
            }
        });

        // Initialize realm with this context bundle
        Realm.init(this);

        // Create configuration
        RealmInitializer.init();

        // Get current list of realm objects
        realm = Realm.getDefaultInstance();
        RealmResults<TaskItem> taskItems = realm
                .where(TaskItem.class)
                .findAllSorted("id", Sort.ASCENDING);

        // Initialize the task list with current items
        taskRealmAdapter =
                new TaskRealmAdapter(this, taskItems, true, true);

        // Set the adapter to the view
        final RealmRecyclerView realmRecyclerView =
                (RealmRecyclerView) findViewById(R.id.realm_recycler_view);
        realmRecyclerView.setAdapter(taskRealmAdapter);

    }

    // When the dataset changes, the activity needs to know to refresh the view
    @Override
    protected void onRestart() {
        super.onRestart();
        taskRealmAdapter.notifyDataSetChanged();
    }

    // Cleanup of the realm client
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(realm != null) {
            realm.close();
            realm = null;
        }
    }
}
