package com.example.calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
public class TaskActivity extends AppCompatActivity {

    List<String> items;

    Button btadd;
    EditText etitem;
    RecyclerView rvitems;
    TasksAdapter tasksAdpater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        btadd = findViewById(R.id.btadd);
        etitem = findViewById(R.id.etitem);
        rvitems = findViewById(R.id.rvitems);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        loadItems();

        TasksAdapter.OnLongClickListener onLongClickListener = new TasksAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                //Delete item from the model
                items.remove(position);
                //notify the adapter
                tasksAdpater.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();

            }
        };
        tasksAdpater = new TasksAdapter(items, onLongClickListener);
        rvitems.setAdapter(tasksAdpater);
        rvitems.setLayoutManager(new LinearLayoutManager(this));

        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String todoItem = etitem.getText().toString();
                // add item to model
                items.add(todoItem);
                //notify adapter that an item is inserted
                tasksAdpater.notifyItemInserted(items.size() - 1);
                etitem.setText("");
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        startActivity(new Intent(TaskActivity.this,MainActivity.class));
                        return true;
                    case R.id.action_events:
                        startActivity(new Intent(TaskActivity.this,EventActivity.class));
                        return true;
                    case R.id.action_list:
                        startActivity(new Intent(TaskActivity.this,TaskActivity.class));
                        return true;
                    default: return true;
                }
            }
        });
    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    //this function will load items by reading every line of the data file
    private void loadItems() {
        try {
            //items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
            items = new ArrayList(FileUtils.readLines(getDataFile(), Charset.defaultCharset() ));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    // This funciton savesitems by writing  them into the file
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }

    }

}

