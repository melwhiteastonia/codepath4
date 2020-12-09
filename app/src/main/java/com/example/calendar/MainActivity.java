package com.example.calendar;      //if red change to codepath4

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.calendar.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int currentYear = 0;
    private int currentMonth = 0;
    private int currentDay = 0;
    private int daysIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalendarView calendarView = findViewById(R.id.calendarView);
        final TextView selectedDay = findViewById(R.id.selectedDay);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//       final TextView selectedYear = findViewById(R.id.selectedYear);
        final List<String> calendarStrings = new ArrayList<>();
        final int[] days = new int[30];
        final EditText textInput = findViewById(R.id.dayContent);
        final View dayContent = findViewById(R.id.textInput);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDay.setText("Day: " + dayOfMonth);

                currentYear = year;
                currentMonth = month;
                currentDay = dayOfMonth;
                if (dayContent.getVisibility() == View.GONE) {
                    dayContent.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < 30; i++) {
                    if (days[i] == currentDay) {
                        textInput.setText(calendarStrings.get(i));
                        return;
                    }
                    textInput.setText("");
                }

            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        return true;
                    case R.id.action_events:
                        startActivity(new Intent(MainActivity.this, EventActivity.class));
                        return true;
                    case R.id.action_list:
                        startActivity(new Intent(MainActivity.this, TaskActivity.class));
                        return true;
                    default:
                        return true;
                }
            }
        });

        final Button saveTextButton = findViewById(R.id.saveTextButton);

        saveTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                days[daysIndex] = currentDay;
                daysIndex++;
                calendarStrings.add(daysIndex, textInput.getText().toString());
                textInput.setText("");

            }
        });

    }
}
