package com.coltonweaver.tasklist.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coltonweaver.tasklist.Controllers.EditActivity;
import com.coltonweaver.tasklist.Controllers.MainActivity;
import com.coltonweaver.tasklist.Models.TaskItem;
import com.coltonweaver.tasklist.R;
import com.coltonweaver.tasklist.Utils.RealmInitializer;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by Colton Weaver on 2/25/2017.
 * This adapter handles the behavior of the realmrecyclerview of the main activity.
 * The logic to control the edit button is in the class as well.
 */

public class TaskRealmAdapter
        extends RealmBasedRecyclerViewAdapter<TaskItem, TaskRealmAdapter.ViewHolder> {

    private Realm realm;

    private Context context;

    private static final int[] COLORS = new int[]{
            Color.argb(255, 28, 160, 170),
            Color.argb(255, 99, 161, 247),
            Color.argb(255, 13, 79, 139),
            Color.argb(255, 89, 113, 173),
            Color.argb(255, 200, 213, 219),
            Color.argb(255, 99, 214, 74),
            Color.argb(255, 205, 92, 92),
            Color.argb(255, 105, 5, 98)
    };

    public class ViewHolder extends RealmViewHolder {

        public TextView taskTextView;
        public TextView taskTitleView;

        public ViewHolder(FrameLayout container) {
            super(container);
            this.taskTextView = (TextView) container.findViewById(R.id.task_text_view);
            this.taskTitleView = (TextView) container.findViewById(R.id.task_title_view);

        }
    }

    public TaskRealmAdapter(
            Context context,
            RealmResults<TaskItem> realmResults,
            boolean automaticUpdate,
            boolean animateResults) {
        super(context, realmResults, automaticUpdate, animateResults);
        this.context = context;
    }

    // Creation of a recycler item using given style.
    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
        View v = inflater.inflate(R.layout.task_text_view, viewGroup, false);
        ViewHolder vh = new ViewHolder((FrameLayout) v);
        realm.getDefaultInstance();
        return vh;
    }

    // When the realm item exists, a listener is set on it's edit button
    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, final int position) {
        final TaskItem taskItem = realmResults.get(position);
        viewHolder.taskTitleView.setText(taskItem.getTitle());
        viewHolder.taskTextView.setText(taskItem.getDescription());
        viewHolder.itemView.setBackgroundColor(
                COLORS[(int) (taskItem.getId() % COLORS.length)]
        );
        ImageView editButton = (ImageView) viewHolder.itemView.findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra("task_id", taskItem.getId());
                context.startActivity(intent);
            }
        });
    }
}
