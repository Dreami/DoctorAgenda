package com.example.doctoragenda;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.joda.time.LocalTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView list_item;
    private Intent dateIntent;
    private Button createAppointment;

    private HashMap<CalendarDay, LocalTime> appointmentMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            appointmentMap = new HashMap<CalendarDay, LocalTime>();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
            SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("h:m");

            list_item = (ListView) findViewById(R.id.list_item);
            createAppointment = (Button) findViewById(R.id.createAppointment);

            dateIntent = new Intent(this, CalendarActivity.class);
            if( getIntent() != null) {
                if( getIntent().getExtras() != null) {
                    if( getIntent().getExtras().get("appointments") != null) {
                        appointmentMap = (HashMap<CalendarDay, LocalTime>) getIntent().getExtras().get("appointments");
                    }
                }
            }
            List<String> appointmentList = new ArrayList<String>();
            String appointmentText;
            for (CalendarDay key : appointmentMap.keySet()) {
                appointmentText = simpleDateFormat.format(key.getDate());
                appointmentText += String.format(" a las %d:%d", appointmentMap.get(key).getHourOfDay(), appointmentMap.get(key).getMinuteOfHour());
                appointmentList.add(appointmentText);
            }

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_list_item_1, appointmentList);

            list_item.setAdapter(arrayAdapter);

            createAppointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dateIntent.putExtra("appointments", appointmentMap);
                    startActivity(dateIntent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
