package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import org.threeten.bp.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.OnClickListener, DialogFragment.DialogInterface {

    EditText item;
    Button add;
    RecyclerView recyclerView;
    List<TaskData> itemList = new ArrayList<>();
    MyRecyclerViewAdapter recyclerViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        item = findViewById(R.id.editText);
        add = findViewById(R.id.button);
        itemList = FileHelper.loadList(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new MyRecyclerViewAdapter(this, itemList);
        recyclerView.setAdapter(recyclerViewAdapter);

        add.setOnClickListener(view -> {
            String itemName = item.getText().toString();
            recyclerViewAdapter.addItem(new TaskData(itemList.size(), itemName, LocalDateTime.now(), false));
            item.setText("");

            Intent saveIntent = new Intent(this, ServiceSaver.class);
            saveIntent.putExtra("itemName", itemName);
            startService(saveIntent);
        });
    }


    @Override
    public void onClick(int position) {
        new DialogFragment(position).show(getSupportFragmentManager(), DialogFragment.TAG);
    }

    @Override
    public void onDelete(int position) {
        recyclerViewAdapter.hideItem(position);
        FileHelper.hideTask(this, position);
    }
}