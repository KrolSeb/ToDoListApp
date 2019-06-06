package com.krolseb.todolistapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.krolseb.todolistapp.model.SingleTask;
import com.google.android.material.textfield.TextInputEditText;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;


public class AddTaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String DATA_WRITE_ATTRIBUTES_SEPARATOR = "|";
    private static final String NEW_LINE_SEPARATOR = "line.separator";
    private static final String FILE_WITH_TASKS = "tasks.txt";

    private static final String DATE_ATTRIBUTES_SEPARATOR = "/";
    private static final String DATE_FORMAT = "d/M/yyyy";
    private static final String TIME_FORMAT = "%02d:%02d";
    private static final String EMPTY_STRING = "";
    private static final String SPACE_STRING = " ";
    private static final int MONTH_INDEX_INCREASE_VALUE = 1;
    private static final Calendar calendar = Calendar.getInstance();

    @BindView(R.id.titleEditText)
    protected TextInputEditText titleEditText;
    @BindView(R.id.dateAndTimeEditText)
    protected TextInputEditText dateAndTimeEditText;
    @BindView(R.id.datePickerButton)
    protected Button datePickerButton;
    @BindView(R.id.timePickerButton)
    protected Button timePickerButton;
    @BindView(R.id.categoriesSpinner)
    protected Spinner categoriesSpinner;
    @BindView(R.id.addTaskButton)
    protected Button addTaskButton;
    @BindView(R.id.abortButton)
    protected Button abortButton;

    private String title = EMPTY_STRING;
    private String date = EMPTY_STRING;
    private String time = EMPTY_STRING;
    private String category = EMPTY_STRING;
    private List<String> categories;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        ButterKnife.bind(this);

        context = this;
        setCategories();
        setSpinnerElements();
    }

    private void setTitle() {
        title = String.valueOf(titleEditText.getText());
    }

    private void setDate() {
        date = String.valueOf(dateAndTimeEditText.getText());
    }

    @OnClick(R.id.datePickerButton)
    public void onButtonDatePickerClick() {
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, yearValue, monthValue, dayValue) -> {
            date = dayValue + DATE_ATTRIBUTES_SEPARATOR + (monthValue + MONTH_INDEX_INCREASE_VALUE) + DATE_ATTRIBUTES_SEPARATOR + yearValue;
            dateAndTimeEditText.setText(date + SPACE_STRING + time);
        }, year, month, day);

        datePickerDialog.show();
    }

    @OnClick(R.id.timePickerButton)
    public void onButtonTimePickerClick() {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        final TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourValue, minuteValue) -> {
            checkIfDateChoosen();
            time = String.format(TIME_FORMAT, hourValue, minuteValue);
            dateAndTimeEditText.setText(date + SPACE_STRING + time);
        }, hour, minute, true);

        timePickerDialog.show();
    }

    private void checkIfDateChoosen() {
        if (date.equals(EMPTY_STRING)) {
            setCurrentDate();
        }
    }

    private void setCurrentDate() {
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        date = simpleDateFormat.format(todayDate);
    }

    private void setCategories() {
        categories = Arrays.asList(getResources().getStringArray(R.array.categories));
    }

    private void setSpinnerElements() {
        ArrayAdapter<String> spinnerElementsAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,categories);
        spinnerElementsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(spinnerElementsAdapter);
        categoriesSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }


    public void addTaskToFile(SingleTask singleTask)  {
        try {
            writeDataToFile(singleTask);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        showToast(context.getResources().getString(R.string.toast_add_task_success_text));
    }

    private void showToast(String message) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }

    private void writeDataToFile(SingleTask singleTask) throws IOException {
        FileOutputStream fileOutputStream = openFileOutput(FILE_WITH_TASKS, MODE_PRIVATE | MODE_APPEND);
        fileOutputStream.write((singleTask.getTitle() + DATA_WRITE_ATTRIBUTES_SEPARATOR +
                singleTask.getDate() + DATA_WRITE_ATTRIBUTES_SEPARATOR + singleTask.getCategory()).getBytes());
        fileOutputStream.write(System.getProperty(NEW_LINE_SEPARATOR).getBytes());
        fileOutputStream.close();
    }

    private boolean isDataExists() {
        return !title.equals(EMPTY_STRING) && !date.equals(EMPTY_STRING) && !category.equals(EMPTY_STRING);
    }

    @OnClick(R.id.addTaskButton)
    public void onAddTaskButtonClick() {
        setTitle();
        setDate();

        if (isDataExists()) {
            SingleTask singleTask = new SingleTask();
            singleTask.setTitle(title);
            singleTask.setDate(date);
            singleTask.setCategory(category);
            addTaskToFile(singleTask);
        }
        else {
            showDialog();
        }
    }

    private void showDialog() {
        AlertDialog alertDialog = createDialog();
        alertDialog.show();
    }

    private AlertDialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddTaskActivity.this);
        showToast(context.getResources().getString(R.string.toast_add_task_success_text));

        builder.setTitle(context.getResources().getString(R.string.dialog_cannot_add_task_title));
        builder.setMessage(context.getResources().getString(R.string.dialog_cannot_add_task_message));
        builder.setCancelable(false);
        builder.setPositiveButton(context.getResources().getString(R.string.dialog_positive_button_text), (dialog, which) -> dialog.cancel());

        return builder.create();
    }

    @OnClick(R.id.abortButton)
    public void onAbortButtonClick() {
        Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}


