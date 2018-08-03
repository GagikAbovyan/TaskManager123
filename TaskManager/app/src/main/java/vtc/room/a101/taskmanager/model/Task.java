package vtc.room.a101.taskmanager.model;

public class Task{
    private final int imageTaskId;
    private final String task;
    private int time[] = new int[2];
    private int date[] = new int[3];

    public Task(final int imageTaskId, final String task,
                final int[] time, final int date[]) {
        this.imageTaskId = imageTaskId;
        this.task = task;
        this.time = time;
        this.date = date;
    }


    public int getImageTaskId() {
        return imageTaskId;
    }

    public String getTask() {
        return task;
    }

    public int[] getTime() {
        return time;
    }

    public int[] getDate() {
        return date;
    }
}
