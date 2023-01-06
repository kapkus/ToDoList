package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.OnClickListener, DialogFragment.DialogInterface {

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
        itemList = FileHelper.readData(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new MyRecyclerViewAdapter(this, itemList);
        recyclerView.setAdapter(recyclerViewAdapter);

        add.setOnClickListener(view -> {
            String itemName = item.getText().toString();
            itemList.add(itemName);
            item.setText("");
            FileHelper.writeData(itemList, getApplicationContext());
            System.out.println(itemList);
            recyclerViewAdapter.notifyItemInserted(recyclerViewAdapter.getItemCount()-1);
        });

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