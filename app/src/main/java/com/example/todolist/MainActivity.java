package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.OnClickListener, DialogFragment.DialogInterface {

    String FILE_NAME = "myData";
    SharedPreferences sharedPreferences;

    EditText item;
    Button add;
    RecyclerView recyclerView;
    ArrayList<String> itemList = new ArrayList<>();
    MyRecyclerViewAdapter recyclerViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item = findViewById(R.id.editText);
        add = findViewById(R.id.button);
        //itemList = FileHelper.readData(this);
        itemList = readSpData();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new MyRecyclerViewAdapter(this, itemList);
        recyclerView.setAdapter(recyclerViewAdapter);

        add.setOnClickListener(view -> {
            String itemName = item.getText().toString();
            itemList.add(itemName);
            item.setText("");
            recyclerViewAdapter.notifyItemInserted(recyclerViewAdapter.getItemCount() - 1);

            Intent saveIntent = new Intent(this, ServiceSaver.class);
            saveIntent.putStringArrayListExtra("itemList", itemList);
            startService(saveIntent);
        });

    }

    private ArrayList<String> readSpData(){
        sharedPreferences = this.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet("itemSet", null);
        return new ArrayList<>(set);
    }

    @Override
    public void onClick(int position) {
        new DialogFragment(position).show(getSupportFragmentManager(), DialogFragment.TAG);
    }

    @Override
    public void onDelete(int position) {
        itemList.remove(position);
        recyclerViewAdapter.notifyItemRemoved(position);
        FileHelper.writeData(itemList, this);
    }
}