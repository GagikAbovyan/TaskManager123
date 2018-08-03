package vtc.room.a101.taskmanager.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import vtc.room.a101.taskmanager.R;
import vtc.room.a101.taskmanager.adapters.TasksAdapter;
import vtc.room.a101.taskmanager.dialogs.AddDialog;
import vtc.room.a101.taskmanager.providers.DataProvider;

public class TasksActivity extends AppCompatActivity {

    private int progress;
    private ProgressBar progressBar;
    private TextView textView;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private TasksAdapter tasksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViews();
        recyclerView.setVisibility(View.INVISIBLE);
        fab.setVisibility(View.INVISIBLE);
        recyclerView = (RecyclerView) findViewById(R.id.rec_tasks);
        tasksAdapter = new TasksAdapter(this, DataProvider.getListTasks());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(tasksAdapter);
        increment();
        addTask();
        swipeToDeleteRight();
        swipeToDeleteLeft();
    }



    private void findViews() {
        progressBar = (ProgressBar) findViewById(R.id.progress_login);
        textView = (TextView) findViewById(R.id.text_progress);
        fab = (FloatingActionButton) findViewById(R.id.add_task_button);
        recyclerView = (RecyclerView) findViewById(R.id.rec_tasks);
    }

    private void swipeToDeleteLeft() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition(); //get position which is swipe

                if (direction == ItemTouchHelper.LEFT) {    //if swipe left

                    AlertDialog.Builder builder = new AlertDialog.Builder(TasksActivity.this); //alert for confirm to delete
                    builder.setMessage("Are you sure to delete?");    //set message

                    builder.setPositiveButton("REMOVE", new DialogInterface.OnClickListener() { //when click on DELETE
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tasksAdapter.notifyItemRemoved(position);    //item removed from recylcerview//query for delete
                            DataProvider.getListTasks().remove(position);  //then remove item
                        }
                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {  //not removing items if cancel is done
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tasksAdapter.notifyItemRemoved(position + 1);    //notifies the RecyclerView Adapter that data in adapter has been removed at a particular position.
                            tasksAdapter.notifyItemRangeChanged(position, tasksAdapter.getItemCount());   //notifies the RecyclerView Adapter that positions of element in adapter has been changed from position(removed element index to end of list), please update it.
                        }
                    }).show();  //show alert dialog
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void swipeToDeleteRight() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                DataProvider.getListTasks().remove(position);
                tasksAdapter.notifyDataSetChanged();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void increment() {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (progress <= 100){
                    progress++;
                    progressBar.setProgress(progress);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(String.valueOf(progress));
                            if(progress == 100) {
                                progressBar.setVisibility(View.GONE);
                                textView.setVisibility(View.GONE);
                                fab.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Log.d("tag", e.getMessage());
                    }
                }
            }
        });
        thread.start();
    }


    private void addTask(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialog addDialog = new AddDialog(TasksActivity.this);
                addDialog.show(getSupportFragmentManager().beginTransaction(), "tag");
                recyclerView.setAdapter(new TasksAdapter(TasksActivity.this, DataProvider.getListTasks()));
            }
        });
    }

}
