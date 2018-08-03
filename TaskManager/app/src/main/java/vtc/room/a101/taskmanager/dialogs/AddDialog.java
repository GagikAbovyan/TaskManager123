package vtc.room.a101.taskmanager.dialogs;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import vtc.room.a101.taskmanager.R;
import vtc.room.a101.taskmanager.model.Task;
import vtc.room.a101.taskmanager.providers.DataProvider;

public class AddDialog extends DialogFragment {

    private TextView textTime;
    private TextView textDate;
    private Calendar calendar = Calendar.getInstance();
    private CircleImageView circleImageView;
    private boolean flagTime;
    private boolean flagDate;
    private int positionImage;
    private int[] dates;
    private int[] times;
    private int images[] = {R.drawable.task, R.drawable.sport, R.drawable.work, R.drawable.lesson};


    private final Context context;

    public AddDialog(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_add, container, false);
        findViews(view);
        changeImages(view);
        showDatePicker(view);
        showTimePicker(view);
        addTask(view);
        return view;
    }


    private void findViews(final View view){
        textTime = (TextView) view.findViewById(R.id.input_time);
        textDate = (TextView) view.findViewById(R.id.input_date);
        circleImageView = (CircleImageView) view.findViewById(R.id.add_task_image);
    }

    private void addTask(final View view) {
        final ImageButton addButton = (ImageButton) view.findViewById(R.id.button_ok);
        final EditText editText = (EditText) view.findViewById(R.id.input_task_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagTime && flagDate){
                    DataProvider.setPosition(DataProvider.getPosition() + 1);
                    DataProvider.getListTasks().add(new Task(images[positionImage], editText.getText().toString(), times, dates));
                    dismiss();
                }else {
                    Toast.makeText(getContext(), R.string.incorrect_argument, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void changeImages(final View view){
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner_image);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        circleImageView.setImageResource(images[0]);
                        positionImage = 0;
                        break;
                    case 1:
                        circleImageView.setImageResource(images[1]);
                        positionImage = 1;
                        break;
                    case 2:
                        circleImageView.setImageResource(images[2]);
                        positionImage = 2;
                        break;
                    case 3:
                        circleImageView.setImageResource(images[3]);
                        positionImage = 3;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showDatePicker(final View view) {
        ImageButton dateButton = (ImageButton) view.findViewById(R.id.date);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog datePickerDialog =
                        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                textDate.setText(String.valueOf(i2) + "." + String.valueOf(i1) + "." + String.valueOf(i));
                                flagDate = true;
                                dates = new int[]{i2, i1, i};
                            }
                        }, calendar.get(Calendar.YEAR)
                                , calendar.get(Calendar.MONTH)
                                , calendar.get(Calendar.DAY_OF_MONTH));

                calendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);

                datePickerDialog.show();
            }
        });

    }

    private void showTimePicker(final View view) {
        ImageButton timeButton = (ImageButton) view.findViewById(R.id.time);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TimePickerDialog pikerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        textTime.setText(String.valueOf(i) + " : " + String.valueOf(i1));
                        flagTime = true;
                        times = new int[]{i, i1};
                    }
                },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true);
                calendar.set(calendar.HOUR_OF_DAY, calendar.MINUTE);
                pikerDialog.show();
            }
        });

    }

}
