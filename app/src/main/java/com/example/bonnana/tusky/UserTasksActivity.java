package com.example.bonnana.tusky;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.bonnana.tusky.model.Task;
import com.example.bonnana.tusky.model.TaskList;

public class UserTasksActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private TaskList taskList = new TaskList();

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();

            Task task = taskList.getTaskArrayList().get(position);
            String id = task.getId();

            Toast.makeText(UserTasksActivity.this, "SUKSES", Toast.LENGTH_LONG).show();

            callExplicitIntent(id);
        }
    };

    public void callExplicitIntent(String id) {
        Intent intent = new Intent(this, TaskActivity.class);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, id);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_tasks);


    }

    public void checkTask(View view) {
        int pos = (Integer) view.getTag();

        Task task = taskList.getTaskArrayList().get(pos);
        String id = task.getId();

        Toast.makeText(UserTasksActivity.this, id, Toast.LENGTH_LONG).show();
    }
}
