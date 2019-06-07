package com.krolseb.todolistapp.model;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;


public class FileService {
    private static final String DATA_READ_ATTRIBUTES_SEPARATOR = "\\|";
    private static final String DATA_WRITE_ATTRIBUTES_SEPARATOR = "|";
    private static final String NEW_LINE_SEPARATOR = "line.separator";
    private static final String FILE_WITH_TASKS = "tasks.txt";

    private List<Task> tasks;
    private Context context;

    public FileService(Context context) {
        this.context = context;
        tasks = new ArrayList<>();
    }

    public boolean isFileExists(){
        File file = new File(context.getFilesDir(),FILE_WITH_TASKS);
        return (file.exists() && !(file.length() == 0));
    }

    public void writeTaskToFile(Task task) throws IOException {
        FileOutputStream fileOutputStream = context.openFileOutput(FILE_WITH_TASKS, MODE_PRIVATE | MODE_APPEND);
        fileOutputStream.write((task.getTitle() + DATA_WRITE_ATTRIBUTES_SEPARATOR +
                task.getDate() + DATA_WRITE_ATTRIBUTES_SEPARATOR + task.getCategory()).getBytes());
        fileOutputStream.write(System.getProperty(NEW_LINE_SEPARATOR).getBytes());
        fileOutputStream.close();
    }

    public List<Task> readTasksFromFile() throws IOException {
        tasks.clear();

        FileInputStream fileInputStream = context.openFileInput(FILE_WITH_TASKS);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        while((line = bufferedReader.readLine()) != null){
            String [] separatedAttributes = line.split(DATA_READ_ATTRIBUTES_SEPARATOR);
            Task task = new Task();
            task.setTitle(separatedAttributes[0]);
            task.setDate(separatedAttributes[1]);
            task.setCategory(separatedAttributes[2]);
            tasks.add(task);
        }

        fileInputStream.close();
        return tasks;
    }

}