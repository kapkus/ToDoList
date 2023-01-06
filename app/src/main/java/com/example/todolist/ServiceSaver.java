package com.example.todolist;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ServiceSaver extends Service {

    String FILE_NAME = "myData";
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Service", "Service started");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
            ArrayList<String> itemList = intent.getStringArrayListExtra("itemList");
            //FileHelper.writeData(itemList, getApplicationContext());
            saveArray(itemList);
        }
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Service", "Service stopped");
    }

    private void saveArray(ArrayList<String> itemList){
        sharedPreferences = this.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<>(itemList);
        editor.putStringSet("itemSet", set);
        editor.apply();
    }

}
