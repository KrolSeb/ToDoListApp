package com.krolseb.todolistapp.presenter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;

import android.widget.ArrayAdapter;

import com.krolseb.todolistapp.R;
import com.krolseb.todolistapp.interfaces.AddTaskInterface;
import com.krolseb.todolistapp.model.FileService;
import com.krolseb.todolistapp.model.Task;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AddTaskPresenter implements AddTaskInterface.Presenter {
    private static final String DATE_ATTRIBUTES_SEPARATOR = "/";
    private static final String DATE_FORMAT = "d/M/yyyy";
    private static final String TIME_FORMAT = "%02d:%02d";
    private static final String EMPTY_STRING = "";
    private static final String SPACE_STRING = " ";

    private static final int MONTH_INDEX_INCREASE_VALUE = 1;
    private static final Calendar calendar = Calendar.getInstance();

    private String title = EMPTY_STRING;
    private String date = EMPTY_STRING;
    private String time = EMPTY_STRING;
    private String fullDate = EMPTY_STRING;
    private String category = EMPTY_STRING;
    private List<String> categories;

    private AddTaskInterface.View addTaskView;
    private FileService fileService;
    private Context context;


    public AddTaskPresenter(AddTaskInterface.View addTaskView, Context context) {
        this.addTaskView = addTaskView;
        this.context = context;
    }

    @Override
    public void onCreate() {
        this.fileService = new FileService(context);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void getDateAndTime(Context context) {
        createDatePickerDialog(context);
    }

    public void getTimeAndDate(Context context) {
        createTimePickerDialog(context);
    }

    private void createDatePickerDialog(Context context) {
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, yearValue, monthValue, dayValue) -> {
            date = dayValue + DATE_ATTRIBUTES_SEPARATOR + (monthValue + MONTH_INDEX_INCREASE_VALUE) + DATE_ATTRIBUTES_SEPARATOR + yearValue;
            fullDate = date + SPACE_STRING + time;
            addTaskView.setDateAndTime(fullDate);
        }, year, month, day);

        datePickerDialog.show();
    }

    private void createTimePickerDialog(Context context) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        final TimePickerDialog timePickerDialog = new TimePickerDialog(context, (view, hourValue, minuteValue) -> {
            checkIfDateChoosen();
            time = String.format(TIME_FORMAT, hourValue, minuteValue);
            fullDate = date + SPACE_STRING + time;
            addTaskView.setDateAndTime(fullDate);
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

    public void setCategories() {
        this.categories = new ArrayList<>();
        categories = Arrays.asList(context.getResources().getStringArray(R.array.categories));
    }

    public void getSpinnerAdapter() {
        ArrayAdapter spinnerElementsAdapter = new ArrayAdapter<>(context,android.R.layout.simple_spinner_item,categories);
        spinnerElementsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addTaskView.setSpinnerAdapter(spinnerElementsAdapter);
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void addTask() {
        if (isDataExists()) {
            Task task = new Task();
            task.setTitle(title);
            task.setDate(date);
            task.setCategory(category);
            addTaskToFile(task);
        }
        else {
            addTaskView.showDialog();
        }
    }

    private void addTaskToFile(Task task)  {
        try {
            fileService.writeTaskToFile(task);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        addTaskView.showToast(context.getResources().getString(R.string.toast_add_task_success_text));
    }

    private boolean isDataExists() {
        return !title.equals(EMPTY_STRING) && !date.equals(EMPTY_STRING) && !category.equals(EMPTY_STRING);
    }

    public AlertDialog createDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(context.getResources().getString(R.string.dialog_cannot_add_task_title));
        alertDialogBuilder.setMessage(context.getResources().getString(R.string.dialog_cannot_add_task_message));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(context.getResources().getString(R.string.dialog_positive_button_text),
                (dialog, which) -> dialog.cancel());

        return alertDialogBuilder.create();
    }

}