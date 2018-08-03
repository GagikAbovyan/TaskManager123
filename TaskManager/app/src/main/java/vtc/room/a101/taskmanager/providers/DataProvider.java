package vtc.room.a101.taskmanager.providers;

import java.util.ArrayList;
import java.util.List;
import vtc.room.a101.taskmanager.model.Account;
import vtc.room.a101.taskmanager.model.Task;

public class DataProvider {

    private static final List<Account> list = new ArrayList<>();
    private static final List<Task> listTasks = new ArrayList<>();
    private static int position = -1;
    private static final String MY_KEY = "F12345678";

    public static List<Task> getListTasks() {
        return listTasks;
    }

    public static List<Account> getList() {
        return list;
    }

    public static int getPosition() {
        return position;
    }


    public static void setPosition(int position) {
        DataProvider.position = position;
    }

    public static String getMyKey() {
        return MY_KEY;
    }

}
