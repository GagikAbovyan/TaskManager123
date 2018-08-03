package vtc.room.a101.taskmanager.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Calendar;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import vtc.room.a101.taskmanager.R;
import vtc.room.a101.taskmanager.activities.InfoActivity;
import vtc.room.a101.taskmanager.model.Task;
import vtc.room.a101.taskmanager.providers.DataProvider;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {

    private final Context context;
    private final List<Task> list;

    public TasksAdapter(final Context context, final List<Task> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public TasksAdapter.TaskViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TasksAdapter.TaskViewHolder holder, final int position) {
        final Task task = DataProvider.getListTasks().get(position);
        holder.imageTask.setImageResource(task.getImageTaskId());
        holder.task.setText(task.getTask());
        holder.time.setText(String.valueOf(task.getTime()[0]) + ":" +
                String.valueOf(task.getTime()[1]));
        holder.date.setText(String.valueOf(task.getDate()[0]) + "." +
                String.valueOf(task.getDate()[1]) + "."
                + String.valueOf(task.getDate()[2]));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfo(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView imageTask;
        private TextView task, time, date;
        TaskViewHolder(final View itemView) {
            super(itemView);
            imageTask = (CircleImageView) itemView.findViewById(R.id.image_task);
            task = (TextView) itemView.findViewById(R.id.task_item);
            time = (TextView) itemView.findViewById(R.id.time);
            date = (TextView) itemView.findViewById(R.id.date);
            sendNotification();
        }

        private int returnTime(final Task task){
            Calendar calendar = Calendar.getInstance();
            final int hours = calendar.get(Calendar.HOUR_OF_DAY);
            final int minutes = calendar.get(Calendar.MINUTE);
            int timeTemp;
            if (hours == task.getTime()[0] || (hours - task.getTime()[0]) == 12){
                timeTemp = (task.getTime()[1] - minutes) * 60 * 1000;
                return timeTemp;
            }
            if (hours < task.getTime()[0] || (hours - 12) < task.getTime()[0]){
                timeTemp = ((60 - minutes) + task.getTime()[1]) * 60 * 1000;
                return timeTemp;
            }
            return 0;
        }

        private void sendNotification(){

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(returnTime(DataProvider.getListTasks().get(DataProvider.getPosition())));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    NotificationCompat.Builder builder = (NotificationCompat.Builder)
                            new NotificationCompat.Builder(context)
                                    .setSmallIcon(R.drawable.ic_eye)
                                    .setContentTitle("Push Cool App")
                                    .setContentText("Welcome to my app")
                                    .setStyle(new NotificationCompat.BigTextStyle()
                                            .bigText(DataProvider.getListTasks().get(DataProvider.getPosition()).getTask()))
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    // Notification Manager
                    final NotificationManagerCompat notificationManager =
                            NotificationManagerCompat.from(context);
                    notificationManager.notify(100, builder.build());
                }
            });
            thread.start();
        }
    }

    private void openInfo(final int position){
        final Intent intent = new Intent(context, InfoActivity.class);
        intent.putExtra(DataProvider.getMyKey(), position);
        (context).startActivity(intent);
    }
}
