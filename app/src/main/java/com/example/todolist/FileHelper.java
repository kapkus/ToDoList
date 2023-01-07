package com.example.todolist;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import org.threeten.bp.LocalDateTime;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

    public static final String FILENAME = "listInfo.json";

    public static void writeData(String item, @NonNull Context context) {
        File directory = context.getFilesDir();
        String path = directory+"/"+FILENAME;

        List<TaskData> readList = new ArrayList<>();
        readList = loadList(readList, path);

        readList.add(new TaskData(readList.size(), item, LocalDateTime.now(), false));
        saveListToJson(readList, path);
        System.out.println(readList);

    }

    public static void saveListToJson(List<TaskData> tasks, String path){

        try (Writer writer = new FileWriter(path)) {
            Gson gsonB = new GsonBuilder().create();
            gsonB.toJson(tasks, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<TaskData> loadList(List<TaskData> taskList, String path){
        Type listType = new TypeToken<ArrayList<TaskData>>(){}.getType();
        Gson gson = new Gson();
        JsonReader reader;

        try {
            reader = new JsonReader(new FileReader(path));
            taskList = gson.fromJson(reader, listType);
            reader.close();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        return taskList;
    }

    public static List<TaskData> loadList(Context context){
        File directory = context.getFilesDir();
        String path = directory+"/"+FILENAME;
        Type listType = new TypeToken<ArrayList<TaskData>>(){}.getType();
        Gson gson = new Gson();
        JsonReader reader;
        List<TaskData> tasks = new ArrayList<>();

        try {
            reader = new JsonReader(new FileReader(path));
            tasks = gson.fromJson(reader, listType);
            reader.close();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public static void hideTask(Context context, int id){
        File directory = context.getFilesDir();
        String path = directory+"/"+FILENAME;
        Type listType = new TypeToken<ArrayList<TaskData>>(){}.getType();
        Gson gson = new Gson();
        JsonReader reader;
        List<TaskData> tasks = new ArrayList<>();

        try {
            reader = new JsonReader(new FileReader(path));
            tasks = gson.fromJson(reader, listType);
            reader.close();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        tasks.get(id).setDeleted(true);
        saveListToJson(tasks, path);
        Log.e("Deleted", "successfully");
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<String> readData(@NonNull Context context) {
        ArrayList<String> itemList = null;
        try {
            FileInputStream fis = context.openFileInput("listInfo.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            itemList = (ArrayList<String>) ois.readObject();
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "File not found: "+ e , Toast.LENGTH_SHORT).show();
            itemList = new ArrayList<>();
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return itemList;
    }
}
