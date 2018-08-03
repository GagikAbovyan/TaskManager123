package vtc.room.a101.taskmanager.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import vtc.room.a101.taskmanager.R;
import vtc.room.a101.taskmanager.model.Task;
import vtc.room.a101.taskmanager.providers.DataProvider;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final int position = getIntent().getExtras().getInt(DataProvider.getMyKey());
        CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        final Task task = DataProvider.getListTasks().get(position);
        ctl.setBackgroundResource(task.getImageTaskId());
        final TextView textView = (TextView) findViewById(R.id.task_info);
        textView.setText(R.string.large_text);
    }
}
